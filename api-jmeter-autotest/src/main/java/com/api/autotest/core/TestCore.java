package com.api.autotest.core;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.api.autotest.common.utils.*;
import com.api.autotest.dto.*;
import com.google.common.collect.Maps;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.log.Logger;
import java.util.*;

/**
 * Created by fanseasn on 2020/10/17.
 */
/*
 @author Season
 @DESCRIPTION 
 @create 2020/10/17
*/
public class TestCore {
    private Logger logger = null;
    private TestMysqlHelp testMysqlHelp = null;
    private TestCaseData testCaseData = null;
    private TestHttpRequestData testHttpRequestData = null;
    private TestHttp testHttp = null;
    private TestCondition testCondition = null;

    public TestCore(JavaSamplerContext context, Logger log) {
        logger = log;
        Httphelp.logger = log;
        String MysqlUrl = context.getParameter("mysqlurl");
        String MysqlUserName = context.getParameter("mysqlusername");
        String MysqlPass = context.getParameter("mysqlpassword");
        logger.info("TestCore 数据库连接字 is :  " + MysqlUrl + "   " + MysqlUserName + "   " + MysqlPass);
        //GetDBConnection(MysqlUrl, MysqlUserName, MysqlPass);
        testHttp=new TestHttp(log);
        testMysqlHelp=new TestMysqlHelp(MysqlUrl, MysqlUserName, MysqlPass, log);
        testCaseData=new TestCaseData(log,testMysqlHelp);
        testHttpRequestData=new TestHttpRequestData(log,testMysqlHelp);
        testCondition=new TestCondition(log,testMysqlHelp,testCaseData,testHttpRequestData,testHttp);
    }

    //性能初始化数据根据jmeter传递下来的数据拼装用例请求的数据
    public RequestObject InitHttpDatabyJmeter(JavaSamplerContext context) throws Exception {
        RequestObject requestObject=testCaseData.InitHttpDatabyJmeter(context);
        RequestObject newob=testHttpRequestData.GetPerformanceHttpRequestData(requestObject);
        logger.info("GetPerformanceHttpRequestData 完成-============：");
        return newob;
    }

    //功能测试通过调度IDs获取请求拼装数据列表
    public List<RequestObject> GetDispatchOBList(JavaSamplerContext context) {
        List<RequestObject> FunctionROList = new ArrayList<>();
        String DispatchIds = context.getParameter("DispatchIds");
        String[] DispatchArray = DispatchIds.split(",");
        for (String DispatchID : DispatchArray) {
            ArrayList<HashMap<String, String>> DispatchList = getcaseData("select * from dispatch where id=" + DispatchID);
            String PlanId = getcaseValue("execplanid", DispatchList);
            String CaseId = getcaseValue("testcaseid", DispatchList);
            String SlaverId = getcaseValue("slaverid", DispatchList);
            String BatchId = getcaseValue("batchid", DispatchList);
            String BatchName = getcaseValue("batchname", DispatchList);
            String ExecPlanName = getcaseValue("execplanname", DispatchList);
            RequestObject ro =testCaseData.GetCaseRequestData(PlanId, CaseId, SlaverId, BatchId, BatchName, ExecPlanName);
            ro=testHttpRequestData.GetFuntionHttpRequestData(ro);
            FunctionROList.add(ro);
        }
        return FunctionROList;
    }

