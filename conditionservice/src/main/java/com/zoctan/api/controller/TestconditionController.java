package com.zoctan.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.core.service.ParseResponeHelp;
import com.zoctan.api.core.service.TestCaseHelp;
import com.zoctan.api.dto.RequestObject;
import com.zoctan.api.entity.*;
import com.zoctan.api.service.*;
import com.zoctan.api.util.DnamicCompilerHelp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author SeasonFan
 * @date 2021/05/31
 */
@Slf4j
@RestController
@RequestMapping("/testcondition")
public class TestconditionController {
    @Resource
    private TestconditionService testconditionService;

    @Resource
    private ConditionApiService conditionApiService;

    @Resource
    private ApicasesService apicasesService;

    @Resource
    private ApiCasedataService apiCasedataService;

    @Resource
    private ApiService apiService;

    @Resource
    private DeployunitService deployunitService;

    @Resource
    private MacdepunitService macdepunitService;

    @Resource
    private MachineService machineService;

    @Resource
    private TestvariablesService testvariablesService;

    @Resource
    private ApicasesVariablesService apicasesVariablesService;

    @Resource
    private TestvariablesValueService testvariablesValueService;

    @Resource
    private TestconditionReportService testconditionReportService;

    @Resource
    private ExecuteplanService executeplanService;

    @Resource
    private ConditionScriptService conditionScriptService;


    @PostMapping
    public Result add(@RequestBody Testcondition testcondition) {
        Condition con = new Condition(Testcondition.class);
        con.createCriteria().andCondition("conditionname = '" + testcondition.getConditionname() + "'");
        if (testconditionService.ifexist(con) > 0) {
            return ResultGenerator.genFailedResult("该条件名已经存在");
        } else {
            testconditionService.save(testcondition);
            return ResultGenerator.genOkResult();
        }
    }

