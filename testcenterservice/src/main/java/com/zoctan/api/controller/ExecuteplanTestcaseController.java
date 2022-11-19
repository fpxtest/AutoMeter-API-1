package com.zoctan.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.dto.ApicasewithStatu;
import com.zoctan.api.dto.StaticsDataForPie;
import com.zoctan.api.dto.TestplanCase;
import com.zoctan.api.entity.Apicases;
import com.zoctan.api.entity.Enviroment;
import com.zoctan.api.entity.Executeplan;
import com.zoctan.api.entity.ExecuteplanTestcase;
import com.zoctan.api.service.ApicasesService;
import com.zoctan.api.service.ExecuteplanService;
import com.zoctan.api.service.ExecuteplanTestcaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author SeasonFan
 * @date 2020/10/12
 */
@RestController
@RequestMapping("/executeplan/testcase")
public class ExecuteplanTestcaseController {
    @Resource
    private ExecuteplanTestcaseService executeplanTestcaseService;
    @Autowired
    private ApicasesService apicaseservice;

    @Resource
    private ExecuteplanService executeplanService;


    @PostMapping
    public Result add(@RequestBody ExecuteplanTestcase executeplanTestcase) {
        executeplanTestcaseService.save(executeplanTestcase);
        return ResultGenerator.genOkResult();
    }

