package com.api.autotest.core;

import cn.hutool.db.Db;
import cn.hutool.db.ds.simple.SimpleDataSource;
import com.api.autotest.common.utils.DnamicCompilerHelp;
import com.api.autotest.dto.RequestObject;
import com.api.autotest.dto.ResponeData;
import com.api.autotest.dto.TestconditionReport;
import com.api.autotest.dto.TestvariablesValue;
import org.apache.log.Logger;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static com.api.autotest.core.TestCaseData.logplannameandcasename;


public class TestCondition {

    private Logger logger = null;
    TestMysqlHelp testMysqlHelp=null;
    private TestCaseData testCaseData = null;
    private TestHttpRequestData testHttpRequestData = null;
    private TestHttp testHttp = null;

    public TestCondition(Logger log,TestMysqlHelp mysqlHelp,TestCaseData casedata,TestHttpRequestData httprequestdata,TestHttp ttHttp)
    {
        testMysqlHelp=mysqlHelp;
        testCaseData=casedata;
        testHttpRequestData=httprequestdata;
        testHttp = ttHttp;
        logger=log;
    }
    //处理接口条件
    public void APICondition(long ConditionID, RequestObject requestObject) throws Exception {
        ArrayList<HashMap<String, String>> conditionApiList = testMysqlHelp.GetApiConditionByConditionID(ConditionID);
        Long PlanID = Long.parseLong(requestObject.getTestplanid());
        logger.info("TestCondition条件报告API子条件数量-============：" + conditionApiList.size());
        for (HashMap<String, String> conditionApi : conditionApiList) {
            long Start = 0;
            long End = 0;
            long CostTime = 0;
            String Respone = "";
            String ConditionResultStatus = "成功";
            RequestObject re = new RequestObject();
            String CondionCaseID = "";
            try {
                CondionCaseID = conditionApi.get("caseid");
                Start = new Date().getTime();

                re = testCaseData.GetCaseRequestData(requestObject.getTestplanid(), CondionCaseID, requestObject.getSlaverid(), requestObject.getBatchid(), requestObject.getBatchname(), requestObject.getTestplanname());
                re = testHttpRequestData.GetFuntionHttpRequestData(re);
                End = new Date().getTime();
                ResponeData responeData = testHttp.doService(re);
                Respone = responeData.getRespone();
                CostTime = End - Start;
                SaveApiSubCondition(re, requestObject.getCasename(), PlanID, requestObject.getTestplanname(), requestObject.getBatchname(), Long.parseLong(CondionCaseID), ConditionID, conditionApi, Respone, ConditionResultStatus, CostTime);
            } catch (Exception ex) {
                ConditionResultStatus = "失败";
                End = new Date().getTime();
                Respone = ex.getMessage();
                CostTime = End - Start;
                SaveApiSubCondition(re,requestObject.getCasename(), PlanID, requestObject.getTestplanname(), requestObject.getBatchname(), Long.parseLong(CondionCaseID), ConditionID, conditionApi, Respone, ConditionResultStatus, CostTime);
                throw new Exception("接口子条件执行异常：" + ex.getMessage());
            }
        }
    }