    @PostMapping("/execplancondition")
    @Async
    public Result exec(@RequestBody Dispatch dispatch) throws Exception {
        Long Planid = dispatch.getExecplanid();
        Long Caseid = dispatch.getTestcaseid();
        Executeplan executeplan = executeplanService.getBy("id", Planid);
        List<Testcondition> testconditionList = testconditionService.GetConditionByPlanIDAndConditionType(Planid, "前置条件", "执行计划");
        if (testconditionList.size() > 0) {
            long ConditionID = testconditionList.get(0).getId();
            TestconditionController.log.info("开始处理计划前置条件-API子条件-============：" );
            APICondition(ConditionID, dispatch, executeplan, Planid);
            TestconditionController.log.info("完成处理计划前置条件-API子条件-============：" );

            TestconditionController.log.info("开始处理用例前置条件-脚本子条件-============：" );
            ScriptCondition(Caseid, dispatch, ConditionID);
            TestconditionController.log.info("完成处理用例前置条件-脚本子条件-============：" );
            //DBCondition();
//            List<ConditionApi> conditionApiList=conditionApiService.GetCaseListByConditionID(ConditionID);
//            TestconditionController.log.info("条件报告API子条件数量-============："+conditionApiList.size());
//            for (ConditionApi conditionApi : conditionApiList) {
//                TestconditionReport testconditionReport=new TestconditionReport();
//                testconditionReport.setTestplanid(dispatch.getExecplanid());
//                testconditionReport.setPlanname(dispatch.getExecplanname());
//                testconditionReport.setBatchname(dispatch.getBatchname());
//                testconditionReport.setConditionid(new Long(ConditionID));
//                testconditionReport.setConditiontype("前置条件");
//                testconditionReport.setConditionresult("");
//                testconditionReport.setConditionstatus("");
//                testconditionReport.setRuntime(new Long(0));
//                testconditionReport.setSubconditionid(conditionApi.getId());
//                testconditionReport.setSubconditiontype("接口");
//                testconditionReport.setStatus("进行中");
//                TestconditionController.log.info("条件报告保存子条件进行中状态-============："+testconditionReport.getPlanname()+"|"+ testconditionReport.getBatchname()+"|"+conditionApi.getCasename());
//                testconditionReportService.save(testconditionReport);
//
//                Long CaseID= conditionApi.getCaseid();
//                Apicases apicases= apicasesService.GetCaseByCaseID(CaseID);
//                Long ApiID= apicases.getApiid();
//                Api api =apiService.getBy("id",ApiID);
//                Long Deployunitid= api.getDeployunitid();
//                Deployunit deployunit=deployunitService.getBy("id",Deployunitid);
//                List<ApiCasedata> apiCasedataList= apiCasedataService.GetCaseDatasByCaseID(CaseID);
//                //区分环境类型
//                Macdepunit macdepunit= macdepunitService.getmacdepbyenvidanddepid(executeplan.getEnvid(),deployunit.getId());
//                Machine machine= machineService.getBy("id",macdepunit.getMachineid());
//                TestCaseHelp testCaseHelp=new TestCaseHelp();
//                long Start = 0;
//                long End = 0;
//                long CostTime=0;
//                RequestObject requestObject= testCaseHelp.GetCaseRequestData(apiCasedataList,api,apicases,deployunit,macdepunit,machine);
//                String Respone="";
//                String ConditionResultStatus="成功";
//                try
//                {
//                    Start = new Date().getTime();
//                    Respone= testCaseHelp.request(requestObject);
//                }
//                catch (Exception ex)
//                {
//                    ConditionResultStatus="失败";
//                    Respone=ex.getMessage();
//                }
//                finally {
//                    End = new Date().getTime();
//                }
//                CostTime=End-Start;
//                //更新条件结果表
//                testconditionReport.setConditionresult(Respone);
//                testconditionReport.setConditionstatus(ConditionResultStatus);
//                testconditionReport.setRuntime(CostTime);
//                testconditionReport.setStatus("已完成");
//                TestconditionController.log.info("条件报告更新子条件结果-============："+testconditionReport.getPlanname()+"|"+ testconditionReport.getBatchname()+"|"+conditionApi.getCasename());
//                testconditionReportService.update(testconditionReport);
//
//                //根据用例是否有中间变量，如果有变量，解析（json，xml，html）保存变量值表，没有变量直接保存条件结果表
//                ApicasesVariables apicasesVariables= apicasesVariablesService.getBy("caseid",apicases.getId());
//                if(apicasesVariables!=null)
//                {
//                    TestconditionController.log.info("条件报告子条件处理变量-============："+apicasesVariables.getVariablesname());
//                    Testvariables testvariables=testvariablesService.getById(apicasesVariables.getVariablesid());
//                    String VariablesPath=testvariables.getVariablesexpress();
//                    TestconditionController.log.info("条件报告子条件处理变量表达式-============："+VariablesPath+" 响应数据类型"+requestObject.getResponecontenttype());
//                    ParseResponeHelp parseResponeHelp=new ParseResponeHelp();
//                    String ParseValue= parseResponeHelp.ParseRespone(requestObject.getResponecontenttype(),Respone,VariablesPath);
//                    TestconditionController.log.info("条件报告子条件处理变量取值-============："+ParseValue);
//                    TestvariablesValue testvariablesValue=new TestvariablesValue();
//                    testvariablesValue.setPlanid(Planid);
//                    testvariablesValue.setPlanname(dispatch.getExecplanname());
//                    testvariablesValue.setBatchname(dispatch.getBatchname());
//                    testvariablesValue.setCaseid(CaseID);
//                    testvariablesValue.setCasename(apicases.getCasename());
//                    testvariablesValue.setVariablesid(testvariables.getId());
//                    testvariablesValue.setVariablesname(testvariables.getTestvariablesname());
//                    testvariablesValue.setVariablesvalue(ParseValue);
//                    testvariablesValue.setMemo("test");
//                    testvariablesValueService.save(testvariablesValue);
//                }
//            }
        }
        return ResultGenerator.genOkResult();
    }

    @PostMapping("/execcasecondition")
    @Async
    public Result execcasecondition(@RequestBody Dispatch dispatch) throws Exception {
        Long Caseid = dispatch.getTestcaseid();
        Long Planid = dispatch.getExecplanid();
        Executeplan executeplan = executeplanService.getBy("id", Planid);
        List<Testcondition> testconditionList = testconditionService.GetConditionByPlanIDAndConditionType(Caseid, "前置条件", "测试用例");
        if (testconditionList.size() > 0) {
            long ConditionID = testconditionList.get(0).getId();
            //处理接口条件
            TestconditionController.log.info("开始处理用例前置条件-API子条件-============：" );
            APICondition(ConditionID, dispatch, executeplan, Planid);
            TestconditionController.log.info("完成处理用例前置条件-API子条件-============：" );
            //处理数据库条件
            //DBCondition();
            //处理脚本条件
            TestconditionController.log.info("开始处理用例前置条件-脚本子条件-============：" );
            ScriptCondition(Caseid, dispatch, ConditionID);
            TestconditionController.log.info("完成处理用例前置条件-脚本子条件-============：" );

        }
        return ResultGenerator.genOkResult();
    }


