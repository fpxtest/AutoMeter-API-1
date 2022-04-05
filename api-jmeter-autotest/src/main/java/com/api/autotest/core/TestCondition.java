package com.api.autotest.core;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.simple.SimpleDataSource;
import com.api.autotest.common.utils.DnamicCompilerHelp;
import com.api.autotest.common.utils.PgsqlConnectionUtils;
import com.api.autotest.dto.*;
import org.apache.http.Header;
import org.apache.log.Logger;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.api.autotest.core.TestCaseData.logplannameandcasename;


public class TestCondition {

    private Logger logger = null;
    TestMysqlHelp testMysqlHelp = null;
    private TestCaseData testCaseData = null;
    private TestHttpRequestData testHttpRequestData = null;
    private TestHttp testHttp = null;

    public TestCondition(Logger log, TestMysqlHelp mysqlHelp, TestCaseData casedata, TestHttpRequestData httprequestdata, TestHttp ttHttp) {
        testMysqlHelp = mysqlHelp;
        testCaseData = casedata;
        testHttpRequestData = httprequestdata;
        testHttp = ttHttp;
        logger = log;
    }


    //接口子条件处理
    public void conditionapi(HashMap<String, String> conditionApi,long ConditionID, RequestObject requestObject,long PlanID) throws Exception {
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
            TestResponeData responeData = testHttp.doService(re,30000);
            Respone = responeData.getResponeContent();

            String ResponeContentType = "application/json;charset=utf-8";
            List<Header> responeheaderlist = responeData.getHeaderList();
            for (Header head : responeheaderlist) {
                if (head.getName().equalsIgnoreCase("Content-Type")) {
                    ResponeContentType = head.getValue();
                }
            }
            requestObject.setResponecontenttype(ResponeContentType);
            CostTime = End - Start;
            SaveApiSubCondition(re, requestObject.getCasename(), PlanID, requestObject.getTestplanname(), requestObject.getBatchname(), Long.parseLong(CondionCaseID), ConditionID, conditionApi, Respone, ConditionResultStatus, CostTime);
        } catch (Exception ex) {
            ConditionResultStatus = "失败";
            End = new Date().getTime();
            Respone = ex.getMessage();
            CostTime = End - Start;
            SaveApiSubCondition(re, requestObject.getCasename(), PlanID, requestObject.getTestplanname(), requestObject.getBatchname(), Long.parseLong(CondionCaseID), ConditionID, conditionApi, Respone, ConditionResultStatus, CostTime);
            throw new Exception("接口子条件执行异常：" + ex.getMessage());
        }
    }

    //接口条件入口
    public void APICondition(long ConditionID, RequestObject requestObject) throws Exception {
        ArrayList<HashMap<String, String>> conditionApiList = testMysqlHelp.GetApiConditionByConditionID(ConditionID);
        Long PlanID = Long.parseLong(requestObject.getTestplanid());
        logger.info("TestCondition条件报告API子条件数量-============：" + conditionApiList.size());
        for (HashMap<String, String> conditionApi : conditionApiList) {
            conditionapi(conditionApi,ConditionID,requestObject,PlanID);
//            long Start = 0;
//            long End = 0;
//            long CostTime = 0;
//            String Respone = "";
//            String ConditionResultStatus = "成功";
//            RequestObject re = new RequestObject();
//            String CondionCaseID = "";
//            try {
//                CondionCaseID = conditionApi.get("caseid");
//                Start = new Date().getTime();
//                re = testCaseData.GetCaseRequestData(requestObject.getTestplanid(), CondionCaseID, requestObject.getSlaverid(), requestObject.getBatchid(), requestObject.getBatchname(), requestObject.getTestplanname());
//                re = testHttpRequestData.GetFuntionHttpRequestData(re);
//                End = new Date().getTime();
//                TestResponeData responeData = testHttp.doService(re,30000);
//                Respone = responeData.getResponeContent();
//
//                String ResponeContentType = "application/json;charset=utf-8";
//                List<Header> responeheaderlist = responeData.getHeaderList();
//                for (Header head : responeheaderlist) {
//                    if (head.getName().equalsIgnoreCase("Content-Type")) {
//                        ResponeContentType = head.getValue();
//                    }
//                }
//                requestObject.setResponecontenttype(ResponeContentType);
//                CostTime = End - Start;
//                SaveApiSubCondition(re, requestObject.getCasename(), PlanID, requestObject.getTestplanname(), requestObject.getBatchname(), Long.parseLong(CondionCaseID), ConditionID, conditionApi, Respone, ConditionResultStatus, CostTime);
//            } catch (Exception ex) {
//                ConditionResultStatus = "失败";
//                End = new Date().getTime();
//                Respone = ex.getMessage();
//                CostTime = End - Start;
//                SaveApiSubCondition(re, requestObject.getCasename(), PlanID, requestObject.getTestplanname(), requestObject.getBatchname(), Long.parseLong(CondionCaseID), ConditionID, conditionApi, Respone, ConditionResultStatus, CostTime);
//                throw new Exception("接口子条件执行异常：" + ex.getMessage());
//            }
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

        //增加判断是否已经存在
        testMysqlHelp.SubConditionReportSave(testconditionReport);
        //根据用例是否有中间变量(多个)，如果有变量，解析（json，xml，html）保存变量值表，没有变量直接保存条件结果表
        ArrayList<HashMap<String, String>> apicasesVariablesList = testMysqlHelp.GetApiCaseVaribales(CaseID);
        if (apicasesVariablesList.size() > 0) {
            for (HashMap<String, String> map : apicasesVariablesList) {
                for (String Key : map.keySet()) {
                    if (Key.equalsIgnoreCase("variablesid")) {
                        logger.info("TestCondition条件报告子条件处理变量-============：" + map.get("variablesname"));
                        String Variablesid = map.get(Key);
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
                            testvariablesValue.setVariablestype("接口");
                            testvariablesValue.setCasename(requestObject.getCasename());
                            testvariablesValue.setVariablesid(Long.parseLong(Variablesid));
                            testvariablesValue.setVariablesname(map.get("variablesname"));
                            testvariablesValue.setVariablesvalue(ParseValue);
                            testvariablesValue.setMemo("test");
                            //增加判断是否已经存在
                            testMysqlHelp.testVariablesValueSave(testvariablesValue);
                        }
                    }
                }
            }
        }
    }

    //处理脚本子条件
    public void conditionscript(HashMap<String, String> conditionScript, long ConditionID, RequestObject requestObject,Long PlanID,Long CaseID) throws Exception {
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

    //处理脚本条件入口
    public void ScriptCondition(long ConditionID, RequestObject requestObject) throws Exception {
        Long PlanID = Long.parseLong(requestObject.getTestplanid());
        Long CaseID = Long.parseLong(requestObject.getCaseid());
        ArrayList<HashMap<String, String>> conditionScriptList = testMysqlHelp.GetScriptConditionByConditionID(ConditionID);
        for (HashMap<String, String> conditionScript : conditionScriptList) {
            conditionscript(conditionScript,ConditionID,requestObject,PlanID,CaseID);
//            long Start = 0;
//            long End = 0;
//            long CostTime = 0;
//            String Respone = "执行脚本成功";
//            String ConditionResultStatus = "成功";
//            try {
//                Start = new Date().getTime();
//                DnamicCompilerHelp dnamicCompilerHelp = new DnamicCompilerHelp();
//                //数据库中获取脚本
//                String JavaSource = conditionScript.get("script");
//                logger.info("TestCondition条件报告脚本子条件:-============：" + JavaSource);
//                String Source = dnamicCompilerHelp.GetCompeleteClass(JavaSource, CaseID);
//                dnamicCompilerHelp.CallDynamicScript(Source);
//                End = new Date().getTime();
//                CostTime = End - Start;
//                SaveSubCondition("脚本", requestObject, PlanID, ConditionID, conditionScript, Respone, ConditionResultStatus, CostTime);
//            } catch (Exception ex) {
//                ConditionResultStatus = "失败";
//                Respone = ex.getMessage();
//                End = new Date().getTime();
//                CostTime = End - Start;
//                SaveSubCondition("脚本", requestObject, PlanID, ConditionID, conditionScript, Respone, ConditionResultStatus, CostTime);
//                throw new Exception("脚本子条件执行异常：" + ex.getMessage());
//            }
        }
    }


    //延时子条件处理
    public void conditiondelay(HashMap<String, String> conditionDelay,long ConditionID, RequestObject requestObject,Long PlanID) throws Exception {
        long Start = 0;
        long End = 0;
        long CostTime = 0;
        String Respone = "执行延时条件成功";
        String ConditionResultStatus = "成功";
        try {
            Start = new Date().getTime();
            long delaytime = Long.parseLong(conditionDelay.get("delaytime"))*1000;
            logger.info("TestCondition条件报告延时子条件，延时时间为（毫秒）:-============：" + delaytime);
            Thread.sleep(delaytime);
            Respone = Respone + "（毫秒）:" + delaytime;
            logger.info("TestCondition条件报告延时子条件，延时时间为（毫秒）:-============：" + Respone);
            End = new Date().getTime();
            CostTime = End - Start;
            SaveSubCondition("延时", requestObject, PlanID, ConditionID, conditionDelay, Respone, ConditionResultStatus, CostTime);
        } catch (Exception ex) {
            ConditionResultStatus = "失败";
            Respone = ex.getMessage();
            End = new Date().getTime();
            CostTime = End - Start;
            SaveSubCondition("延时", requestObject, PlanID, ConditionID, conditionDelay, Respone, ConditionResultStatus, CostTime);
            throw new Exception("延时子条件执行异常：" + ex.getMessage());
        }
    }

    //延时子条件入口
    public void DelayCondition(long ConditionID, RequestObject requestObject) throws Exception {
        Long PlanID = Long.parseLong(requestObject.getTestplanid());
        ArrayList<HashMap<String, String>> conditionDelayList = testMysqlHelp.GetDelayConditionByConditionID(ConditionID);
        for (HashMap<String, String> conditionDelay : conditionDelayList) {
            conditiondelay(conditionDelay,ConditionID,requestObject,PlanID);
//            long Start = 0;
//            long End = 0;
//            long CostTime = 0;
//            String Respone = "执行延时条件成功";
//            String ConditionResultStatus = "成功";
//            try {
//                Start = new Date().getTime();
//                long delaytime = Long.parseLong(conditionDelay.get("delaytime"))*1000;
//                logger.info("TestCondition条件报告延时子条件，延时时间为（毫秒）:-============：" + delaytime);
//                Thread.sleep(delaytime);
//                Respone = Respone + "（毫秒）:" + delaytime;
//                logger.info("TestCondition条件报告延时子条件，延时时间为（毫秒）:-============：" + Respone);
//                End = new Date().getTime();
//                CostTime = End - Start;
//                SaveSubCondition("延时", requestObject, PlanID, ConditionID, conditionDelay, Respone, ConditionResultStatus, CostTime);
//            } catch (Exception ex) {
//                ConditionResultStatus = "失败";
//                Respone = ex.getMessage();
//                End = new Date().getTime();
//                CostTime = End - Start;
//                SaveSubCondition("延时", requestObject, PlanID, ConditionID, conditionDelay, Respone, ConditionResultStatus, CostTime);
//                throw new Exception("延时子条件执行异常：" + ex.getMessage());
//            }
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
        logger.info("TestCondition " + SubconditionType + "条件报告保存子条件进行中状态-============：" + testconditionReport.getPlanname() + "|" + testconditionReport.getBatchname());

        testconditionReport.setConditionresult(Respone.replace("'", "''"));
        testconditionReport.setConditionstatus(ConditionResultStatus);
        testconditionReport.setRuntime(CostTime);
        testconditionReport.setStatus("已完成");
        testMysqlHelp.SubConditionReportSave(testconditionReport);
        logger.info("TestCondition " + SubconditionType + "条件报告更新子条件结果-============：" + testconditionReport.getPlanname() + "|" + testconditionReport.getBatchname());

    }


    //处理数据库子条件
    public void conditiondb(HashMap<String, String> conditionDb,long ConditionID, RequestObject requestObject,Long PlanID) throws Exception {
        long Start = 0;
        long End = 0;
        long CostTime = 0;
        String Respone = "";
        String ConditionResultStatus = "成功";
        Long Assembleid = Long.parseLong(conditionDb.get("assembleid"));
        Long DBConditionid = Long.parseLong(conditionDb.get("id"));
        String SqlType = conditionDb.get("dbtype");
        String DBConditionName = conditionDb.get("subconditionname");
        try {
            ArrayList<HashMap<String, String>> enviromentAssemblelist = testMysqlHelp.getcaseData("select * from enviroment_assemble where id=" + Assembleid);
            if (enviromentAssemblelist.size() == 0) {
                Respone = "未找到环境组件，请检查是否存在或已被删除";
                ConditionResultStatus = "失败";
                SaveSubCondition("数据库", requestObject, PlanID, ConditionID, conditionDb, Respone, ConditionResultStatus, CostTime);
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
            }

            Long MachineID = Long.parseLong(macdepunitlist.get(0).get("machineid"));
            ArrayList<HashMap<String, String>> machinelist = testMysqlHelp.getcaseData("select * from machine where id=" + MachineID);
            if (machinelist.size() == 0) {
                Respone = "未找到环境组件部署的服务器，请检查是否存在或已被删除";
                ConditionResultStatus = "失败";
                SaveSubCondition("数据库", requestObject, PlanID, ConditionID, conditionDb, Respone, ConditionResultStatus, CostTime);
            }
            String deployunitvisittype = macdepunitlist.get(0).get("visittype");
            String[] ConnetcArray = ConnnectStr.split(",");
            if (ConnetcArray.length < 4) {
                Respone = "数据库连接字填写不规范，请按规则填写";
                ConditionResultStatus = "失败";
                SaveSubCondition("数据库", requestObject, PlanID, ConditionID, conditionDb, Respone, ConditionResultStatus, CostTime);
            }
            Long planid = Long.parseLong(requestObject.getTestplanid());
            Rundb(planid, requestObject.getTestplanname(), requestObject.getBatchname(), DBConditionid, DBConditionName, macdepunitlist, machinelist, ConnetcArray, AssembleType, deployunitvisittype, Sql, SqlType);
            Start = new Date().getTime();
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

    //数据库条件入口
    public void DBCondition(long ConditionID, RequestObject requestObject) throws Exception {
        Long PlanID = Long.parseLong(requestObject.getTestplanid());
        ArrayList<HashMap<String, String>> conditionDbListList = testMysqlHelp.GetDBConditionByConditionID(ConditionID);
        for (HashMap<String, String> conditionDb : conditionDbListList) {
            conditiondb(conditionDb,ConditionID,requestObject,PlanID);
//            long Start = 0;
//            long End = 0;
//            long CostTime = 0;
//            String Respone = "";
//            String ConditionResultStatus = "成功";
//            Long Assembleid = Long.parseLong(conditionDb.get("assembleid"));
//            Long DBConditionid = Long.parseLong(conditionDb.get("id"));
//            String SqlType = conditionDb.get("dbtype");
//            String DBConditionName = conditionDb.get("subconditionname");
//            try {
//                ArrayList<HashMap<String, String>> enviromentAssemblelist = testMysqlHelp.getcaseData("select * from enviroment_assemble where id=" + Assembleid);
//                if (enviromentAssemblelist.size() == 0) {
//                    Respone = "未找到环境组件，请检查是否存在或已被删除";
//                    ConditionResultStatus = "失败";
//                    SaveSubCondition("数据库", requestObject, PlanID, ConditionID, conditionDb, Respone, ConditionResultStatus, CostTime);
//                    break;
//                }
//                String AssembleType = enviromentAssemblelist.get(0).get("assembletype");
//                Long Envid = Long.parseLong(conditionDb.get("enviromentid"));
//                String Sql = conditionDb.get("dbcontent");
//                logger.info(logplannameandcasename + "TestCondition数据库子条件完整的sql ....." + Sql);
//                String ConnnectStr = enviromentAssemblelist.get(0).get("connectstr");
//                ArrayList<HashMap<String, String>> macdepunitlist = testMysqlHelp.getcaseData("select * from macdepunit where envid=" + Envid + " and assembleid=" + Assembleid);
//                if (macdepunitlist.size() == 0) {
//                    Respone = "未找到环境组件部署，请检查是否存在或已被删除";
//                    ConditionResultStatus = "失败";
//                    SaveSubCondition("数据库", requestObject, PlanID, ConditionID, conditionDb, Respone, ConditionResultStatus, CostTime);
//                    break;
//                }
//
//                Long MachineID = Long.parseLong(macdepunitlist.get(0).get("machineid"));
//                ArrayList<HashMap<String, String>> machinelist = testMysqlHelp.getcaseData("select * from machine where id=" + MachineID);
//                if (machinelist.size() == 0) {
//                    Respone = "未找到环境组件部署的服务器，请检查是否存在或已被删除";
//                    ConditionResultStatus = "失败";
//                    SaveSubCondition("数据库", requestObject, PlanID, ConditionID, conditionDb, Respone, ConditionResultStatus, CostTime);
//                    break;
//                }
//                String deployunitvisittype = macdepunitlist.get(0).get("visittype");
//                String[] ConnetcArray = ConnnectStr.split(",");
//                if (ConnetcArray.length < 4) {
//                    Respone = "数据库连接字填写不规范，请按规则填写";
//                    ConditionResultStatus = "失败";
//                    SaveSubCondition("数据库", requestObject, PlanID, ConditionID, conditionDb, Respone, ConditionResultStatus, CostTime);
//                    break;
//                }
//                Long planid = Long.parseLong(requestObject.getTestplanid());
//                Rundb(planid, requestObject.getTestplanname(), requestObject.getBatchname(), DBConditionid, DBConditionName, macdepunitlist, machinelist, ConnetcArray, AssembleType, deployunitvisittype, Sql, SqlType);
//                Start = new Date().getTime();
//            } catch (Exception ex) {
//                ConditionResultStatus = "失败";
//                Respone = ex.getMessage();
//                throw new Exception("数据库子条件执行异常：" + ex.getMessage());
//            } finally {
//                End = new Date().getTime();
//                CostTime = End - Start;
//                //更新条件结果表
//                SaveSubCondition("数据库", requestObject, PlanID, ConditionID, conditionDb, Respone, ConditionResultStatus, CostTime);
//            }
        }
    }

    //获取数据库连接字
    private String GetDbUrl(String AssembleType, ArrayList<HashMap<String, String>> macdepunitlist, String deployunitvisittype, ArrayList<HashMap<String, String>> machinelist, String dbname, String port) {
        String DBUrl = "";
        if (AssembleType.equalsIgnoreCase("pgsql")) {
            DBUrl = "jdbc:postgresql://";
            // 根据访问方式来确定ip还是域名
            if (deployunitvisittype.equalsIgnoreCase("ip")) {
                String IP = machinelist.get(0).get("ip");
                DBUrl = DBUrl + IP + ":" + port + "/" + dbname;
            } else {
                String Domain = macdepunitlist.get(0).get("domain");
                DBUrl = DBUrl + Domain + "/" + dbname;
            }
        }
        if (AssembleType.equalsIgnoreCase("mysql")) {
            DBUrl = "jdbc:mysql://";
            // 根据访问方式来确定ip还是域名
            if (deployunitvisittype.equalsIgnoreCase("ip")) {
                String IP = machinelist.get(0).get("ip");
                DBUrl = DBUrl + IP + ":" + port + "/" + dbname + "?useUnicode=true&useSSL=false&allowMultiQueries=true&characterEncoding=utf-8&useLegacyDatetimeCode=false&serverTimezone=UTC";
            } else {
                String Domain = macdepunitlist.get(0).get("domain");
                DBUrl = DBUrl + Domain + "/" + dbname + "?useUnicode=true&useSSL=false&allowMultiQueries=true&characterEncoding=utf-8&useLegacyDatetimeCode=false&serverTimezone=UTC";
            }
        }
        if (AssembleType.equalsIgnoreCase("oracle")) {
            DBUrl = "jdbc:oracle:thin:@";
            // 根据访问方式来确定ip还是域名
            if (deployunitvisittype.equalsIgnoreCase("ip")) {
                String IP = machinelist.get(0).get("ip");
                DBUrl = DBUrl + IP + ":" + port + ":" + dbname;
            } else {
                String Domain = macdepunitlist.get(0).get("domain");
                DBUrl = DBUrl + Domain + ":" + dbname;
            }
        }
        return DBUrl;
    }

    //获取数据库变量值
    private String GetDBResultValueByMap(List<HashMap<String, String>> DbResult, String columnname, long rownum) {
        String Result = "未获得数据库变量值，请确认查询sql是否能正常获取数据，或者列名是否和Sql中匹配";
        for (int i = 0; i < DbResult.size(); i++) {
            if (i == rownum) {
                HashMap<String, String> rowdata = DbResult.get(i);
                for (String Cloumn : rowdata.keySet()) {
                    if (Cloumn.equalsIgnoreCase(columnname)) {
                        Result = rowdata.get(Cloumn);
                    }
                }
            }
        }
        return Result;
    }

    //获取数据库变量值
    private String GetDBResultValueByEntity(List<Entity> DbResult, String columnname, long rownum) {
        String Result = "未获得数据库变量值，请确认查询sql是否能正常获取数据，或者列名是否和Sql中匹配";
        for (int i = 0; i < DbResult.size(); i++) {
            if (i == rownum) {
                Entity row = DbResult.get(i);
                Result = row.getStr(columnname);
            }
        }
        return Result;
    }

    //保存数据库变量值
    private void SaveDBTestVariablesValue(long planid, String planname, String batchname, long Conidtiondbid, String DBConditionName, long variablesid, String Variablesname, String VariablesValue) {
        TestvariablesValue testvariablesValue = new TestvariablesValue();
        testvariablesValue.setPlanid(planid);
        testvariablesValue.setPlanname(planname);
        testvariablesValue.setBatchname(batchname);
        testvariablesValue.setCaseid(Conidtiondbid);
        testvariablesValue.setVariablestype("数据库");
        testvariablesValue.setCasename(DBConditionName);
        testvariablesValue.setVariablesid(variablesid);
        testvariablesValue.setVariablesname(Variablesname);
        testvariablesValue.setVariablesvalue(VariablesValue);
        testvariablesValue.setMemo("test");
        testMysqlHelp.testVariablesValueSave(testvariablesValue);
    }

    private String Rundb(long planid, String planname, String batchname, long Conidtiondbid, String DBConditionName, ArrayList<HashMap<String, String>> macdepunitlist, ArrayList<HashMap<String, String>> machinelist, String[] ConnetcArray, String AssembleType, String deployunitvisittype, String Sql, String SqlType) throws Exception {
        String Respone = "";
        String username = ConnetcArray[0];
        String pass = ConnetcArray[1];
        String port = ConnetcArray[2];
        String dbname = ConnetcArray[3];
        String DBUrl = "";
        if (AssembleType.equalsIgnoreCase("pgsql")) {
            DBUrl = GetDbUrl(AssembleType, macdepunitlist, deployunitvisittype, machinelist, dbname, port);
            PgsqlConnectionUtils.initDbResource(DBUrl, username, pass);
            if (SqlType.equalsIgnoreCase("Select")) {
                //查询语句结果解析到数据库变量中
                // 1.查询数据库条件是否有变量关联
                ArrayList<HashMap<String, String>> dbconditionVariablesList = testMysqlHelp.getbyconditionid(Conidtiondbid);
                if (dbconditionVariablesList.size() > 0) {
                    //2.获取查询结果
                    List<HashMap<String, String>> result = PgsqlConnectionUtils.query(Sql);
                    for (HashMap<String, String> dbconditionVariables : dbconditionVariablesList) {
                        long variablesid = Long.parseLong(dbconditionVariables.get("variablesid"));
                        String Variablesname = dbconditionVariables.get("variablesname");
                        String columnname = dbconditionVariables.get("fieldname");
                        long roworder = Long.parseLong(dbconditionVariables.get("roworder"));
                        if (roworder > 0) {
                            roworder = roworder - 1;
                        }
                        String VariablesValue = GetDBResultValueByMap(result, columnname, roworder);
                        //保存数据库变量
                        Respone = Respone + "成功获取 数据库变量名：" + Variablesname + " 值:" + VariablesValue;
                        SaveDBTestVariablesValue(planid, planname, batchname, Conidtiondbid, DBConditionName, variablesid, Variablesname, VariablesValue);
                    }
                }
            } else {
                String[] SqlArr = Sql.split(";");
                for (String ExecSql : SqlArr) {
                    int nums = PgsqlConnectionUtils.execsql(ExecSql);
                    Respone = Respone + " 成功执行Sql:" + Sql + " 影响条数：" + nums;
                }
            }
        }

        if (AssembleType.equalsIgnoreCase("mysql")) {
            DBUrl = GetDbUrl(AssembleType, macdepunitlist, deployunitvisittype, machinelist, dbname, port);
            Respone = UseHutoolDb(planid, planname, batchname, Conidtiondbid, DBConditionName, SqlType, DBUrl, username, pass, Sql);
        }
        if (AssembleType.equalsIgnoreCase("oracle")) {
            DBUrl = GetDbUrl(AssembleType, macdepunitlist, deployunitvisittype, machinelist, dbname, port);
            Respone = UseHutoolDb(planid, planname, batchname, Conidtiondbid, DBConditionName, SqlType, DBUrl, username, pass, Sql);
        }
        return Respone;
    }

    private String UseHutoolDb(long planid, String planname, String batchname, long Conidtiondbid, String DBConditionName, String SqlType, String DBUrl, String username, String pass, String Sql) throws SQLException {
        String Respone = "";
        DataSource ds = new SimpleDataSource(DBUrl, username, pass);

        if (SqlType.equalsIgnoreCase("Select")) {
            // 1.查询数据库条件是否有变量关联
            ArrayList<HashMap<String, String>> dbconditionVariablesList = testMysqlHelp.getbyconditionid(Conidtiondbid);
            if (dbconditionVariablesList.size() > 0) {
                //2.获取查询结果
                List<Entity> result = Db.use(ds).query(Sql);
                for (HashMap<String, String> dbconditionVariables : dbconditionVariablesList) {
                    long variablesid = Long.parseLong(dbconditionVariables.get("variablesid"));
                    String Variablesname = dbconditionVariables.get("variablesname");
                    String columnname = dbconditionVariables.get("fieldname");
                    long roworder = Long.parseLong(dbconditionVariables.get("roworder"));
                    if (roworder > 0) {
                        roworder = roworder - 1;
                    }
                    String VariablesValue = GetDBResultValueByEntity(result, columnname, roworder);
                    Respone = Respone + "成功获取 数据库变量名：" + Variablesname + " 值:" + VariablesValue;
                    //保存数据库变量
                    SaveDBTestVariablesValue(planid, planname, batchname, Conidtiondbid, DBConditionName, variablesid, Variablesname, VariablesValue);
                }
            }
        } else {
            String[] SqlArr = Sql.split(";");
            for (String ExecSql : SqlArr) {
                int nums = Db.use(ds).execute(ExecSql);
                Respone = Respone + " 成功执行Sql:" + Sql + " 影响条数：" + nums;
            }
        }
        return Respone;
    }

}