    private void SaveApiSubCondition(RequestObject requestObject, String CaseName, Long PlanID, String PlanName, String BatchName, Long CaseID, Long ConditionID, HashMap<String, String> conditionApi, String Respone, String ConditionResultStatus, long CostTime) {
        TestconditionReport testconditionReport = new TestconditionReport();
        testconditionReport.setTestplanid(PlanID);
        testconditionReport.setPlanname(CaseName);
        testconditionReport.setBatchname(BatchName);
        testconditionReport.setConditionid(new Long(ConditionID));
        testconditionReport.setConditiontype("前置条件");
        testconditionReport.setConditionresult(Respone);
        testconditionReport.setConditionstatus(ConditionResultStatus);
        testconditionReport.setRuntime(CostTime);
        testconditionReport.setSubconditionid(Long.parseLong(conditionApi.get("id")));
        testconditionReport.setSubconditionname(conditionApi.get("subconditionname"));
        testconditionReport.setSubconditiontype("接口");
        testconditionReport.setStatus("已完成");
        logger.info("TestCondition条件报告保存子条件已完成状态-============：" + testconditionReport.getPlanname() + "|" + testconditionReport.getBatchname() + "|" + requestObject.getCasename());
        testMysqlHelp.SubConditionReportSave(testconditionReport);
        //根据用例是否有中间变量，如果有变量，解析（json，xml，html）保存变量值表，没有变量直接保存条件结果表
        ArrayList<HashMap<String, String>> apicasesVariablesList = testMysqlHelp.GetApiCaseVaribales(CaseID);
        if (apicasesVariablesList.size() > 0) {
            logger.info("TestCondition条件报告子条件处理变量-============：" + apicasesVariablesList.get(0).get("variablesname"));
            String Variablesid = apicasesVariablesList.get(0).get("id");
            ArrayList<HashMap<String, String>> VariablesList = testMysqlHelp.GetVaribales(Variablesid);
            if (VariablesList.size() > 0) {
                String VariablesPath = VariablesList.get(0).get("variablesexpress");
                logger.info("TestCondition条件报告子条件处理变量表达式-============：" + VariablesPath + " 响应数据类型" + requestObject.getResponecontenttype());
                TestAssert testAssert = new TestAssert(logger);
                String ParseValue = testAssert.ParseRespone(requestObject.getResponecontenttype(), VariablesPath, Respone);
                logger.info("TestCondition条件报告子条件处理变量取值-============：" + ParseValue);
                TestvariablesValue testvariablesValue = new TestvariablesValue();
                testvariablesValue.setPlanid(PlanID);
                testvariablesValue.setPlanname(PlanName);
                testvariablesValue.setBatchname(BatchName);
                testvariablesValue.setCaseid(CaseID);
                testvariablesValue.setCasename(requestObject.getCasename());
                testvariablesValue.setVariablesid(Long.parseLong(VariablesList.get(0).get("id")));
                testvariablesValue.setVariablesname(VariablesList.get(0).get("testvariablesname"));
                testvariablesValue.setVariablesvalue(ParseValue);
                testvariablesValue.setMemo("test");
                testMysqlHelp.testVariablesValueSave(testvariablesValue);
            }
        }
    }
    //处理脚本条件
    public void ScriptCondition(long ConditionID, RequestObject requestObject) throws Exception {
        Long PlanID = Long.parseLong(requestObject.getTestplanid());
        Long CaseID = Long.parseLong(requestObject.getCaseid());
        ArrayList<HashMap<String, String>> conditionScriptList = testMysqlHelp.GetScriptConditionByConditionID(ConditionID);
        for (HashMap<String, String> conditionScript : conditionScriptList) {
            long Start = 0;
            long End = 0;
            long CostTime = 0;
            String Respone = "执行脚本成功";
            String ConditionResultStatus = "成功";
            try {
                Start = new Date().getTime();
                DnamicCompilerHelp dnamicCompilerHelp = new DnamicCompilerHelp();
                //数据库中获取脚本
                String JavaSource = conditionScript.get("script");
                logger.info("TestCondition条件报告脚本子条件:-============：" + JavaSource);
                String Source = dnamicCompilerHelp.GetCompeleteClass(JavaSource, CaseID);
                dnamicCompilerHelp.CallDynamicScript(Source);
                End = new Date().getTime();
                CostTime = End - Start;
                SaveSubCondition("脚本", requestObject, PlanID, ConditionID, conditionScript, Respone, ConditionResultStatus, CostTime);
            } catch (Exception ex) {
                ConditionResultStatus = "失败";
                Respone = ex.getMessage();
                End = new Date().getTime();
                CostTime = End - Start;
                SaveSubCondition("脚本", requestObject, PlanID, ConditionID, conditionScript, Respone, ConditionResultStatus, CostTime);
                throw new Exception("脚本子条件执行异常：" + ex.getMessage());
            }
        }
    }

    private void SaveSubCondition(String SubconditionType, RequestObject requestObject, Long PlanID, Long ConditionID, HashMap<String, String> conditionScript, String Respone, String ConditionResultStatus, long CostTime) {
        //更新条件结果表
        TestconditionReport testconditionReport = new TestconditionReport();
        testconditionReport.setTestplanid(PlanID);
        testconditionReport.setPlanname(requestObject.getCasename());
        testconditionReport.setBatchname(requestObject.getBatchname());
        testconditionReport.setConditionid(new Long(ConditionID));
        testconditionReport.setConditiontype("前置条件");
        testconditionReport.setSubconditionid(Long.parseLong(conditionScript.get("id")));
        testconditionReport.setSubconditionname(conditionScript.get("subconditionname"));
        testconditionReport.setSubconditiontype(SubconditionType);
        logger.info("TestCondition "+SubconditionType + "条件报告保存子条件进行中状态-============：" + testconditionReport.getPlanname() + "|" + testconditionReport.getBatchname());

        testconditionReport.setConditionresult(Respone.replace("'","''"));
        testconditionReport.setConditionstatus(ConditionResultStatus);
        testconditionReport.setRuntime(CostTime);
        testconditionReport.setStatus("已完成");
        testMysqlHelp.SubConditionReportSave(testconditionReport);
        logger.info("TestCondition "+SubconditionType + "条件报告更新子条件结果-============：" + testconditionReport.getPlanname() + "|" + testconditionReport.getBatchname());

    }