    public void APICondition(long ConditionID, Dispatch dispatch, Executeplan executeplan, Long Planid) {
        List<ConditionApi> conditionApiList = conditionApiService.GetCaseListByConditionID(ConditionID);
        TestconditionController.log.info("条件报告API子条件数量-============：" + conditionApiList.size());
        for (ConditionApi conditionApi : conditionApiList) {
            TestconditionReport testconditionReport = new TestconditionReport();
            testconditionReport.setTestplanid(dispatch.getExecplanid());
            testconditionReport.setPlanname(dispatch.getExecplanname());
            testconditionReport.setBatchname(dispatch.getBatchname());
            testconditionReport.setConditionid(new Long(ConditionID));
            testconditionReport.setConditiontype("前置条件");
            testconditionReport.setConditionresult("");
            testconditionReport.setConditionstatus("");
            testconditionReport.setRuntime(new Long(0));
            testconditionReport.setSubconditionid(conditionApi.getId());
            testconditionReport.setSubconditiontype("接口");
            testconditionReport.setStatus("进行中");
            TestconditionController.log.info("条件报告保存子条件进行中状态-============：" + testconditionReport.getPlanname() + "|" + testconditionReport.getBatchname() + "|" + conditionApi.getCasename());
            testconditionReportService.save(testconditionReport);

            Long CaseID = conditionApi.getCaseid();
            Apicases apicases = apicasesService.GetCaseByCaseID(CaseID);
            Long ApiID = apicases.getApiid();
            Api api = apiService.getBy("id", ApiID);
            Long Deployunitid = api.getDeployunitid();
            Deployunit deployunit = deployunitService.getBy("id", Deployunitid);
            List<ApiCasedata> apiCasedataList = apiCasedataService.GetCaseDatasByCaseID(CaseID);
            //区分环境类型
            Macdepunit macdepunit = macdepunitService.getmacdepbyenvidanddepid(executeplan.getEnvid(), deployunit.getId());
            Machine machine = machineService.getBy("id", macdepunit.getMachineid());
            TestCaseHelp testCaseHelp = new TestCaseHelp();
            long Start = 0;
            long End = 0;
            long CostTime = 0;
            RequestObject requestObject = testCaseHelp.GetCaseRequestData(apiCasedataList, api, apicases, deployunit, macdepunit, machine);
            String Respone = "";
            String ConditionResultStatus = "成功";
            try {
                Start = new Date().getTime();
                Respone = testCaseHelp.request(requestObject);
            } catch (Exception ex) {
                ConditionResultStatus = "失败";
                Respone = ex.getMessage();
            } finally {
                End = new Date().getTime();
            }
            CostTime = End - Start;
            //更新条件结果表
            testconditionReport.setConditionresult(Respone);
            testconditionReport.setConditionstatus(ConditionResultStatus);
            testconditionReport.setRuntime(CostTime);
            testconditionReport.setStatus("已完成");
            TestconditionController.log.info("条件报告更新子条件结果-============：" + testconditionReport.getPlanname() + "|" + testconditionReport.getBatchname() + "|" + conditionApi.getCasename());
            testconditionReportService.update(testconditionReport);

            //根据用例是否有中间变量，如果有变量，解析（json，xml，html）保存变量值表，没有变量直接保存条件结果表
            ApicasesVariables apicasesVariables = apicasesVariablesService.getBy("caseid", apicases.getId());
            if (apicasesVariables != null) {
                TestconditionController.log.info("条件报告子条件处理变量-============：" + apicasesVariables.getVariablesname());
                Testvariables testvariables = testvariablesService.getById(apicasesVariables.getVariablesid());
                String VariablesPath = testvariables.getVariablesexpress();
                TestconditionController.log.info("条件报告子条件处理变量表达式-============：" + VariablesPath + " 响应数据类型" + requestObject.getResponecontenttype());
                ParseResponeHelp parseResponeHelp = new ParseResponeHelp();
                String ParseValue = parseResponeHelp.ParseRespone(requestObject.getResponecontenttype(), Respone, VariablesPath);
                TestconditionController.log.info("条件报告子条件处理变量取值-============：" + ParseValue);
                TestvariablesValue testvariablesValue = new TestvariablesValue();
                testvariablesValue.setPlanid(Planid);
                testvariablesValue.setPlanname(dispatch.getExecplanname());
                testvariablesValue.setBatchname(dispatch.getBatchname());
                testvariablesValue.setCaseid(CaseID);
                testvariablesValue.setCasename(apicases.getCasename());
                testvariablesValue.setVariablesid(testvariables.getId());
                testvariablesValue.setVariablesname(testvariables.getTestvariablesname());
                testvariablesValue.setVariablesvalue(ParseValue);
                testvariablesValue.setMemo("test");
                testvariablesValueService.save(testvariablesValue);
            }
        }
    }

