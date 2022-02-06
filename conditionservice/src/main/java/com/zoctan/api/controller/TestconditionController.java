package com.zoctan.api.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.db.Db;
import cn.hutool.db.ds.simple.SimpleDataSource;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.core.service.ParseResponeHelp;
import com.zoctan.api.core.service.ResponeData;
import com.zoctan.api.core.service.TestCaseHelp;
import com.zoctan.api.dto.RequestObject;
import com.zoctan.api.dto.TestResponeData;
import com.zoctan.api.entity.*;
import com.zoctan.api.entity.Dictionary;
import com.zoctan.api.service.*;
import com.zoctan.api.util.DnamicCompilerHelp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.*;

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

    @Autowired(required = false)
    private ConditionApiService conditionApiService;

    @Autowired(required = false)
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
    private EnviromentService enviromentService;

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

    @Resource
    private ConditionDbService conditionDbService;

    @Resource
    private EnviromentAssembleService enviromentAssembleService;

    @Resource
    private ConditionOrderService conditionOrderService;
    @Resource
    private DictionaryService dictionaryService;

    @Resource
    private AccountService accountService;


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
    public Result exec(@RequestBody Dispatch dispatch)  {
        Long Planid = dispatch.getExecplanid();
        Long Caseid = dispatch.getTestcaseid();
        Executeplan executeplan = executeplanService.getBy("id", Planid);
        List<Testcondition> testconditionList = testconditionService.GetConditionByPlanIDAndConditionType(Planid, "前置条件", "测试集合");
        if (testconditionList.size() > 0) {
            long ConditionID = testconditionList.get(0).getId();
            Map<String,Object> conditionmap=new HashMap<>();
            conditionmap.put("conditionid",ConditionID);
            List<ConditionOrder> conditionOrderList= conditionOrderService.findconditionorderWithid(conditionmap);
            //条件排序的按照顺序执行
            if(conditionOrderList.size()>0)
            {
                for (ConditionOrder conditionOrder :conditionOrderList) {
                    if(conditionOrder.getSubconditiontype().equals("接口"))
                    {
                        TestconditionController.log.info("开始顺序处理计划前置条件-接口子条件-============：");
                        APICondition(ConditionID, dispatch, executeplan, Planid);
                        TestconditionController.log.info("完成顺序处理计划前置条件-接口子条件-============：");
                    }
                    if(conditionOrder.getSubconditiontype().equals("数据库"))
                    {
                        TestconditionController.log.info("开始顺序处理计划前置条件-数据库子条件-============：");
                        DBCondition(ConditionID, dispatch);
                        TestconditionController.log.info("完成顺序处理计划前置条件-数据库子条件-============：");
                    }
                    if(conditionOrder.getSubconditiontype().equals("脚本"))
                    {
                        TestconditionController.log.info("开始顺序处理用例前置条件-脚本子条件-============：");
                        ScriptCondition(Caseid, dispatch, ConditionID);
                        TestconditionController.log.info("完成顺序处理用例前置条件-脚本子条件-============：");
                    }
                }
            }
            else
            {
                TestconditionController.log.info("开始处理计划前置条件-数据库子条件-============：");
                DBCondition(ConditionID, dispatch);
                TestconditionController.log.info("完成处理计划前置条件-数据库子条件-============：");
                TestconditionController.log.info("开始处理计划前置条件-接口子条件-============：");
                APICondition(ConditionID, dispatch, executeplan, Planid);
                TestconditionController.log.info("完成处理计划前置条件-接口子条件-============：");
                TestconditionController.log.info("开始处理用例前置条件-脚本子条件-============：");
                ScriptCondition(Caseid, dispatch, ConditionID);
                TestconditionController.log.info("完成处理用例前置条件-脚本子条件-============：");
            }
        }
        return ResultGenerator.genOkResult();
    }

    @PostMapping("/execcasecondition")
    public Result execcasecondition(@RequestBody Dispatch dispatch) throws Exception {
        HashMap<String, String> VariablesNameVlaueMap = new HashMap<>();
        Long Caseid = dispatch.getTestcaseid();
        Long Planid = dispatch.getExecplanid();
        Executeplan executeplan = executeplanService.getBy("id", Planid);
        List<Testcondition> testconditionList = testconditionService.GetConditionByPlanIDAndConditionType(Caseid, "前置条件", "测试用例");
        if (testconditionList.size() > 0) {
            long ConditionID = testconditionList.get(0).getId();
            //处理接口条件
            TestconditionController.log.info("开始处理用例前置条件-API子条件-============：");
            VariablesNameVlaueMap = APICondition(ConditionID, dispatch, executeplan, Planid);
            TestconditionController.log.info("完成处理用例前置条件-API子条件-============：");
            //处理数据库条件
            DBCondition(ConditionID, dispatch);
            //处理脚本条件
            TestconditionController.log.info("开始处理用例前置条件-脚本子条件-============：");
            ScriptCondition(Caseid, dispatch, ConditionID);
            TestconditionController.log.info("完成处理用例前置条件-脚本子条件-============：");

        }
        return ResultGenerator.genOkResult(VariablesNameVlaueMap);
    }

    public HashMap<String, String> APICondition(long ConditionID, Dispatch dispatch, Executeplan executeplan, Long Planid) {
        HashMap<String, String> VariableNameValueMap = new HashMap<>();
        List<ConditionApi> conditionApiList = conditionApiService.GetCaseListByConditionID(ConditionID);
        TestconditionController.log.info("接口子条件条件报告API子条件数量-============：" + conditionApiList.size());
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
            testconditionReport.setSubconditionname(conditionApi.getSubconditionname());
            testconditionReport.setSubconditiontype("接口");
            testconditionReport.setStatus("进行中");
            TestconditionController.log.info("接口子条件条件报告保存子条件进行中状态-============：" + testconditionReport.getPlanname() + "|" + testconditionReport.getBatchname() + "|" + conditionApi.getCasename());
            testconditionReportService.save(testconditionReport);

            long Start = 0;
            long End = 0;
            long CostTime = 0;
            String Respone = "";
            String ConditionResultStatus = "成功";
            Long CaseID = conditionApi.getCaseid();
            Apicases apicases = apicasesService.GetCaseByCaseID(CaseID);

            if (apicases == null) {
                Respone = "未找到条件运行的接口，请检查是否存在或已被删除";
                ConditionResultStatus = "失败";
                UpdatetestconditionReport(testconditionReport, Respone, ConditionResultStatus, new Long(0),conditionApi.getCreator());
                break;
            }
            Long ApiID = apicases.getApiid();
            Api api = apiService.getBy("id", ApiID);
            if (api == null) {
                Respone = "未找到条件运行的接口的API，请检查是否存在或已被删除";
                ConditionResultStatus = "失败";
                UpdatetestconditionReport(testconditionReport, Respone, ConditionResultStatus, new Long(0),conditionApi.getCreator());
                break;
            }
            Long Deployunitid = api.getDeployunitid();
            Deployunit deployunit = deployunitService.getBy("id", Deployunitid);
            if (deployunit == null) {
                Respone = "未找到条件运行接口API所在的发布单元，请检查是否存在或已被删除";
                ConditionResultStatus = "失败";
                UpdatetestconditionReport(testconditionReport, Respone, ConditionResultStatus, new Long(0),conditionApi.getCreator());
                break;
            }

            Enviroment enviroment = enviromentService.getBy("id", executeplan.getEnvid());
            if (enviroment == null) {
                Respone = "未找到条件接口发布单元部署的环境，请检查是否存在或已被删除";
                ConditionResultStatus = "失败";
                UpdatetestconditionReport(testconditionReport, Respone, ConditionResultStatus, new Long(0),conditionApi.getCreator());
                break;
            }

            List<ApiCasedata> apiCasedataList = apiCasedataService.GetCaseDatasByCaseID(CaseID);
            //区分环境类型
            Macdepunit macdepunit = macdepunitService.getmacdepbyenvidanddepid(executeplan.getEnvid(), deployunit.getId());
            if (macdepunit == null) {
                Respone = "接口所在的发布单元未在环境中部署，请检查是否存在或已被删除";
                ConditionResultStatus = "失败";
                UpdatetestconditionReport(testconditionReport, Respone, ConditionResultStatus, new Long(0),conditionApi.getCreator());
                break;
            }
            Machine machine = machineService.getBy("id", macdepunit.getMachineid());
            if (machine == null) {
                Respone = "未找到环境组件部署的服务器，请检查是否存在或已被删除";
                ConditionResultStatus = "失败";
                UpdatetestconditionReport(testconditionReport, Respone, ConditionResultStatus, new Long(0),conditionApi.getCreator());
                break;
            }
            TestCaseHelp testCaseHelp = new TestCaseHelp();
            RequestObject requestObject=new RequestObject();
            try {
                //前置接口条件没支持接口变量的情况
                 requestObject = testCaseHelp.GetCaseRequestData(apiCasedataList, api, apicases, deployunit, macdepunit, machine);
            }
            catch (Exception ex)
            {
                TestconditionController.log.info("接口子条件条件获取请求数据GetCaseRequestData异常-============：" + ex.getMessage());
            }
            try {
                Start = new Date().getTime();
                TestconditionController.log.info("接口子条件条件请求数据-============：" + requestObject.getPostData());
                ResponeData testResponeData = testCaseHelp.request(requestObject);
                Respone = testResponeData.getContent();
            } catch (Exception ex) {
                ConditionResultStatus = "失败";
                Respone = ex.getMessage();
            } finally {
                End = new Date().getTime();
            }

            //根据用例是否有中间变量，如果有变量，解析（json，xml，html）保存变量值表，如果解析失败，置条件为失败
            ApicasesVariables apicasesVariables = apicasesVariablesService.getBy("caseid", apicases.getId());
            TestvariablesValue testvariablesValue = new TestvariablesValue();
            try {
                testvariablesValue = FixApicasesVariables(apicasesVariables, requestObject, Respone, Planid, CaseID, dispatch, apicases);
            } catch (Exception exception) {
                ConditionResultStatus="失败";
            }
            VariableNameValueMap.put(testvariablesValue.getVariablesname(), testvariablesValue.getVariablesvalue());
            CostTime = End - Start;
            //更新条件结果表
            UpdatetestconditionReport(testconditionReport, Respone, ConditionResultStatus, CostTime,conditionApi.getCreator());
        }
        return VariableNameValueMap;
    }

    private TestvariablesValue FixApicasesVariables(ApicasesVariables apicasesVariables, RequestObject requestObject, String Respone, Long Planid, Long CaseID, Dispatch dispatch, Apicases apicases) throws Exception {
        TestvariablesValue testvariablesValue = new TestvariablesValue();
        if (apicasesVariables != null) {
            TestconditionController.log.info("接口子条件条件报告子条件处理变量-============：" + apicasesVariables.getVariablesname());
            Testvariables testvariables = testvariablesService.getById(apicasesVariables.getVariablesid());
            if (testvariables != null) {
                String VariablesPath = testvariables.getVariablesexpress();
                TestconditionController.log.info("接口子条件条件报告子条件处理变量表达式-============：" + VariablesPath + " 响应数据类型" + requestObject.getResponecontenttype());
                ParseResponeHelp parseResponeHelp = new ParseResponeHelp();
                String ParseValue="";
                try
                {
                     ParseValue = parseResponeHelp.ParseRespone(requestObject.getResponecontenttype(), Respone, VariablesPath);
                }
                catch (Exception ex)
                {
                    ParseValue=ex.getMessage();
                    throw new Exception(ex.getMessage());
                }
                finally {
                    TestconditionController.log.info("接口子条件条件报告子条件处理变量取值-============：" + ParseValue);
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
                    TestconditionController.log.info("接口子条件条件报告子条件处理变量完成-============：");
                }
            }
        }
        return testvariablesValue;
    }

    @PostMapping("/execcasecondition/script")
    public Result ConditionForScript(@RequestBody final Map<String, Object> param) throws Exception {
        Long ConditionID = Long.parseLong(param.get("ConditionID").toString());
        Long Caseid = Long.parseLong(param.get("caseid").toString());
        ConditionScript conditionScript = conditionScriptService.findtestconditionscriptwithid(ConditionID);
        if (conditionScript != null) {
            String Respone = "";
            try {
                DnamicCompilerHelp dnamicCompilerHelp = new DnamicCompilerHelp();
                //数据库中获取脚本
                String Script = conditionScript.getScript();
                TestconditionController.log.info("调试脚本报告脚本子条件:-============：" + conditionScript.getScript());
                String Source = dnamicCompilerHelp.GetCompeleteClass(Script, Caseid);
                dnamicCompilerHelp.CallDynamicScript(Source);
            } catch (Exception ex) {
                Respone = ex.getMessage();
                throw new Exception("脚本条件执行异常:" + Respone);
            }
            TestconditionController.log.info("调试脚本报告更新子条件结果-============：");
        }
        else
        {
            throw new Exception("未找到脚本子条件，请检查条件管理-脚本子条件中是否被删除");
        }
        return ResultGenerator.genOkResult("数据库条件执行完成");
    }

    @PostMapping("/execcasecondition/api")
    public Result ConditionForAPI(@RequestBody final Map<String, Object> param) throws Exception {
        Long ConditionID = Long.parseLong(param.get("ConditionID").toString());
        Long EnviromentID = Long.parseLong(param.get("enviromentid").toString());
        HashMap<String, String> VariableNameValueMap = new HashMap<>();
        List<ConditionApi> conditionApiList = conditionApiService.GetCaseListByConditionID(ConditionID);
        TestconditionController.log.info("调试接口子条件条件报告API子条件数量-============：" + conditionApiList.size());
        for (ConditionApi conditionApi : conditionApiList) {
            Long CaseID = conditionApi.getCaseid();
            Apicases apicases = apicasesService.GetCaseByCaseID(CaseID);
            if (apicases == null) {
                throw new Exception("接口子条件执行异常:接口子条件未找到条件运行的接口，请检查是否存在或已被删除！");
            }
            Long ApiID = apicases.getApiid();
            Api api = apiService.getBy("id", ApiID);
            if (api == null) {
                throw new Exception("接口子条件执行异常:接口子条件未找到条件运行的接口的API，请检查是否存在或已被删除！");
            }
            Long Deployunitid = api.getDeployunitid();
            Deployunit deployunit = deployunitService.getBy("id", Deployunitid);
            if (deployunit == null) {
                throw new Exception("接口子条件执行异常:接口子条件未找到条件运行接口API所在的发布单元，请检查是否存在或已被删除！");
            }
            List<ApiCasedata> apiCasedataList = apiCasedataService.GetCaseDatasByCaseID(CaseID);
            //区分环境类型
            Macdepunit macdepunit = macdepunitService.getmacdepbyenvidanddepid(EnviromentID, deployunit.getId());
            if (macdepunit == null) {
                throw new Exception("接口子条件执行异常:接口子条件未找到环境组件部署，请检查是否存在或已被删除！");
            }
            Machine machine = machineService.getBy("id", macdepunit.getMachineid());
            if (machine == null) {
                throw new Exception("接口子条件执行异常:接口子条件未找到环境组件部署的服务器，请检查是否存在或已被删除！");
            }
            TestCaseHelp testCaseHelp = new TestCaseHelp();
            RequestObject requestObject = testCaseHelp.GetCaseRequestData(apiCasedataList, api, apicases, deployunit, macdepunit, machine);
            ResponeData testResponeData = testCaseHelp.request(requestObject);
            String Respone= testResponeData.getContent();
            //根据用例是否有中间变量，如果有变量，解析（json，xml，html）保存变量值表，没有变量直接保存条件结果表
            ApicasesVariables apicasesVariables = apicasesVariablesService.getBy("caseid", apicases.getId());
            if (apicasesVariables != null) {
                ParseResponeHelp parseResponeHelp = new ParseResponeHelp();
                Testvariables testvariables = testvariablesService.getById(apicasesVariables.getVariablesid());
                if (testvariables != null) {
                    String ParseValue = parseResponeHelp.ParseRespone(requestObject.getResponecontenttype(), Respone, testvariables.getVariablesexpress());
                    VariableNameValueMap.put(testvariables.getTestvariablesname(), ParseValue);
                }
                else
                {
                    throw new Exception("接口子条件执行异常:接口子条件未找到变量:"+apicasesVariables.getVariablesname()+"，请检查变量管理-变量管理中是否存在！");
                }
            }
            else
            {
                throw new Exception("接口子条件执行异常:接口子条件未找到接口:"+apicases.getCasename()+",和变量关系，请检查变量管理-用例变量中是否存在！");
            }
        }
        return ResultGenerator.genOkResult(VariableNameValueMap);
    }

    @PostMapping("/execcasecondition/db")
    public Result ConditionForDB(@RequestBody final Map<String, Object> param) throws Exception {
        Long ConditionID = Long.parseLong(param.get("ConditionID").toString());
        List<ConditionDb> conditionDbListList = conditionDbService.GetDBConditionByConditionID(ConditionID);
        for (ConditionDb conditionDb : conditionDbListList) {
            Long Assembleid = conditionDb.getAssembleid();
            EnviromentAssemble enviromentAssemble = enviromentAssembleService.getBy("id", Assembleid);
            if (enviromentAssemble == null) {
                throw new Exception("数据库子条件执行异常:数据库子条件未找到环境组件，请检查是否存在或已被删除！");
            }
            String Respone = "";
            String AssembleType = enviromentAssemble.getAssembletype();
            Long Envid = conditionDb.getEnviromentid();
            String Sql = conditionDb.getDbcontent();
            String ConnnectStr = enviromentAssemble.getConnectstr();
            Macdepunit macdepunit = macdepunitService.getmacdepbyenvidandassmbleid(Envid, Assembleid);
            if (macdepunit == null) {
                throw new Exception("数据库子条件执行异常:数据库子条件未找到环境组件部署，请检查是否存在或已被删除");
            }
            Machine machine = machineService.getBy("id", macdepunit.getMachineid());
            if (machine == null) {
                throw new Exception("数据库子条件执行异常:数据库子条件未找到环境组件部署的服务器，请检查是否存在或已被删除");
            }
            String deployunitvisittype = macdepunit.getVisittype();
            String[] ConnetcArray = ConnnectStr.split(",");
            if (ConnetcArray.length < 4) {
                throw new Exception("数据库子条件执行异常:数据库子条件数据库连接字填写不规范，请按规则填写," + ConnnectStr);
            }
            try {
                Respone = Rundb(ConnetcArray, AssembleType, deployunitvisittype, machine, macdepunit, Sql);
//                String username = ConnetcArray[0];
//                String pass = ConnetcArray[1];
//                String port = ConnetcArray[2];
//                String dbname = ConnetcArray[3];
//                String DBUrl = "";
//                if (AssembleType.equals("mysql")) {
//                    DBUrl = "jdbc:mysql://";
//                    // 根据访问方式来确定ip还是域名
//                    if (deployunitvisittype.equals("ip")) {
//                        String IP = machine.getIp();
//                        DBUrl = DBUrl + IP + ":" + port + "/" + dbname + "?useUnicode=true&useSSL=false&allowMultiQueries=true&characterEncoding=utf-8&useLegacyDatetimeCode=false&serverTimezone=UTC";
//                    } else {
//                        String Domain = macdepunit.getDomain();
//                        DBUrl = DBUrl + Domain + "/" + dbname + "?useUnicode=true&useSSL=false&allowMultiQueries=true&characterEncoding=utf-8&useLegacyDatetimeCode=false&serverTimezone=UTC";
//                    }
//                }
//                if (AssembleType.equals("oracle")) {
//                    DBUrl = "jdbc:oracle:thin:@";
//                    // 根据访问方式来确定ip还是域名
//                    if (deployunitvisittype.equals("ip")) {
//                        String IP = machine.getIp();
//                        DBUrl = DBUrl + IP + ":" + port + ":" + dbname;
//                    } else {
//                        String Domain = macdepunit.getDomain();
//                        DBUrl = DBUrl + Domain + ":" + dbname;
//                    }
//                }
//                DataSource ds = new SimpleDataSource(DBUrl, username, pass);
//                String Respone = "";
//                String[] SqlArr = Sql.split(";");
//                for (String ExecSql : SqlArr) {
//                    int nums = Db.use(ds).execute(ExecSql);
//                    Respone = Respone + " 成功执行Sql:" + Sql + " 影响条数：" + nums;
//                    TestconditionController.log.info("调试数据库子条件Sql执行完成：" + Sql);
//                }
                return ResultGenerator.genOkResult(Respone);
            } catch (Exception ex) {
                throw new Exception("数据库子条件执行异常：" + ex.getMessage());
                //return ResultGenerator.genFailedResult("数据库子条件执行异常：" + ex.getMessage());
            }
        }
        TestconditionController.log.info("调试数据库子条件条件报告子条件完成-============：");
        return ResultGenerator.genOkResult("调试数据库子条件执行完成");
    }

    public void DBCondition(long ConditionID, Dispatch dispatch) {
        List<ConditionDb> conditionDbListList = conditionDbService.GetDBConditionByConditionID(ConditionID);
        TestconditionController.log.info("数据库子条件条件报告数据库子条件数量-============：" + conditionDbListList.size());
        for (ConditionDb conditionDb : conditionDbListList) {
            TestconditionReport testconditionReport = new TestconditionReport();
            testconditionReport.setTestplanid(dispatch.getExecplanid());
            testconditionReport.setPlanname(dispatch.getExecplanname());
            testconditionReport.setBatchname(dispatch.getBatchname());
            testconditionReport.setConditionid(new Long(ConditionID));
            testconditionReport.setConditiontype("前置条件");
            testconditionReport.setConditionresult("");
            testconditionReport.setConditionstatus("");
            testconditionReport.setRuntime(new Long(0));
            testconditionReport.setSubconditionid(conditionDb.getId());
            testconditionReport.setSubconditionname(conditionDb.getSubconditionname());
            testconditionReport.setSubconditiontype("数据库");
            testconditionReport.setStatus("进行中");
            TestconditionController.log.info("数据库子条件条件报告保存子条件进行中状态-============：" + testconditionReport.getPlanname() + "|" + testconditionReport.getBatchname() + "|" + conditionDb.getSubconditionname());
            testconditionReportService.save(testconditionReport);

            String Respone = "";
            long Start = 0;
            long End = 0;
            long CostTime = 0;
            String ConditionResultStatus = "成功";
            Long Assembleid = conditionDb.getAssembleid();
            EnviromentAssemble enviromentAssemble = enviromentAssembleService.getBy("id", Assembleid);
            if (enviromentAssemble == null) {
                Respone = "未找到环境组件，请检查是否存在或已被删除";
                ConditionResultStatus = "失败";
                UpdatetestconditionReport(testconditionReport, Respone, ConditionResultStatus, new Long(0),conditionDb.getCreator());
                break;
            }
            String AssembleType = enviromentAssemble.getAssembletype();
            Long Envid = conditionDb.getEnviromentid();
            String Dbtype = conditionDb.getDbtype();
            String Sql = conditionDb.getDbcontent();
            String ConnnectStr = enviromentAssemble.getConnectstr();
            Macdepunit macdepunit = macdepunitService.getmacdepbyenvidandassmbleid(Envid, Assembleid);
            if (macdepunit == null) {
                Respone = "未找到环境组件部署，请检查是否存在或已被删除";
                ConditionResultStatus = "失败";
                UpdatetestconditionReport(testconditionReport, Respone, ConditionResultStatus, new Long(0),conditionDb.getCreator());
                break;
            }
            Machine machine = machineService.getBy("id", macdepunit.getMachineid());
            if (machine == null) {
                Respone = "未找到环境组件部署的服务器，请检查是否存在或已被删除";
                ConditionResultStatus = "失败";
                UpdatetestconditionReport(testconditionReport, Respone, ConditionResultStatus, new Long(0),conditionDb.getCreator());
                break;
            }
            String deployunitvisittype = macdepunit.getVisittype();
            String[] ConnetcArray = ConnnectStr.split(",");
            if (ConnetcArray.length < 4) {
                Respone = "数据库连接字填写不规范，请按规则填写";
                ConditionResultStatus = "失败";
                UpdatetestconditionReport(testconditionReport, Respone, ConditionResultStatus, new Long(0),conditionDb.getCreator());
                break;
            }
            try {
                Start = new Date().getTime();
                Respone = Rundb(ConnetcArray, AssembleType, deployunitvisittype, machine, macdepunit, Sql);
            } catch (Exception ex) {
                ConditionResultStatus = "失败";
                Respone = ex.getMessage();
            } finally {
                End = new Date().getTime();
                CostTime = End - Start;
                //更新条件结果表
                UpdatetestconditionReport(testconditionReport, Respone, ConditionResultStatus, CostTime,conditionDb.getCreator());
                TestconditionController.log.info("数据库子条件条件报告子条件完成-============：");
            }
        }
    }

    private String Rundb(String[] ConnetcArray, String AssembleType, String deployunitvisittype, Machine machine, Macdepunit macdepunit, String Sql) throws Exception {
        String Respone = "";

        String username = ConnetcArray[0];
        String pass = ConnetcArray[1];
        String port = ConnetcArray[2];
        String dbname = ConnetcArray[3];
        String DBUrl = "";
        if (AssembleType.equals("mysql")) {
            DBUrl = "jdbc:mysql://";
            // 根据访问方式来确定ip还是域名
            if (deployunitvisittype.equals("ip")) {
                String IP = machine.getIp();
                DBUrl = DBUrl + IP + ":" + port + "/" + dbname + "?useUnicode=true&useSSL=false&allowMultiQueries=true&characterEncoding=utf-8&useLegacyDatetimeCode=false&serverTimezone=UTC";
            } else {
                String Domain = macdepunit.getDomain();
                DBUrl = DBUrl + Domain + "/" + dbname + "?useUnicode=true&useSSL=false&allowMultiQueries=true&characterEncoding=utf-8&useLegacyDatetimeCode=false&serverTimezone=UTC";
            }
        }
        if (AssembleType.equals("oracle")) {
            DBUrl = "jdbc:oracle:thin:@";
            // 根据访问方式来确定ip还是域名
            if (deployunitvisittype.equals("ip")) {
                String IP = machine.getIp();
                DBUrl = DBUrl + IP + ":" + port + ":" + dbname;
            } else {
                String Domain = macdepunit.getDomain();
                DBUrl = DBUrl + Domain + ":" + dbname;
            }
        }
        DataSource ds = new SimpleDataSource(DBUrl, username, pass);
        String[] SqlArr = Sql.split(";");
        //Db.use(ds).getConnection().setAutoCommit(false);
//        try
//        {
        for (String ExecSql : SqlArr) {
            TestconditionController.log.info("数据库子条件Sql开始执行：" + ExecSql);
            int nums = Db.use(ds).execute(ExecSql);
            TestconditionController.log.info("数据库子条件Sql执行完成：" + ExecSql);
            Respone = Respone + " 成功执行Sql:" + Sql + " 影响条数：" + nums;
        }
        //Db.use(ds).getConnection().commit();
        // }
//        catch (Exception ex)
//        {
//            Db.use(ds).getConnection().rollback();
//            throw new Exception(ex.getMessage());
//        }
//        finally {
//            System.out.println("Connection is:"+Db.use(ds).getConnection());
//            Db.use(ds).getConnection().close();
//            System.out.println("Connection is h:"+Db.use(ds).getConnection());
//
//        }
        return Respone;
    }

    private void UpdatetestconditionReport(TestconditionReport testconditionReport, String Respone, String ConditionResultStatus, Long CostTime,String user) {
        //更新条件结果表
        testconditionReport.setConditionresult(Respone);
        testconditionReport.setConditionstatus(ConditionResultStatus);
        testconditionReport.setRuntime(CostTime);
        testconditionReport.setStatus("已完成");
        testconditionReportService.update(testconditionReport);

        //当结果为失败的情况发邮件通知用户
        if(ConditionResultStatus.equals("失败"))
        {
            try
            {
                List<Dictionary>dictionaryList= dictionaryService.findDicNameValueWithCode("Mail");
                if(dictionaryList.size()>0)
                {
                    Dictionary dictionary=dictionaryList.get(0);
                    String MailInfo=dictionary.getDicitmevalue();
                    String[] MailArray=MailInfo.split(",");
                    if(MailArray.length>4)
                    {
                        String Smtp=MailArray[0];
                        int port=Integer.parseInt(MailArray[1]);
                        String from=MailArray[2];
                        String mailuser=MailArray[3];
                        String pass=MailArray[4];

                        MailAccount account = new MailAccount();
                        account.setHost(Smtp);
                        account.setPort(port);
                        account.setAuth(true);
                        account.setFrom(from);
                        account.setUser(mailuser);
                        account.setPass(pass);

                        List<Account>accountList= accountService.findWithUsername(user);
                        String mailto="";
                        if(accountList.size()>0)
                        {
                            mailto=accountList.get(0).getEmail();
                        }
                        String Subject=testconditionReport.getPlanname()+"|"+testconditionReport.getBatchname()+"前置子条件执行失败："+testconditionReport.getSubconditionname();
                        String Content="失败原因："+Respone+" ,前置子条件执行失败会导致测试集合所有用例停止运行，请及时前后AutoMeter处理！";
                        MailUtil.send(account, CollUtil.newArrayList(mailto), Subject, Content, false);
                        TestconditionController.log.info("发送邮件成功-============："+mailto);
                    }
                }
            }
            catch (Exception ex)
            {
                TestconditionController.log.info("发送邮件异常-============："+ex.getMessage());
            }
        }
    }

    public void ScriptCondition(Long Caseid, Dispatch dispatch, Long ConditionID) {
        ConditionScript conditionScript = conditionScriptService.findtestconditionscriptwithid(ConditionID);
        if (conditionScript != null) {
            TestconditionController.log.info("脚本条件id：-============：" + conditionScript.getId());
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
            testconditionReport.setSubconditionname(conditionScript.getSubconditionname());
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