    public void DBCondition(long ConditionID, RequestObject requestObject) throws Exception {
        Long PlanID = Long.parseLong(requestObject.getTestplanid());
        ArrayList<HashMap<String, String>> conditionDbListList = testMysqlHelp.GetDBConditionByConditionID(ConditionID);
        for (HashMap<String, String> conditionDb : conditionDbListList) {
            long Start = 0;
            long End = 0;
            long CostTime = 0;
            String Respone = "";
            String ConditionResultStatus = "成功";
            Long Assembleid = Long.parseLong(conditionDb.get("assembleid"));
            try {
                ArrayList<HashMap<String, String>> enviromentAssemblelist = testMysqlHelp.getcaseData("select * from enviroment_assemble where id=" + Assembleid);
                if (enviromentAssemblelist.size() == 0) {
                    Respone = "未找到环境组件，请检查是否存在或已被删除";
                    ConditionResultStatus = "失败";
                    SaveSubCondition("数据库", requestObject, PlanID, ConditionID, conditionDb, Respone, ConditionResultStatus, CostTime);
                    break;
                }
                String AssembleType = enviromentAssemblelist.get(0).get("assembletype");
                Long Envid = Long.parseLong(conditionDb.get("enviromentid"));
                String Sql = conditionDb.get("dbcontent");
                logger.info(logplannameandcasename + "TestCondition数据库子条件完整的sql ....." + Sql);
                String ConnnectStr = enviromentAssemblelist.get(0).get("connectstr");
                ArrayList<HashMap<String, String>> macdepunitlist = testMysqlHelp.getcaseData("select * from macdepunit where envid=" + Envid + " and assembleid=" + Assembleid);
                if (macdepunitlist.size() == 0) {
                    Respone = "未找到环境组件部署，请检查是否存在或已被删除";
                    ConditionResultStatus = "失败";
                    SaveSubCondition("数据库", requestObject, PlanID, ConditionID, conditionDb, Respone, ConditionResultStatus, CostTime);
                    break;
                }

                Long MachineID = Long.parseLong(macdepunitlist.get(0).get("machineid"));
                ArrayList<HashMap<String, String>> machinelist = testMysqlHelp.getcaseData("select * from machine where id=" + MachineID);
                if (machinelist.size() == 0) {
                    Respone = "未找到环境组件部署的服务器，请检查是否存在或已被删除";
                    ConditionResultStatus = "失败";
                    SaveSubCondition("数据库", requestObject, PlanID, ConditionID, conditionDb, Respone, ConditionResultStatus, CostTime);
                    break;
                }
                String deployunitvisittype = macdepunitlist.get(0).get("visittype");
                String[] ConnetcArray = ConnnectStr.split(",");
                if (ConnetcArray.length < 4) {
                    Respone = "数据库连接字填写不规范，请按规则填写";
                    ConditionResultStatus = "失败";
                    SaveSubCondition("数据库", requestObject, PlanID, ConditionID, conditionDb, Respone, ConditionResultStatus, CostTime);
                    break;
                }
                String username = ConnetcArray[0];
                String pass = ConnetcArray[1];
                String port = ConnetcArray[2];
                String dbname = ConnetcArray[3];
                String DBUrl = "";
                if (AssembleType.equals("mysql")) {
                    DBUrl = "jdbc:mysql://";
                    // 根据访问方式来确定ip还是域名
                    if (deployunitvisittype.equals("ip")) {
                        String IP = machinelist.get(0).get("ip");
                        DBUrl = DBUrl + IP + ":" + port + "/" + dbname + "?useUnicode=true&useSSL=false&allowMultiQueries=true&characterEncoding=utf-8&useLegacyDatetimeCode=false&serverTimezone=UTC";
                    } else {
                        String Domain = macdepunitlist.get(0).get("domain");
                        DBUrl = DBUrl + Domain + "/" + dbname + "?useUnicode=true&useSSL=false&allowMultiQueries=true&characterEncoding=utf-8&useLegacyDatetimeCode=false&serverTimezone=UTC";
                    }
                }
                if (AssembleType.equals("oracle")) {
                    DBUrl = "jdbc:oracle:thin:@";
                    // 根据访问方式来确定ip还是域名
                    if (deployunitvisittype.equals("ip")) {
                        String IP = machinelist.get(0).get("ip");
                        DBUrl = DBUrl + IP + ":" + port + ":" + dbname ;
                    } else {
                        String Domain = macdepunitlist.get(0).get("domain");
                        DBUrl = DBUrl + Domain + ":" + dbname ;
                    }
                }
                Start = new Date().getTime();
                DataSource ds = new SimpleDataSource(DBUrl, username, pass);

                String[] SqlArr = Sql.split(";");
                for (String ExecSql : SqlArr) {
                    logger.info(logplannameandcasename + "TestCondition数据库子条件执行sql ....." + ExecSql);
                    if((!ExecSql.isEmpty())||(ExecSql.equals("")))
                    {
                        int nums = Db.use(ds).execute(ExecSql);
                        Respone = Respone + " 成功执行Sql:" + Sql + " 影响条数：" + nums;
                    }
                }
            } catch (Exception ex) {
                ConditionResultStatus = "失败";
                Respone = ex.getMessage();
                throw new Exception("数据库子条件执行异常：" + ex.getMessage());
            } finally {
                End = new Date().getTime();
                CostTime = End - Start;
                //更新条件结果表
                SaveSubCondition("数据库", requestObject, PlanID, ConditionID, conditionDb, Respone, ConditionResultStatus, CostTime);
            }
        }
    }

}