    public void DBCondition() {
    }

    public void ScriptCondition(Long Caseid, Dispatch dispatch, Long ConditionID) {
        ConditionScript conditionScript = conditionScriptService.findtestconditionscriptwithid(ConditionID);
        if(conditionScript!=null)
        {
            TestconditionController.log.info("脚本条件id：-============：" +conditionScript.getId());
            TestconditionReport testconditionReport = new TestconditionReport();
            testconditionReport.setTestplanid(dispatch.getExecplanid());
            testconditionReport.setPlanname(dispatch.getTestcasename());
            testconditionReport.setBatchname(dispatch.getBatchname());
            testconditionReport.setConditionid(new Long(ConditionID));
            testconditionReport.setConditiontype("前置条件");
            testconditionReport.setConditionresult("");
            testconditionReport.setConditionstatus("");
            testconditionReport.setRuntime(new Long(0));
            testconditionReport.setSubconditionid(conditionScript.getId());
            testconditionReport.setSubconditiontype("脚本");
            testconditionReport.setStatus("进行中");
            TestconditionController.log.info("脚本报告保存子条件进行中状态-============：" + testconditionReport.getPlanname() + "|" + testconditionReport.getBatchname());
            testconditionReportService.save(testconditionReport);
            long Start = 0;
            long End = 0;
            long CostTime = 0;
            String Respone = "执行脚本成功";
            String ConditionResultStatus = "成功";
            try {
                Start = new Date().getTime();
                DnamicCompilerHelp dnamicCompilerHelp = new DnamicCompilerHelp();
                //数据库中获取脚本
                String Script = conditionScript.getScript();
                TestconditionController.log.info("脚本报告脚本子条件:-============：" + conditionScript.getScript());
                String Source = dnamicCompilerHelp.GetCompeleteClass(Script, Caseid);
                dnamicCompilerHelp.CallDynamicScript(Source);
            } catch (Exception ex) {
                ConditionResultStatus = "失败";
                Respone =  ex.getMessage();
            } finally {
                End = new Date().getTime();
            }
            CostTime = End - Start;
            //更新条件结果表
            testconditionReport.setConditionresult(Respone);
            testconditionReport.setConditionstatus(ConditionResultStatus);
            testconditionReport.setRuntime(CostTime);
            testconditionReport.setStatus("已完成");
            TestconditionController.log.info("脚本报告更新子条件结果-============：" + testconditionReport.getPlanname() + "|" + testconditionReport.getBatchname());
            testconditionReportService.update(testconditionReport);
        }

    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        testconditionService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody Testcondition testcondition) {
        testconditionService.update(testcondition);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        Testcondition testcondition = testconditionService.getById(id);
        return ResultGenerator.genOkResult(testcondition);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Testcondition> list = testconditionService.listAll();
        PageInfo<Testcondition> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    /**
     * 更新自己的资料
     */
    @PutMapping("/detail")
    public Result updateDeploy(@RequestBody final Testcondition dic) {
        Condition con = new Condition(Testcondition.class);
        con.createCriteria().andCondition("conditionname = '" + dic.getConditionname() + "'").andCondition("id <> " + dic.getId());
        if (testconditionService.ifexist(con) > 0) {
            return ResultGenerator.genFailedResult("已存在该条件名");
        } else {

            this.testconditionService.updateTestcondition(dic);
            return ResultGenerator.genOkResult();
        }
    }


    @GetMapping("/getalltestcondition")
    public Result getallexplan() {
        List<Testcondition> list = testconditionService.getallTestcondition();
        return ResultGenerator.genOkResult(list);
    }

    /**
     * 输入框查询
     */
    @PostMapping("/search")
    public Result search(@RequestBody final Map<String, Object> param) {
        Integer page = Integer.parseInt(param.get("page").toString());
        Integer size = Integer.parseInt(param.get("size").toString());
        PageHelper.startPage(page, size);
        final List<Testcondition> list = this.testconditionService.findtestconditionWithName(param);
        final PageInfo<Testcondition> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }
}
