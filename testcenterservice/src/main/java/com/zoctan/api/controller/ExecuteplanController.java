package com.zoctan.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoctan.api.core.exception.ServiceException;
import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.dto.Testplanandbatch;
import com.zoctan.api.entity.Executeplan;
import com.zoctan.api.entity.ExecuteplanTestcase;
import com.zoctan.api.mapper.ExecuteplanParamsMapper;
import com.zoctan.api.service.ExecuteplanParamsService;
import com.zoctan.api.service.ExecuteplanService;
import com.zoctan.api.service.ExecuteplanTestcaseService;
import com.zoctan.api.service.MacdepunitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author SeasonFan
 * @date 2020/09/27
 */
@Slf4j
@RestController
@RequestMapping("/executeplan")
@Validated
public class ExecuteplanController {
    @Resource
    private ExecuteplanService executeplanService;
    @Autowired
    private ExecuteplanTestcaseService execplantestcaseService;
    @Autowired
    private ExecuteplanParamsService executeplanParamsService;
    @Autowired
    private MacdepunitService macdepunitService;

    @PostMapping
    public Result add(@RequestBody Executeplan executeplan) {
        Condition con=new Condition(Executeplan.class);
        con.createCriteria().andCondition("executeplanname = '" + executeplan.getExecuteplanname().replace("'","''") + "'")
                .andCondition("enviromentname = '" + executeplan.getEnviromentname() + "'");
        if(executeplanService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult("此环境下执行计划已经存在");
        }
        else {
            executeplanService.save(executeplan);
            return ResultGenerator.genOkResult();
        }
    }

    @PostMapping("/execplancases")
    public Result execcases(@RequestBody final List<Testplanandbatch> planbatchList) {
        //暂时支持单计划执行
        try {
            executeplanService.executeplancase(planbatchList,"立即执行");
            return ResultGenerator.genOkResult();
        }
        catch (ServiceException se)
        {
            return ResultGenerator.genFailedResult(se.getMessage());
        }
    }

    @PostMapping("/checkcondition")
    public Result checkcondition(@RequestBody Executeplan executeplan) {
        try {
            // 检查此计划下是否有装载用例
            Long planid= executeplan.getId();
            Long envid= executeplan.getEnvid();
            String enviromentname= executeplan.getEnviromentname();
            Integer casenum=execplantestcaseService.findcasenumbyplanid(planid);
            if(casenum.intValue()==0)
            {
                return ResultGenerator.genFailedResult("该执行计划下还未装载测试用例！");
            }
            else
            {
                List<ExecuteplanTestcase> deployidlist = execplantestcaseService.finddeployunitbyplanid(planid);
                if(deployidlist.size()==0)
                {
                    return ResultGenerator.genFailedResult("该执行计划下用例所在的所有发布单元不存在，请检查是否被删除！");
                }
                else
                {
                    for (ExecuteplanTestcase ect: deployidlist) {
                        Long deployid=ect.getDeployunitid();
                        String deployname=ect.getDeployunitname();
                        Integer machinenum= macdepunitService.findmachinenumbyenvidanddeployid(envid,deployid);
                        if(machinenum.intValue()==0)
                        {
                            return ResultGenerator.genFailedResult("该执行计划的用例所在的发布单元: "+deployname+" 在环境: "+enviromentname+" 中未完成部署！");
                        }
                    }
                    return ResultGenerator.genOkResult();
                }
            }
        }
        catch (ServiceException se)
        {
            return ResultGenerator.genFailedResult(se.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        executeplanService.deleteById(id);
        //删除集合用例
        execplantestcaseService.removeplancase(id);
        //删除集合全局参数
        executeplanParamsService.removeplanparams(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody Executeplan executeplan) {
        executeplanService.update(executeplan);
        return ResultGenerator.genOkResult();
    }

    @PostMapping("/updatestatus")
    public Result updatestatus(@PathVariable final List<Executeplan> executeplanList) {
        for (Executeplan ep:executeplanList) {
            executeplanService.updatetestplanstatus(ep.getId(),ep.getStatus());
        }
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        Executeplan executeplan = executeplanService.getById(id);
        return ResultGenerator.genOkResult(executeplan);
    }


    @GetMapping("/getexecuteplannum")
    public Result detail() {
        Integer executeplannum = executeplanService.getexecuteplannum();
        return ResultGenerator.genOkResult(executeplannum);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Executeplan> list = executeplanService.listAll();
        PageInfo<Executeplan> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    @GetMapping("/getallexplan")
    public Result getallexplan() {
        List<Executeplan> list = executeplanService.getallexplan();
        return ResultGenerator.genOkResult(list);
    }

    @GetMapping("/getstaticsplan")
    public Result getstaticsplan() {
        List<String> list = executeplanService.getstaticsplan();
        return ResultGenerator.genOkResult(list);
    }

    @GetMapping("/getallexplanbytype")
    public Result getallexplanbytype(@RequestParam String usetype) {
        List<Executeplan> list = executeplanService.getallexplanbytype(usetype);
        return ResultGenerator.genOkResult(list);
    }

    /**
     * 更新自己的资料
     */
    @PutMapping("/detail")
    public Result updateExecuteplan(@RequestBody final Executeplan executeplan) {
        Condition con=new Condition(Executeplan.class);
        con.createCriteria().andCondition("executeplanname = '" + executeplan.getExecuteplanname().replace("'","''") + "'")
                .andCondition("id <> " + executeplan.getId())
                .andCondition("enviromentname = '" + executeplan.getEnviromentname() + "'");
        if(executeplanService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult("此环境下执行计划已经存在");
        }
        else {
            this.executeplanService.updateexecuteplanname(executeplan);
            return ResultGenerator.genOkResult();
        }
    }

    /**
     * 输入框查询
     */
    @PostMapping("/search")
    public Result search(@RequestBody final Map<String, Object> param) {
        Integer page= Integer.parseInt(param.get("page").toString());
        Integer size= Integer.parseInt(param.get("size").toString());
        PageHelper.startPage(page, size);
        final List<Executeplan> list = this.executeplanService.findexplanWithName(param);
        final PageInfo<Executeplan> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }
}