    //处理条件入口
    public void FixCondition(RequestObject requestObject) throws Exception {
        Long ObjectID = Long.parseLong(requestObject.getCaseid());
        ArrayList<HashMap<String, String>> testconditionList = GetConditionByPlanIDAndConditionType(ObjectID, "前置条件", "测试用例");
        if (testconditionList.size() > 0) {
            long ConditionID = Long.parseLong(testconditionList.get(0).get("id"));
            ArrayList<HashMap<String, String>> conditionorderList =GetConditionOrderByID(ConditionID);
            if(conditionorderList.size()>0)
            {
                for (HashMap<String, String> conditionorder:conditionorderList) {
                    long subconditionid=Long.parseLong(conditionorder.get("subconditionid"));
                    if(conditionorder.get("subconditiontype").equals("接口"))
                    {
                        ArrayList<HashMap<String, String>> result= testMysqlHelp.GetApiConditionByID(subconditionid);
                        if(result.size()>0)
                        {
                            logger.info("TestCore 开始处理用例前置条件顺序-API子条件-============：");
                            testCondition.conditionapi(result.get(0),ConditionID, requestObject,Long.parseLong(requestObject.getTestplanid()));
                            logger.info("TestCore 完成处理用例前置条件顺序-API子条件-============：");
                        }
                    }
                    if(conditionorder.get("subconditiontype").equals("数据库"))
                    {
                        ArrayList<HashMap<String, String>> result= testMysqlHelp.GetDBConditionByID(subconditionid);
                        if(result.size()>0)
                        {
                            logger.info("TestCore 开始处理用例前置条件顺序-数据库子条件-============：");
                            testCondition.conditiondb(result.get(0),ConditionID, requestObject,Long.parseLong(requestObject.getTestplanid()));
                            logger.info("TestCore 完成处理用例前置条件顺序-数据库子条件-============：");
                        }
                    }
                    if(conditionorder.get("subconditiontype").equals("脚本"))
                    {
                        ArrayList<HashMap<String, String>> result= testMysqlHelp.GetScriptConditionByID(subconditionid);
                        if(result.size()>0)
                        {
                            logger.info("TestCore 开始处理用例前置条件顺序-脚本子条件-============：");
                            testCondition.conditionscript(result.get(0),ConditionID, requestObject,Long.parseLong(requestObject.getTestplanid()),Long.parseLong(requestObject.getCaseid()));
                            logger.info("TestCore 完成处理用例前置条件顺序-脚本子条件-============：");
                        }
                    }
                    if(conditionorder.get("subconditiontype").equals("延时"))
                    {
                        ArrayList<HashMap<String, String>> result= testMysqlHelp.GetDelayConditionByID(subconditionid);
                        if(result.size()>0) {
                            logger.info("TestCore 开始处理用例前置条件顺序-延时子条件-============：");
                            testCondition.conditiondelay(result.get(0),ConditionID, requestObject,Long.parseLong(requestObject.getTestplanid()));
                            logger.info("TestCore 完成处理用例前置条件顺序-延时子条件-============：");
                        }
                    }
                }
            }
            else
            {
                //处理数据库条件
                logger.info("TestCore 开始处理用例前置条件-数据库子条件-============：");
                testCondition.DBCondition(ConditionID, requestObject);
                logger.info("TestCore 完成处理用例前置条件-数据库条件-============：");
                //处理接口条件
                logger.info("TestCore 开始处理用例前置条件-API子条件-============：");
                testCondition.APICondition(ConditionID, requestObject);
                logger.info("TestCore 完成处理用例前置条件-API子条件-============：");
                //处理脚本条件
                logger.info("TestCore 开始处理用例前置条件-脚本子条件-============：");
                testCondition.ScriptCondition(ConditionID, requestObject);
                logger.info("TestCore 完成处理用例前置条件-脚本子条件-============：");
                //处理脚本条件
                logger.info("TestCore 开始处理用例前置条件-延时子条件-============：");
                testCondition.DelayCondition(ConditionID, requestObject);
                logger.info("TestCore 完成处理用例前置条件-延时子条件-============：");
            }
        }
    }

    // 发送http请求
    public TestResponeData request(RequestObject requestObject) throws Exception {
        TestResponeData result = testHttp.doService(requestObject,30000);
        return result;
    }