    @PostMapping("/addcases")
    public Result addcase(@RequestBody final List<ExecuteplanTestcase> executeplanTestcase) {
        executeplanTestcaseService.savetestplancase(executeplanTestcase);
        return ResultGenerator.genOkResult();
    }


    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        ExecuteplanTestcase executeplanTestcase=executeplanTestcaseService.findexecplancasebyid(id);
        long execplanid=executeplanTestcase.getExecuteplanid();
        executeplanTestcaseService.deleteById(id);
        Executeplan executeplan=executeplanService.getById(execplanid);
        if(executeplan!=null)
        {
            executeplan.setCasecounts(executeplan.getCasecounts()-1);
            executeplanService.update(executeplan);
        }
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody ExecuteplanTestcase executeplanTestcase) {
        executeplanTestcaseService.update(executeplanTestcase);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        ExecuteplanTestcase executeplanTestcase = executeplanTestcaseService.getById(id);
        return ResultGenerator.genOkResult(executeplanTestcase);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<ExecuteplanTestcase> list = executeplanTestcaseService.listAll();
        PageInfo<ExecuteplanTestcase> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }


    @GetMapping("/getstaticsplancases")
    public Result getstaticsplancases(@RequestParam long projectid) {
        List<ExecuteplanTestcase> list = executeplanTestcaseService.getstaticsplancases(projectid);
        List<StaticsDataForPie> result=new ArrayList<>();
        for (ExecuteplanTestcase executeplanTestcase: list) {
            StaticsDataForPie staticsDataForPie =new StaticsDataForPie();
            staticsDataForPie.setValue(executeplanTestcase.getId());
            staticsDataForPie.setName(executeplanTestcase.getDeployunitname());
            result.add(staticsDataForPie);
        }
        return ResultGenerator.genOkResult(result);
    }

    public List<Apicases> GetApiCase(final Map<String, Object> param)
    {
        Integer page= Integer.parseInt(param.get("page").toString());
        Integer size= Integer.parseInt(param.get("size").toString());
        PageHelper.startPage(page, size);

        List<Apicases> apicaselist = this.apicaseservice.findApiCasebynameandcasetype(param);
        return apicaselist;

    }

    /**
     * 根据caseid和参数类型返回参数值
     */
    @PostMapping("/getcasebydeployandapi")
    public Result casevalue(@RequestBody final Map<String, Object> param) {
        Integer page= Integer.parseInt(param.get("page").toString());
        Integer size= Integer.parseInt(param.get("size").toString());
        PageHelper.startPage(page, size);
         List<ExecuteplanTestcase> plancaselist = this.executeplanTestcaseService.findcasebydeployandapi(param);

         List<Apicases> apicaselist =GetApiCase(param); //this.apicaseservice.findApiCasebynameandcasetype(param);

        List<ApicasewithStatu> lastresult = new ArrayList<ApicasewithStatu>();
        for (Apicases ac : apicaselist) {
            Boolean flag = false;
            System.out.println("外循环casename...................: "+ac.getCasename());
            ApicasewithStatu et = null;
            for (int i = 0; i < plancaselist.size(); i++) {
                if (plancaselist.get(i).getTestcaseid().equals(ac.getId())) {
                    System.out.println("caseid...................: "+plancaselist.get(i).getTestcaseid());
                    et = new ApicasewithStatu();
                    et.setApiname(ac.getApiname());
                    et.setDeployunitname(ac.getDeployunitname());
                    et.setExpect(ac.getExpect());
                    et.setCasename(ac.getCasename());
                    et.setId(ac.getId());
                    et.setApiid(ac.getApiid());
                    et.setDeployunitid(ac.getDeployunitid());
                    et.setStatus(true);
                    flag = true;
                    lastresult.add(et);
                    break;
                }
            }
            System.out.println("外循环flag...................: "+flag);
            if (!flag) {
                et = new ApicasewithStatu();
                et.setApiname(ac.getApiname());
                et.setDeployunitname(ac.getDeployunitname());
                et.setExpect(ac.getExpect());
                et.setCasename(ac.getCasename());
                et.setId(ac.getId());
                et.setApiid(ac.getApiid());
                et.setDeployunitid(ac.getDeployunitid());
                et.setStatus(false);
                lastresult.add(et);
            }
        }
        PageInfo<ApicasewithStatu> pageInfo = new PageInfo<>(lastresult);
        return ResultGenerator.genOkResult(pageInfo);
    }

    @PostMapping("/removecases")
    public Result removeplancase(@RequestBody final List<ExecuteplanTestcase> executeplanTestcase) {
        executeplanTestcaseService.removeexecuteplantestcase(executeplanTestcase);
        return ResultGenerator.genOkResult();
    }

    /**
     * 输入框查询
     */
    @PostMapping("/search")
    public Result search(@RequestBody final Map<String, Object> param) {
        Integer page= Integer.parseInt(param.get("page").toString());
        Integer size= Integer.parseInt(param.get("size").toString());
        PageHelper.startPage(page, size);
        final List<ExecuteplanTestcase> list = this.executeplanTestcaseService.findexplanWithName(param);
        final PageInfo<ExecuteplanTestcase> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    @PostMapping("/getplancasesbyplanidandorder")
    public Result getplancasesbyplanidandorder(@RequestBody final Map<String, Object> param) {
        Integer page= Integer.parseInt(param.get("page").toString());
        Integer size= Integer.parseInt(param.get("size").toString());
        long execplanid= Long.parseLong(param.get("executeplanid").toString());
        PageHelper.startPage(page, size);
        final List<ExecuteplanTestcase> list = this.executeplanTestcaseService.getplancasesbyplanidandorder(execplanid);
        final PageInfo<ExecuteplanTestcase> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    @PostMapping("/updatePlanCaseorder")
    public Result updatePlanCaseorder(@RequestBody final Map<String, Object> param) {
        long id= Long.parseLong(param.get("id").toString());
        long caseorder= Long.parseLong(param.get("caseorder").toString());
        long execplanid= Long.parseLong(param.get("executeplanid").toString());
        List<ExecuteplanTestcase> executeplanTestcaseList= executeplanTestcaseService.findcaseorderexist(execplanid,caseorder);
        if(executeplanTestcaseList.size()>0)
        {
            return ResultGenerator.genFailedResult("此测试集合中的用例已经存在该顺序，请修改");
        }
        else
        {
            this.executeplanTestcaseService.updatePlanCaseorder(id,caseorder);
            return ResultGenerator.genOkResult();
        }
    }



}
