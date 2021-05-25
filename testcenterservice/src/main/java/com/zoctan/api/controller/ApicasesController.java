package com.zoctan.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.dto.StaticsDataForPie;
import com.zoctan.api.entity.ApiCasedata;
import com.zoctan.api.entity.Apicases;
import com.zoctan.api.service.ApiCasedataService;
import com.zoctan.api.service.ApicasesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Zoctan
 * @date 2020/09/11
 */
@RestController
@RequestMapping("/apicases")
public class ApicasesController {
    @Resource
    private ApicasesService apicasesService;
    @Autowired
    private ApiCasedataService apiCasedataService;

    @PostMapping
    public Result add(@RequestBody Apicases apicases) {

        Condition con=new Condition(Apicases.class);
        con.createCriteria().andCondition("deployunitname = '" + apicases.getDeployunitname() + "'")
                .andCondition("apiname = '" + apicases.getApiname() + "'").andCondition("casename = '" + apicases.getCasename() + "'")
                ;
        //.orCondition("casejmxname = '" + apicases.getCasejmxname() + "'")
        if(apicasesService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult("用例名或者jmx名已存在");
        }
        else
        {
            apicasesService.save(apicases);
            return ResultGenerator.genOkResult();
        }
    }


    @PostMapping("/copycases")
    public Result Copycases(@RequestBody final Map<String, Object> param) {
        String sourcecaseid=param.get("sourcecaseid").toString();
        String sourcedeployunitid=param.get("sourcedeployunitid").toString();
        String sourcedeployunitname=param.get("sourcedeployunitname").toString();
        String newcasename=param.get("newcasename").toString();

        Condition con=new Condition(Apicases.class);
        con.createCriteria().andCondition("deployunitid = " + sourcedeployunitid)
                .andCondition("casename = '" + newcasename + "'");
        if(apicasesService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult(sourcedeployunitname+"已存在存在此用例名");
        }
        else
        {
            Apicases Sourcecase =apicasesService.getBy("id",Long.parseLong(sourcecaseid));
            Condition apcasedatacon=new Condition(Apicases.class);
            apcasedatacon.createCriteria().andCondition("caseid = " + Long.parseLong(sourcecaseid));
            List<ApiCasedata> SourceApicasedataList= apiCasedataService.listByCondition(apcasedatacon);
            if(SourceApicasedataList.size()>0)
            {
                Sourcecase.setDeployunitid(Long.parseLong(sourcedeployunitid));
                Sourcecase.setDeployunitname(sourcedeployunitname);
                Sourcecase.setCasename(newcasename);
                Sourcecase.setId(null);
                apicasesService.save(Sourcecase);
                Long NewCaseId= Sourcecase.getId();

                for (ApiCasedata apiCasedata:SourceApicasedataList)
                {
                    apiCasedata.setCaseid(NewCaseId);
                    apiCasedata.setId(null);
                    apiCasedataService.save(apiCasedata);
                }
                return ResultGenerator.genOkResult();
            }
            else
            {
                return ResultGenerator.genFailedResult("当前选择的源用例还未完成用例数据，请完成后再进行复制操作");
            }
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        apicasesService.deleteById(id);
        apiCasedataService.deletcasedatabyid(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody Apicases apicases) {
        apicasesService.update(apicases);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        Apicases apicases = apicasesService.getById(id);
        return ResultGenerator.genOkResult(apicases);
    }

    @GetMapping("/getcasenum")
    public Result getcasenum(@RequestParam String casetype) {
        Integer apicasesnum = apicasesService.getcasenum(casetype);
        return ResultGenerator.genOkResult(apicasesnum);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Apicases> list = apicasesService.listAll();
        PageInfo<Apicases> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    @GetMapping("/getstaticsdeployunitcases")
    public Result getstaticsdeployunitcases() {
        List<Apicases> list = apicasesService.getstaticsdeployunitcases();
        List<StaticsDataForPie> result=new ArrayList<>();
        for (Apicases ac: list) {
            StaticsDataForPie staticsDataForPie =new StaticsDataForPie();
            staticsDataForPie.setValue(ac.getApiid());
            staticsDataForPie.setName(ac.getDeployunitname());
            result.add(staticsDataForPie);
        }
        return ResultGenerator.genOkResult(result);
    }

    /**
     * 更新自己的资料
     */
    @PutMapping("/detail")
    public Result updateDeploy(@RequestBody final Apicases apicases) {
//        Condition con=new Condition(Apicases.class);
//        con.createCriteria().andCondition("deployunitname = '" + apicases.getDeployunitname() + "'")
//                .andCondition("apiname = '" + apicases.getApiname() + "'")
//                .andCondition("id <> " + apicases.getId())
//                .andCondition("casename = '" + apicases.getCasename() + "'")
//                .orCondition("casejmxname = '" + apicases.getCasejmxname() + "'");
        if(apicasesService.forupdateifexist(apicases).size() >0)
        {
            return ResultGenerator.genFailedResult("用例名或者jmx名已存在");
        }
        else
        {
            this.apicasesService.updateApicase(apicases);
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
        final List<Apicases> list = this.apicasesService.findApiCaseWithName(param);
        final PageInfo<Apicases> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }


    /**
     * 根据发布单元id获取用例
     */
    @PostMapping("/getcasebydeployunitid")
    public Result getcasebydeployunitid(@RequestBody final Map<String, Object> param) {
        String deployunitid=param.get("sourcedeployunitid").toString();
        final List<Apicases> list = this.apicasesService.getcasebydeployunitid(Long.parseLong(deployunitid));
        return ResultGenerator.genOkResult(list);
    }

    /**
     * 输入框查询
     */
    @PostMapping("/searchbyname")
    public Result searchbyname(@RequestBody final Map<String, Object> param) {
        String deployunitname=(String)param.get("casedeployunitname");
        String apiname=(String)param.get("caseapiname");
        final List<Apicases> list = this.apicasesService.getapicasebyName(deployunitname,apiname);
        return ResultGenerator.genOkResult(list);
    }
}
