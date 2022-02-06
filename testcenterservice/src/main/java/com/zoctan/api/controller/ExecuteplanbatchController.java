package com.zoctan.api.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.core.service.HttpHeader;
import com.zoctan.api.core.service.HttpParamers;
import com.zoctan.api.core.service.TestHttp;
import com.zoctan.api.dto.TestResponeData;
import com.zoctan.api.dto.Testplanandbatch;
import com.zoctan.api.entity.Executeplan;
import com.zoctan.api.entity.ExecuteplanTestcase;
import com.zoctan.api.entity.Executeplanbatch;
import com.zoctan.api.entity.Slaver;
import com.zoctan.api.mapper.ExecuteplanMapper;
import com.zoctan.api.mapper.SlaverMapper;
import com.zoctan.api.service.ExecuteplanService;
import com.zoctan.api.service.ExecuteplanbatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author SeasonFan
 * @date 2020/10/22
 */
@RestController
@RequestMapping("/executeplanbatch")
public class ExecuteplanbatchController {
    @Resource
    private ExecuteplanbatchService executeplanbatchService;
    @Autowired
    private ExecuteplanService executeplanService;
    @Resource
    private SlaverMapper slaverMapper;
    @Resource
    private ExecuteplanMapper executeplanMapper;

    @PostMapping
    public Result add(@RequestBody Executeplanbatch executeplanbatch) {
        Condition con=new Condition(Executeplanbatch.class);
        con.createCriteria().andCondition("batchname = '" + executeplanbatch.getBatchname().replace("'","''") + "'")
                .andCondition("executeplanid = " + executeplanbatch.getExecuteplanid());
        if(executeplanbatchService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult("该测试集合下已经存在此执行计划");
        }
        else {
            Long execplanid = executeplanbatch.getExecuteplanid();
            Executeplan ep = executeplanMapper.findexplanWithid(execplanid);
            String plantype=ep.getUsetype();
            List<Slaver> slaverlist=slaverMapper.findslaverWithType(plantype);
            slaverlist=GetAliveSlaver(slaverlist);
            if(slaverlist.size()>0)
            {
                executeplanbatch.setStatus("待执行");
                executeplanbatch.setSource("平台");
                executeplanbatchService.save(executeplanbatch);
                return ResultGenerator.genOkResult();
            }
            else
            {
                return ResultGenerator.genFailedResult("无执行机可用，请检查是否SlaverService是否正常启动!");
            }
        }
    }

    public List<Slaver> GetAliveSlaver(List<Slaver> SlaverList)
    {
        List<Slaver> AliveList=new ArrayList<>();
        for (Slaver slaver:SlaverList) {
            String IP=slaver.getIp();
            String Port=slaver.getPort();
            String ServerUrl = "http://" + IP + ":" + Port + "/exectestplancase/test";
            ExecuteplanTestcase plancase=new ExecuteplanTestcase();
            String params = JSON.toJSONString(plancase);
            HttpHeader header = new HttpHeader();
            String respon="";
            try {
                TestHttp testHttp=new TestHttp();
                header.addParam("Content-Type", "application/json;charset=utf-8");
                TestResponeData testResponeData=testHttp.doService("http","",ServerUrl,header,new HttpParamers(),params,"POST","",30000);
                respon=testResponeData.getResponeContent();
                //respon = HttphelpB1.doPost(ServerUrl, params, header, 5000,5000);
            } catch (Exception e) {
                respon=e.getMessage();
            }
            if(respon.contains("200"))
            {
                AliveList.add(slaver);
            }
        }
        return AliveList;
    }

    //对外接口，CI或者其他触发计划用例执行
    @PostMapping("/TestPlanRun")
    public Result TestPlanRun(@RequestBody final Map<String, Object> param) {
        String TestPlanName=param.get("TestPlanName").toString();
        String BatchName=param.get("BatchName").toString();
        String Source=param.get("Source").toString();
        long PlanID;
        Executeplan executeplan= executeplanService.getBy("executeplanname",TestPlanName);
        if (executeplan != null)
        {
            Executeplanbatch executeplanbatch=new Executeplanbatch();
            PlanID=executeplan.getId();
            Condition con=new Condition(Executeplanbatch.class);
            con.createCriteria().andCondition("batchname = '" + BatchName + "'")
                    .andCondition("executeplanid = " + PlanID);
            if(executeplanbatchService.ifexist(con)>0)
            {
                return ResultGenerator.genFailedResult("该执行计划下已经存在此批次");
            }
            else {
                executeplanbatch.setStatus("待执行");
                executeplanbatch.setSource(Source);
                executeplanbatch.setBatchname(BatchName);
                executeplanbatch.setExecuteplanid(PlanID);
                executeplanbatch.setExecuteplanname(TestPlanName);
                executeplanbatchService.save(executeplanbatch);

                List<Testplanandbatch> list=new ArrayList<>();
                Testplanandbatch testplanandbatch = new Testplanandbatch();
                testplanandbatch.setBatchname(BatchName);
                testplanandbatch.setPlanid(PlanID);
                list.add(testplanandbatch);
                executeplanService.executeplancase(list,"立即执行");
                return ResultGenerator.genOkResult();
            }
        }
        else
        {
            return ResultGenerator.genFailedResult("未找到此测试计划："+TestPlanName);
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        executeplanbatchService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody Executeplanbatch executeplanbatch) {
        executeplanbatchService.update(executeplanbatch);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        Executeplanbatch executeplanbatch = executeplanbatchService.getById(id);
        return ResultGenerator.genOkResult(executeplanbatch);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Executeplanbatch> list = executeplanbatchService.getallexplanbatch();
        PageInfo<Executeplanbatch> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    @PostMapping("/getbatchbyplan")
    public Result getbatchbyplan(@RequestParam Long executeplanid) {
        List<Executeplanbatch> list = executeplanbatchService.getbatchbyplan(executeplanid);
        return ResultGenerator.genOkResult(list);
    }

    /**
     * 输入框查询
     */
    @PostMapping("/search")
    public Result search(@RequestBody final Map<String, Object> param) {
        Integer page= Integer.parseInt(param.get("page").toString());
        Integer size= Integer.parseInt(param.get("size").toString());
        PageHelper.startPage(page, size);
        final List<Executeplanbatch> list = this.executeplanbatchService.findexplanbatchWithName(param);
        final PageInfo<Executeplanbatch> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }


}