    //断言
    public String FixAssert(TestAssert TestAssert, List<ApicasesAssert> apicasesAssertList, TestResponeData responeData) throws Exception {
        String AssertInfo = "";
        for (ApicasesAssert apicasesAssert : apicasesAssertList) {

            if (apicasesAssert.getAsserttype().equalsIgnoreCase("Respone")) {
                AssertInfo = TestAssert.ParseResponeResult(responeData, apicasesAssert);
            }
            if (apicasesAssert.getAsserttype().equalsIgnoreCase("Json")) {
                AssertInfo = TestAssert.ParseJsonResult(responeData, apicasesAssert);
            }
            if (apicasesAssert.getAsserttype().equalsIgnoreCase("Xml")) {
                AssertInfo = TestAssert.ParseXmlResult(responeData, apicasesAssert);
            }
        }
        return AssertInfo;
    }

    //发送邮件
    public void SendMailByFinishPlanCase(String PlanID,String BatchName)
    {
        try
        {
            ArrayList<HashMap<String, String>> dicNameValueWithCode= findDicNameValueWithCode("Mail");
            if(dicNameValueWithCode.size()>0)
            {
                String MailInfo=dicNameValueWithCode.get(0).get("dicitmevalue");
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

                    ArrayList<HashMap<String, String>> list=GetplanBatchCreator(PlanID,BatchName);
                    if(list.size()>0)
                    {
                        String PlanName=list.get(0).get("executeplanname");
                        String Creator=list.get(0).get("creator");
                        ArrayList<HashMap<String, String>> listaccount= findWithUsername(Creator);
                        if(listaccount.size()>0)
                        {
                            String  mailto=listaccount.get(0).get("email");
                            String Subject=PlanName+"|"+BatchName+" 执行完成！";
                            ArrayList<HashMap<String, String>> liststatics= GetStatic(PlanID,BatchName);
                            long tc=0;
                            long tpc=0;
                            long tfc=0;
                            if(liststatics.size()>0)
                            {
                                tc=Long.parseLong(liststatics.get(0).get("tc"));
                                tpc=Long.parseLong(liststatics.get(0).get("tpc"));
                                tfc=Long.parseLong(liststatics.get(0).get("tfc"));
                            }
                            String Content="测试集合运行完成结果总计用例数："+tc+"， 成功数："+tpc+"， 失败数："+tfc;
                            MailUtil.send(account, CollUtil.newArrayList(mailto), Subject, Content, false);
                            logger.info("TestCore 发送邮件成功-============："+mailto);
                        }
                    }
                }
            }
            else
            {
                logger.info("TestCore发送邮件未找到字典表邮件配置信息-============：");
            }
        }
        catch (Exception ex)
        {
            logger.info("发送邮件异常-============："+ex.getMessage());
        }
    }

    private String GetSendContent(String PlanID,String BatchName)
    {
        String Content="";
        ArrayList<HashMap<String, String>> list=GetplanBatchCreator(PlanID,BatchName);
        if(list.size()>0)
        {
            String PlanName=list.get(0).get("executeplanname");
            String Subject="测试集合："+PlanName+"| 执行计划："+BatchName+" 执行完成！";
            ArrayList<HashMap<String, String>> liststatics= GetStatic(PlanID,BatchName);
            long tc=0;
            long tpc=0;
            long tfc=0;
            if(liststatics.size()>0)
            {
                tc=Long.parseLong(liststatics.get(0).get("tc"));
                tpc=Long.parseLong(liststatics.get(0).get("tpc"));
                tfc=Long.parseLong(liststatics.get(0).get("tfc"));
            }
            Content=Subject+"---------此测试集合运行完成结果总计用例数："+tc+"， 成功数："+tpc+"， 失败数："+tfc;
        }
        return Content;
    }
    //发送钉钉
    public void SendMessageDingDing(String PlanID,String BatchName)
    {
        try
        {
            String MessageContent= GetSendContent(PlanID,BatchName);
            ArrayList<HashMap<String, String>> dicNameValueWithCode= findDicNameValueWithCode("DingDing");
            if(dicNameValueWithCode.size()>0)
            {
                String Token=dicNameValueWithCode.get(0).get("dicitmevalue");
                //消息内容
                Map<String, String> contentMap = new HashMap<>();
                contentMap.put("content", MessageContent);
                //通知人
                Map<String, Object> atMap = new HashMap<>();;
                //1.是否通知所有人
                atMap.put("isAtAll", true);
                Map<String, Object> reqMap =new HashMap<>(); //Maps.newHashMap();
                reqMap.put("msgtype", "text");
                reqMap.put("text", contentMap);
                reqMap.put("at", atMap);
                String RequestContent= JSON.toJSONString(reqMap);
                String Respone = HttpRequest.post(Token).body(RequestContent).timeout(10000).execute().body();
                logger.info("发送钉钉信息响应：-============：" + Respone);
            }
            else
            {
                logger.info("发送钉钉信息未找到字典表钉钉配置信息：-============：" );
            }
        }
        catch (Exception ex)
        {
            logger.info("发送钉钉信息异常：-============：" + ex.getMessage());
        }
    }

    //获取计划批次的数据统计
    public ArrayList<HashMap<String, String>> GetStatic(String planid,String Batchname) {
        ArrayList<HashMap<String, String>> list=testMysqlHelp.GetStatic(planid, Batchname);
        return list;
    }


    //获取账号数据
    public ArrayList<HashMap<String, String>> findWithUsername(String username) {
        ArrayList<HashMap<String, String>> list=testMysqlHelp.findWithUsername(username);
        return list;
    }


    //获取数据库用例相关数据
    public ArrayList<HashMap<String, String>> findDicNameValueWithCode(String DicCode) {
        ArrayList<HashMap<String, String>> list=testMysqlHelp.findDicNameValueWithCode(DicCode);
        return list;
    }

    //获取计划批次
    public ArrayList<HashMap<String, String>> GetplanBatchCreator(String planid,String BatchName) {
        ArrayList<HashMap<String, String>> list=testMysqlHelp.GetplanBatchCreator(planid,BatchName);
        return list;
    }

    //获取数据库用例相关数据
    public ArrayList<HashMap<String, String>> getcaseData(String Sql) {
        ArrayList<HashMap<String, String>> list=testMysqlHelp.getcaseData(Sql);
        return list;
    }

    // 获取用例期望值
    public String getcaseValue(String key, ArrayList<HashMap<String, String>> list) {
        String value=testMysqlHelp.getcaseValue(key,list);
        return value;
    }


    //获取变量值类型
    private String GetVariablesDataType(String VariablesName) {
        String ValueType=testMysqlHelp.GetVariablesDataType(VariablesName);
        return ValueType;
    }

    //根据变量名获取caseid
    private String GetCaseIdByVariablesName(String VariablesName,String Caseid) {
        String CaseID=testMysqlHelp.GetCaseIdByVariablesName(VariablesName);
        return CaseID;
    }

    //获取变量值
    private String GetVariablesValues(String PlanID, String TestCaseId, String BatchName, String VariablesName) {
        String VariablesResult=testMysqlHelp.GetVariablesValues(PlanID,TestCaseId,BatchName,VariablesName);
        return VariablesResult;
    }


    //获取条件
    private ArrayList<HashMap<String, String>> GetConditionByPlanIDAndConditionType(Long Caseid, String ConditionType, String ObjectType) {
        ArrayList<HashMap<String, String>> result = testMysqlHelp.GetConditionByPlanIDAndConditionType(Caseid,ConditionType,ObjectType);
        return result;
    }

    //获取条件顺序
    private ArrayList<HashMap<String, String>> GetConditionOrderByID(Long ConditionID) {
        ArrayList<HashMap<String, String>> result = testMysqlHelp.GetConditionOrderByID(ConditionID);
        return result;
    }

    //获取接口条件
    private ArrayList<HashMap<String, String>> GetApiConditionByConditionID(Long ConditionID) {
        ArrayList<HashMap<String, String>> result = testMysqlHelp.GetApiConditionByConditionID(ConditionID);
        return result;
    }

    //获取脚本条件
    private ArrayList<HashMap<String, String>> GetScriptConditionByConditionID(Long ConditionID) {
        ArrayList<HashMap<String, String>> result = testMysqlHelp.GetScriptConditionByConditionID(ConditionID);
        return result;
    }

    //获取数据库条件
    private ArrayList<HashMap<String, String>> GetDBConditionByConditionID(Long ConditionID) {
        ArrayList<HashMap<String, String>> result = testMysqlHelp.GetDBConditionByConditionID(ConditionID);
        return result;
    }


    //保存条件结果
    public void SubConditionReportSave(TestconditionReport testconditionReport) {
        testMysqlHelp.SubConditionReportSave(testconditionReport);
    }

    //保存变量结果
    public void testVariablesValueSave(TestvariablesValue testvariablesValue) {
        testMysqlHelp.testVariablesValueSave(testvariablesValue);
    }

    //查询用例变量
    public ArrayList<HashMap<String, String>> GetApiCaseVaribales(Long CaseID) {
        ArrayList<HashMap<String, String>> result = testMysqlHelp.GetApiCaseVaribales(CaseID);
        return result;
    }

    //查询变量
    public ArrayList<HashMap<String, String>> GetVaribales(String VaribaleID) {
        ArrayList<HashMap<String, String>> result = testMysqlHelp.GetVaribales(VaribaleID);
        return result;
    }

    // 获取用例Header，params，Body，Dubbo数据
    public HashMap<String, String> fixhttprequestdatas(String MapType, ArrayList<HashMap<String, String>> casedatalist) {
        HashMap<String, String> DataMap = testMysqlHelp.fixhttprequestdatas(MapType,casedatalist);
        return DataMap;
    }

    public HashMap<String, String> getparamsdatabytype(String MapType, ArrayList<HashMap<String, String>> casedatalist) {
        HashMap<String, String> DataMap = testMysqlHelp.getparamsdatabytype(MapType,casedatalist);
        return DataMap;
    }

    // 记录用例测试结果
    public void savetestcaseresult(boolean status, long time, String respone, String assertvalue, String errorinfo, RequestObject requestObject, JavaSamplerContext context) {
        testMysqlHelp.savetestcaseresult(status,time,respone,assertvalue,errorinfo,requestObject,context);
    }

    // 记录用例测试结果
    public void SaveReportStatics(ApicasesReportstatics apicasesReportstatics) {
        testMysqlHelp.SaveReportStatics(apicasesReportstatics);
    }


    //查询此计划下的批次调度是否已经全部完成，如果完成，刷新计划批次状态为finish
    public long PlanBatchAllDipatchFinish(String Testplanid, String batchname) {
        long DispatchNotFinishNums = testMysqlHelp.PlanBatchAllDipatchFinish(Testplanid,batchname);
        return  DispatchNotFinishNums;
    }

    // 更新计划批次状态
    public void UpdateReportStatics(String Planid, String BatchName, String status) {
        testMysqlHelp.UpdateReportStatics(Planid,BatchName,status);
    }

    // 更新Slaver状态
    public void UpdateSlaverStatus(String Slaverid, String status) {
        testMysqlHelp.UpdateSlaverStatus(Slaverid,status);
    }

    // 更新用例调度结果
    public void updatedispatchcasestatus(String testplanid, String caseid, String slaverid, String batchid) {
        testMysqlHelp.updatedispatchcasestatus(testplanid,caseid,slaverid,batchid);
    }

    //生成性能报告目录
    public void genealperformacestaticsreport(String testclass, String batchname, String testplanid, String batchid, String slaverid, String caseid, String casereportfolder, double costtime,String Creator) throws Exception {
        testMysqlHelp.genealperformacestaticsreport(testclass,batchname,testplanid,batchid,slaverid,caseid,casereportfolder,costtime,Creator);
    }
}
