package com.zoctan.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.dto.CaseReportStatics;
import com.zoctan.api.dto.FunctionCaseSandF;
import com.zoctan.api.dto.FunctionCaseStatis;
import com.zoctan.api.dto.FunctionConditionStatis;
import com.zoctan.api.entity.*;
import com.zoctan.api.mapper.ExecuteplanTestcaseMapper;
import com.zoctan.api.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author SeasonFan
 * @date 2020/10/16
 */
@Slf4j
@RestController
@RequestMapping("/apicases/report")
public class ApicasesReportController {
    @Resource
    private ApicasesReportService apicasesReportService;

    @Resource
    private ExecuteplanTestcaseService executeplanTestcaseService;

    @Resource
    private DispatchService dispatchService;

    @Resource
    private TestconditionService testconditionService;

    @Resource
    private ConditionApiService conditionApiService;

    @Resource
    private ConditionDbService conditionDbService;

    @Resource
    private ConditionScriptService conditionScriptService;

    @Resource
    private TestconditionReportService testconditionReportService;




    @PostMapping
    public Result add(@RequestBody ApicasesReport apicasesReport) {
        apicasesReportService.save(apicasesReport);
        return ResultGenerator.genOkResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        apicasesReportService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody ApicasesReport apicasesReport) {
        apicasesReportService.update(apicasesReport);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        ApicasesReport apicasesReport = apicasesReportService.getById(id);
        return ResultGenerator.genOkResult(apicasesReport);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<ApicasesReport> list = apicasesReportService.listallresult();
        PageInfo<ApicasesReport> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    /**
     * 输入框查询
     */
    @PostMapping("/search")
    public Result search(@RequestBody final Map<String, Object> param) {
        Integer page = Integer.parseInt(param.get("page").toString());
        Integer size = Integer.parseInt(param.get("size").toString());
        PageHelper.startPage(page, size);
        final List<ApicasesReport> list = this.apicasesReportService.findApicasereportWithName(param);
        final PageInfo<ApicasesReport> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    @PostMapping("/findApicasereportWithNameandStatus")
    public Result findApicasereportWithNameandStatus(@RequestBody final Map<String, Object> param) {
        Integer page = Integer.parseInt(param.get("page").toString());
        Integer size = Integer.parseInt(param.get("size").toString());
        Long executeplanid = Long.parseLong(param.get("executeplanid").toString());
        String status = param.get("caseststus").toString();
        String batchname = param.get("batchname").toString();
        PageHelper.startPage(page, size);
        final List<ApicasesReport> list = this.apicasesReportService.findApicasereportWithNameandStatus(executeplanid,status,batchname);
        final PageInfo<ApicasesReport> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

//    @PostMapping("/findconditionreport")
//    public Result findconditionreport(@RequestBody final Map<String, Object> param) {
//        Integer page = Integer.parseInt(param.get("page").toString());
//        Integer size = Integer.parseInt(param.get("size").toString());
//        Long executeplanid = Long.parseLong(param.get("executeplanid").toString());
//        String batchname = param.get("batchname").toString();
//        PageHelper.startPage(page, size);
//        final List<TestconditionReport> list = this.testconditionReportService.findconditionreport(executeplanid,batchname);
//        final PageInfo<TestconditionReport> pageInfo = new PageInfo<>(list);
//        return ResultGenerator.genOkResult(pageInfo);
//    }




    /**
     * 输入框查询
     */
    @PostMapping("/getstaticsreport")
    public Result getstaticsreport(@RequestBody final Map<String, Object> param) {
        //ApicasesReportController.log.info(param);
        if (param.get("batchname") == null || param.get("testplanname") == null) {
            return ResultGenerator.genOkResult("请选中测试计划和批次");
        }
        CaseReportStatics caseReportStatics = new CaseReportStatics();
        Long casetotals = this.apicasesReportService.getApicasetotalsWithName(param);
        Map<String, Object> statusparams = param;
        statusparams.put("status", "成功");
        Long passcasetotals = this.apicasesReportService.getApicasenumbystatus(statusparams);
        Long costtimes = this.apicasesReportService.getApicasecosttimes(param);
        caseReportStatics.setBatchname(param.get("batchname").toString());
        caseReportStatics.setPlanname(param.get("testplanname").toString());
        caseReportStatics.setTestcasenums(casetotals);
        caseReportStatics.setPassnums(passcasetotals);
        caseReportStatics.setFailnums(casetotals - passcasetotals);
        caseReportStatics.setCosttimes(costtimes);

        final List<CaseReportStatics> list = new ArrayList<>();
        list.add(caseReportStatics);
        final PageInfo<CaseReportStatics> pageInfo = new PageInfo<>(list);

        return ResultGenerator.genOkResult(pageInfo);
    }


    @PostMapping("/getfunctioncasestatics")
    public Result getfunctioncasestatics(@RequestBody final Map<String, Object> param) {
        if (param.get("batchid").toString().isEmpty() || param.get("executeplanid").toString().isEmpty()) {
            return ResultGenerator.genFailedResult("请选择测试集合，执行计划");
        } else {
            Long executeplanid = Long.parseLong(param.get("executeplanid").toString());
            Long batchid = Long.parseLong(param.get("batchid").toString());
            String batchname = param.get("batchname").toString();

            FunctionCaseStatis functionCaseStatis = new FunctionCaseStatis();

            List<ExecuteplanTestcase> executeplanTestcaseList = executeplanTestcaseService.getplancasesbyplanid(executeplanid);
            functionCaseStatis.setCaseNum(executeplanTestcaseList.size());

            Condition dispatchexeccon = new Condition(Dispatch.class);
            dispatchexeccon.createCriteria().andCondition("execplanid = " + executeplanid)
                    .andCondition("batchid = " + batchid)
                    .andCondition("status = '" + "已分配" + "'");
            List<Dispatch> dispatchexecList = dispatchService.listByCondition(dispatchexeccon);
            functionCaseStatis.setExecCaseNums(dispatchexecList.size());

            Condition dispatchnotexeccon = new Condition(Dispatch.class);
            dispatchnotexeccon.createCriteria().andCondition("execplanid = " + executeplanid)
                    .andCondition("batchid = " + batchid)
                    .andCondition("status != '" + "已完成" + "'");
            List<Dispatch> dispatchnotexecList = dispatchService.listByCondition(dispatchnotexeccon);
            functionCaseStatis.setNotExecCaseNums(dispatchnotexecList.size());

            List<ApicasesReport> apicasesReportSuccessList = apicasesReportService.getreportbyplanandbatchstatus(executeplanid, "成功", batchname);
            functionCaseStatis.setSuccessCaseNums(apicasesReportSuccessList.size());

            List<ApicasesReport> apicasesReportFailList = apicasesReportService.getreportbyplanandbatchstatus(executeplanid, "失败", batchname);
            functionCaseStatis.setFailCaseNums(apicasesReportFailList.size());

            float successrate = Float.valueOf(apicasesReportSuccessList.size()) / Float.valueOf(executeplanTestcaseList.size());
            float failrate = Float.valueOf(apicasesReportFailList.size()) / Float.valueOf(executeplanTestcaseList.size());
            String sresultrate="";
            String fresultrate = "";
            DecimalFormat decimalFormat=new DecimalFormat(".00");

            if(successrate==0.0)
            {
                sresultrate="0%";
            }
            else
            {
                 sresultrate=decimalFormat.format(successrate* 100)+ "%";
            }
            if(failrate==0.0)
            {
                fresultrate="0%";
            }
            else
            {
                 fresultrate=decimalFormat.format(failrate* 100)+ "%";
            }

            functionCaseStatis.setFailrate(fresultrate);
            functionCaseStatis.setSuccessrate(sresultrate);

            List<FunctionCaseStatis> functionCaseStatisList = new ArrayList<>();
            functionCaseStatisList.add(functionCaseStatis);

            return ResultGenerator.genOkResult(functionCaseStatisList);
        }
    }

    @PostMapping("/getfunctionconditionstatics")
    public Result getfunctionconditionstatics(@RequestBody final Map<String, Object> param) {
        FunctionConditionStatis functionConditionStatis = new FunctionConditionStatis();
        if (param.get("batchid").toString().isEmpty() || param.get("executeplanid").toString().isEmpty()) {
            return ResultGenerator.genFailedResult("请选择测试集合，执行计划");
        } else {
            Long executeplanid = Long.parseLong(param.get("executeplanid").toString());
            Long batchid = Long.parseLong(param.get("batchid").toString());
            String batchname = param.get("batchname").toString();

            Condition testconditioncon = new Condition(Testcondition.class);
            testconditioncon.createCriteria().andCondition("objecttype = '" + "测试集合" + "'")
                    .andCondition("objectid = " + executeplanid);
            List<Testcondition> testconditionList = testconditionService.listByCondition(testconditioncon);

            long totalconditionnums = 0;
            if (testconditionList.size() > 0) {
                long conditionid = testconditionList.get(0).getId();
                ConditionApi conditionApi = conditionApiService.getBy("conditionid", conditionid);
                if (conditionApi != null) {
                    totalconditionnums = totalconditionnums + 1;
                }
                ConditionDb conditionDb = conditionDbService.getBy("conditionid", conditionid);
                if (conditionDb != null) {
                    totalconditionnums = totalconditionnums + 1;
                }
                ConditionScript conditionScript = conditionScriptService.getBy("conditionid", conditionid);
                if (conditionScript != null) {
                    totalconditionnums = totalconditionnums + 1;
                }
            }
            functionConditionStatis.setTestCollectionConditionsNUms(totalconditionnums);

            long caseconditionnums = 0;
            List<ExecuteplanTestcase> executeplanTestcaseList = executeplanTestcaseService.getplancasesbyplanid(executeplanid);

            for (ExecuteplanTestcase ec : executeplanTestcaseList) {
                long caseid = ec.getTestcaseid();

                Condition testcaseconditioncon = new Condition(Testcondition.class);
                testcaseconditioncon.createCriteria().andCondition("objecttype = '" + "测试用例" + "'")
                        .andCondition("objectid = " + caseid);
                List<Testcondition> testcaseconditionList = testconditionService.listByCondition(testcaseconditioncon);

                if (testcaseconditionList.size() > 0) {
                    long conditionid = testcaseconditionList.get(0).getId();

                    Condition conditionApi = new Condition(ConditionApi.class);
                    conditionApi.createCriteria().andCondition("conditionid = " + conditionid);
                    List<ConditionApi>conditionApiList = conditionApiService.listByCondition(conditionApi);
                    caseconditionnums = caseconditionnums+conditionApiList.size();

                    Condition conditionDB = new Condition(ConditionDb.class);
                    conditionDB.createCriteria().andCondition("conditionid = " + conditionid);
                    List<ConditionDb>conditionDbList = conditionDbService.listByCondition(conditionDB);
                    caseconditionnums = caseconditionnums+conditionDbList.size();

                    Condition conditionScript = new Condition(ConditionScript.class);
                    conditionScript.createCriteria().andCondition("conditionid = " + conditionid);
                    List<ConditionScript>conditionScriptList = conditionScriptService.listByCondition(conditionDB);
                    caseconditionnums = caseconditionnums+conditionScriptList.size();
                }
            }
            functionConditionStatis.setCaseConditionNums(caseconditionnums);
        }
        List<FunctionConditionStatis> functionConditionStatisList = new ArrayList<>();
        functionConditionStatisList.add(functionConditionStatis);
        return ResultGenerator.genOkResult(functionConditionStatisList);
    }

    @PostMapping("/getfunctionCaseSandF")
    public Result getfunctionCaseSandF(@RequestBody final Map<String, Object> param) {
        if (param.get("batchid").toString().isEmpty() || param.get("executeplanid").toString().isEmpty()) {
            return ResultGenerator.genFailedResult("请选择测试集合，执行计划");
        }
        else
        {
            Long executeplanid = Long.parseLong(param.get("executeplanid").toString());
            String batchname = param.get("batchname").toString();

            List<FunctionCaseSandF>functionCaseSandFList=new ArrayList<>();
            List<ApicasesReport> apicasesReportSuccessList = apicasesReportService.getreportbyplanandbatchstatus(executeplanid, "成功", batchname);
            FunctionCaseSandF functionCaseSandF = new FunctionCaseSandF();
            functionCaseSandF.setName("成功");
            functionCaseSandF.setValue(apicasesReportSuccessList.size());
            functionCaseSandFList.add(functionCaseSandF);

            List<ApicasesReport> apicasesReportFailList = apicasesReportService.getreportbyplanandbatchstatus(executeplanid, "失败", batchname);
            FunctionCaseSandF functionCaseSandFail = new FunctionCaseSandF();
            functionCaseSandFail.setName("失败");
            functionCaseSandFail.setValue(apicasesReportFailList.size());
            functionCaseSandFList.add(functionCaseSandFail);
            return ResultGenerator.genOkResult(functionCaseSandFList);
        }
    }

}
