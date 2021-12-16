package com.api.autotest.core;

import cn.hutool.db.Db;
import cn.hutool.db.ds.simple.SimpleDataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.api.autotest.common.utils.*;
import com.api.autotest.dto.*;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.log.Logger;

import javax.sql.DataSource;
import javax.swing.plaf.synth.SynthTreeUI;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
    public static String logplannameandcasename = "";
    private Logger logger = null;

    public TestCore(JavaSamplerContext context, Logger log) {
        logger = log;
        Httphelp.logger = log;
        String MysqlUrl = context.getParameter("mysqlurl");
        String MysqlUserName = context.getParameter("mysqlusername");
        String MysqlPass = context.getParameter("mysqlpassword");
        logger.info("connectstr is :  " + MysqlUrl + "   " + MysqlUserName + "   " + MysqlPass);
        GetDBConnection(MysqlUrl, MysqlUserName, MysqlPass);
    }

    //性能初始化数据根据jmeter传递下来的数据拼装用例请求的数据
    public RequestObject InitHttpDatabyJmeter(JavaSamplerContext context) throws Exception {
        RequestObject newob = new RequestObject();

        String casename = context.getParameter("casename");
        String executeplanname = context.getParameter("executeplanname");
        logplannameandcasename = executeplanname + "--" + casename + " :";
        logger.info(logplannameandcasename + "casename is :  " + casename);
        logger.info(logplannameandcasename + "executeplanname is :  " + executeplanname);

        String testplanid = context.getParameter("testplanid");
        String caseid = context.getParameter("caseid");
        String slaverid = context.getParameter("slaverid");
        String batchid = context.getParameter("batchid");
        String batchname = context.getParameter("batchname");

        String RequestmMthod = context.getParameter("RequestmMthod");
        logger.info(logplannameandcasename + "RequestmMthod is :  " + RequestmMthod);

        String resource = context.getParameter("resource");
        logger.info(logplannameandcasename + "resource is :  " + resource);

        String apistyle = context.getParameter("apistyle");
        logger.info(logplannameandcasename + "apistyle is :  " + apistyle);

        String requestcontenttype = context.getParameter("requestcontenttype");
        logger.info(logplannameandcasename + "requestcontenttype is :  " + requestcontenttype);

        String responecontenttype = context.getParameter("responecontenttype");
        logger.info(logplannameandcasename + "responecontenttype is :  " + responecontenttype);

        String headjson = context.getParameter("headjson");
        logger.info(logplannameandcasename + "headjson is :  " + headjson);

        String paramsjson = context.getParameter("paramsjson");
        logger.info(logplannameandcasename + "paramsjson is :  " + paramsjson);

        String bodyjson = context.getParameter("bodyjson");
        String dubbo = context.getParameter("dubbojson");
        String expect = context.getParameter("expect");

        List<ApicasesAssert> apicasesAssertList = new ArrayList<>();
        if (expect.isEmpty()) {
            newob.setApicasesAssertList(apicasesAssertList);
        } else {
            apicasesAssertList = JSONObject.parseArray(expect, ApicasesAssert.class);
            newob.setApicasesAssertList(apicasesAssertList);
        }
        expect = GetAssertInfo(apicasesAssertList);
        logger.info(logplannameandcasename + "expect is :  " + expect);

        String casetype = context.getParameter("casetype");
        logger.info(logplannameandcasename + "casetype is :  " + casetype);

        String protocal = context.getParameter("protocal");
        logger.info(logplannameandcasename + "protocal is :  " + protocal);

        //GetExpectMap(expect);
        newob.setCaseid(caseid);
        newob.setCasename(casename);
        newob.setTestplanid(testplanid);
        newob.setSlaverid(slaverid);
        newob.setBatchname(batchname);
        newob.setBatchid(batchid);
        newob.setExpect(expect);
        newob.setCasetype(casetype);

        newob.setResponecontenttype(responecontenttype);
        if (resource == null) {
            throw new Exception("测试集合对应的环境中未部署发布单元，请在测试环境中完成部署发布单元后再运行");
        }
        newob.setResource(resource);
        newob.setRequestmMthod(RequestmMthod);
        newob.setRequestcontenttype(requestcontenttype);
        newob.setApistyle(apistyle);
        newob.setProtocal(protocal);

        HttpHeader header = new HttpHeader();
        if (headjson.equals("NoCaseData")) {
            //表示api设置了header参数，但是用例无数据
            throw new Exception("API的Header参数未设计测试用例数据，请完成用例数据后再运行");
        } else {
            if (headjson.equals("NN")) {
                newob.setHeader(header);
            }
            else {
                Map headermaps = (Map) JSON.parse(headjson);
                for (Object map : headermaps.entrySet()) {
                    String Value = ((Map.Entry) map).getValue().toString();
                    Value = GetVariablesValues(Value, testplanid, batchname);
                    header.addParam(((Map.Entry) map).getKey().toString(), Value);
                }
                newob.setHeader(header);
            }
//            HttpHeader header = null;
//            if (headjson.equals("headjson")) {
//                header = new HttpHeader();
//                newob.setHeader(header);
//            } else {
//
//
        }
        HttpParamers params = new HttpParamers();
        if (paramsjson.equals("NoCaseData")) {
            throw new Exception("API的Params参数未设计测试用例数据，请完成用例数据后再运行");
        } else {
            String PostData = "";
            if (paramsjson.equals("NN")) {
                newob.setPostData(PostData);
            }
            else
            {
                Map paramsmaps = (Map) JSON.parse(paramsjson);
                for (Object map : paramsmaps.entrySet()) {
                    String Value = ((Map.Entry) map).getValue().toString();
                    Value=GetVariablesValues(Value,testplanid,batchname);
                    params.addParam(((Map.Entry) map).getKey().toString(), Value);
                }
                if (params.getParams().size() > 0) {
                    PostData = GetParasPostData(requestcontenttype, params);
                    newob.setPostData(PostData);
                }
            }
            newob.setParamers(params);
//            HttpParamers params = new HttpParamers();
//            if (paramsjson.equals("paramjson")) {
//                newob.setParamers(params);
//                newob.setPostData("");
//            } else {
//                try {
//                    Map paramsmaps = (Map) JSON.parse(paramsjson);
//                    for (Object map : paramsmaps.entrySet()) {
//                        String Value = ((Map.Entry) map).getValue().toString();
//                        Value=GetVariablesValues(Value,testplanid,batchname);
//                        params.addParam(((Map.Entry) map).getKey().toString(), Value);
//                    }
//                    if (params.getParams().size() > 0) {
//                        String PostData = "";
//                        try {
//                            PostData = GetParasPostData(requestcontenttype, params);
//                        } catch (IOException e) {
//                            savetestcaseresult(false, 0, "", "", e.getMessage(), newob, null);
//                        }
//                        newob.setPostData(PostData);
//                    }
//                    newob.setParamers(params);
//                } catch (Exception ex) {
//                    throw new Exception("Paras参数用例数据异常：" + paramsjson);
//                }
//            }
        }
        if (bodyjson.equals("NoCaseData")) {
            throw new Exception("API的Body参数未设计测试用例数据，请完成用例数据后再运行");
        } else {
            String PostData = "";
            if (paramsjson.equals("NN")) {
                newob.setPostData(PostData);
            }
            else
            {
                newob.setPostData(bodyjson);
            }
//            HttpParamers params = null;
//            if (bodyjson.equals("bodyjson")) {
//                params = new HttpParamers();
//                newob.setParamers(params);
//            } else {
//                try {
//                    Map paramsmaps = (Map) JSON.parse(bodyjson);
//                    params = new HttpParamers();
//                    for (Object map : paramsmaps.entrySet()) {
//                        String Value = ((Map.Entry) map).getValue().toString();
//                        if (Value.startsWith("$")) {
//                            String VariablesName = Value.substring(1);
//                            String Caseid = GetCaseIdByVariablesName(VariablesName);
//                            //根据用例参数值是否以$开头，如果是则认为是变量通过变量表取到变量值
//                            Value = GetVariablesValues(testplanid, Caseid, batchname, Value);
//                        }
//                        //Value = GetVariablesValues(testplanid, caseid, batchname, Value);
//                        params.addParam(((Map.Entry) map).getKey().toString(), Value);
//                    }
//                    newob.setParamers(params);
//                } catch (Exception ex) {
//                    throw new Exception("Body参数用例数据异常：" + bodyjson);
//                }
//            }
        }
        return newob;
    }

    //通过调度IDs获取请求拼装数据列表
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
            String TestCaseName = getcaseValue("testcasename", DispatchList);
            RequestObject ro = GetCaseRequestData(PlanId, CaseId, SlaverId, BatchId, BatchName, ExecPlanName);
            FunctionROList.add(ro);
        }
        return FunctionROList;
    }

    // 功能用例拼装请求需要的用例数据
    public RequestObject GetCaseRequestData(String PlanId, String TestCaseId, String SlaverId, String BatchId, String BatchName, String ExecPlanName) {
        RequestObject ro = new RequestObject();
        //ArrayList<HashMap<String, String>> planlist = getcaseData("select * from executeplan where id=" + PlanId);
        ArrayList<HashMap<String, String>> deployunitmachineiplist = getcaseData("select m.ip,a.domain,a.visittype from macdepunit a INNER JOIN apicases b INNER JOIN executeplan c JOIN machine m on a.depunitid=b.deployunitid and  a.envid=c.envid and  m.id=a.machineid where b.id=" + TestCaseId + " and c.id=" + PlanId);
        ArrayList<HashMap<String, String>> caselist = getcaseData("select * from apicases where id=" + TestCaseId);
        ArrayList<HashMap<String, String>> casedatalist = getcaseData("select * from api_casedata where caseid=" + TestCaseId);
        ArrayList<HashMap<String, String>> caseassertlist = getcaseData("select * from apicases_assert where caseid=" + TestCaseId);
        ArrayList<HashMap<String, String>> casedataptlist = getcaseData("select DISTINCT propertytype  from api_casedata where caseid=" + TestCaseId);
        ArrayList<HashMap<String, String>> apilist = getcaseData("select b.visittype,b.apistyle,b.path,b.requestcontenttype,b.responecontenttype from apicases a inner join api b on a.apiid=b.id where a.id=" + TestCaseId);
        ArrayList<HashMap<String, String>> deployunitlist = getcaseData("select b.protocal,b.port,b.id from apicases a inner join deployunit b on a.deployunitid=b.id where a.id=" + TestCaseId);

        //获取断言记录
        TestAssert testAssert = new TestAssert(logger);
        List<ApicasesAssert> apicasesAssertList = testAssert.GetApicasesAssertList(caseassertlist);
        ro.setApicasesAssertList(apicasesAssertList);
        // url请求资源路径
        String path = getcaseValue("path", apilist);
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        String casetype = getcaseValue("casetype", caselist);

        String CaseName=getcaseValue("casename", caselist);

        String expect = GetAssertInfo(apicasesAssertList); //getcaseValue("expect", caselist);

        String Apiid = getcaseValue("apiid", caselist);

        //获取请求响应的数据格式
        String requestcontenttype = getcaseValue("requestcontenttype", apilist);
        String responecontenttype = getcaseValue("responecontenttype", apilist);
        // http请求方式 get，post
        String method = getcaseValue("visittype", apilist).toLowerCase();
        // api风格
        String apistyle = getcaseValue("apistyle", apilist).toLowerCase();
        // 协议 http,https,rpc
        String protocal = getcaseValue("protocal", deployunitlist).toLowerCase();
        // 发布单元端口
        String port = getcaseValue("port", deployunitlist);
        String deployunitid = getcaseValue("id", deployunitlist);

        // 获取发布单元访问方式，ip或者域名
        String deployunitvisittype = getcaseValue("visittype", deployunitmachineiplist);
        // 根据访问方式来确定ip还是域名
        String testserver = "";
        String resource = "";
        if (deployunitvisittype.equals(new String("ip"))) {
            testserver = getcaseValue("ip", deployunitmachineiplist);
            resource = protocal + "://" + testserver + ":" + port + path;
        } else {
            testserver = getcaseValue("domain", deployunitmachineiplist);
            resource = protocal + "://" + testserver + path;
        }
        logger.info(logplannameandcasename + "resource is :  " + resource + "   protocal  is:   " + protocal + "  expect is :      " + expect + "  visittype is: " + method + "   path is: " + path + " casetype is: " + casetype);

        ro.setCaseid(TestCaseId);
        ro.setCasename(CaseName);
        ro.setDeployunitid(deployunitid);
        ro.setTestplanid(PlanId);
        ro.setSlaverid(SlaverId);
        ro.setBatchname(BatchName);
        ro.setTestplanname(ExecPlanName);
        ro.setBatchid(BatchId);
        ro.setExpect(expect);
        ro.setCasetype(casetype);
        ro.setProtocal(protocal);
        ro.setApistyle(apistyle);
        ro.setRequestcontenttype(requestcontenttype);
        ro.setRequestmMthod(method);
        ro.setResource(resource);
        ro.setResponecontenttype(responecontenttype);

        List<String> PropertyList = GetCaseDataPropertyType(casedataptlist);
        HttpHeader header = new HttpHeader();
        HttpParamers paramers = new HttpParamers();
        for (String property : PropertyList) {
            if (property.equals("Header")) {
                header = GetHttpHeader(casedatalist, header, PlanId, TestCaseId, BatchName);
            }
            ro.setHeader(header);
            if (property.equals("Params")) {
                paramers = GetHttpParams(casedatalist, paramers, PlanId, TestCaseId, BatchName);
                if (paramers.getParams().size() > 0) {
                    String PostData = "";
                    try {
                        PostData = GetParasPostData(requestcontenttype, paramers);
                    } catch (IOException e) {
                        savetestcaseresult(false, 0, "", "", e.getMessage(), ro, null);
                    }
                    ro.setPostData(PostData);
                }
            }
            ro.setParamers(paramers);
            if (property.equals("Body")) {
                // 设置Body
                HashMap<String, String> bodymap = fixhttprequestdatas("Body", casedatalist);
                String PostData = "";
                for (String Key:bodymap.keySet()) {
                    PostData=bodymap.get(Key);
                }
                ro.setPostData(PostData);
            }
        }
        return ro;
    }

    //获取断言信息
    private String GetAssertInfo(List<ApicasesAssert> apicasesAssertList) {
        String expectValue = "";
        if (apicasesAssertList.size() > 0) {
            for (ApicasesAssert apicasesAssert : apicasesAssertList) {
                if (apicasesAssert.getAsserttype().equals(new String("Respone"))) {
                    expectValue = expectValue + "【断言类型：" + apicasesAssert.getAsserttype() + "， 断言子类型：" + apicasesAssert.getAssertsubtype() + "， 断言条件：" + apicasesAssert.getAssertcondition() + "， 断言值：" + apicasesAssert.getAssertvalues() + "， 断言值类型：" + apicasesAssert.getAssertvaluetype()+"】";
                } else {
                    expectValue = expectValue + "【断言类型：" + apicasesAssert.getAsserttype() + "， 断言表达式：" + apicasesAssert.getExpression() + "， 断言条件：" + apicasesAssert.getAssertcondition() + "， 断言值：" + apicasesAssert.getAssertvalues() + "， 断言值类型：" + apicasesAssert.getAssertvaluetype()+"】";
                }
            }
        }
        return expectValue;
    }

    //获取参数值的具体内容，支持$变量，以及$变量和字符串拼接
    private String GetVariablesValues(String Value, String PlanId, String BatchName)
    {
        String Result="";
        if(Value.contains("+"))
        {
            String[] Array=Value.split("\\+");
            for (String str:Array)
            {
                if(str.contains("$"))
                {
                    String VariablesName = str.substring(1);
                    String Caseid = GetCaseIdByVariablesName(VariablesName);
                    //根据用例参数值是否以$开头，如果是则认为是变量通过变量表取到变量值
                    String VariablesNameValue = GetVariablesValues(PlanId, Caseid, BatchName, VariablesName);
                    Result = Result+VariablesNameValue;
                }
                else
                {
                    Result=Result+str;
                }
            }
        }
        else
        {
            if(Value.contains("$"))
            {
                String VariablesName = Value.substring(1);
                String Caseid = GetCaseIdByVariablesName(VariablesName);
                //根据用例参数值是否以$开头，如果是则认为是变量通过变量表取到变量值
                String VariablesNameValue = GetVariablesValues(PlanId, Caseid, BatchName, VariablesName);
                Result = VariablesNameValue;
            }
            else
            {
                Result=Value;
            }
        }
        return Result;
    }


    private HttpHeader GetHttpHeader(ArrayList<HashMap<String, String>> casedatalist, HttpHeader header, String PlanId, String TestCaseId, String BatchName) {
        // 设置header
        HashMap<String, String> headmap = fixhttprequestdatas("Header", casedatalist);
        for (String key : headmap.keySet()) {
            String Value = headmap.get(key);
            Value=GetVariablesValues(Value,PlanId,BatchName);
            header.addParam(key, Value);
        }
        return header;
    }

    private HttpParamers GetHttpParams(ArrayList<HashMap<String, String>> casedatalist, HttpParamers paramers, String PlanId, String TestCaseId, String BatchName) {
        // 设置参数params
        HashMap<String, String> paramsmap = fixhttprequestdatas("Params", casedatalist);
        for (String key : paramsmap.keySet()) {
            String Value = paramsmap.get(key);
            Value=GetVariablesValues(Value,PlanId,BatchName);
            paramers.addParam(key, Value);
        }
        return paramers;
    }

    private List<String> GetCaseDataPropertyType(ArrayList<HashMap<String, String>> casedataptlist) {
        List<String> PropertyType = new ArrayList<>();
        for (HashMap<String, String> hs : casedataptlist) {
            for (String property : hs.keySet()) {
                PropertyType.add(hs.get(property));
            }
        }
        return PropertyType;
    }

    private String GetParasPostData(String RequestContentType, HttpParamers paramers) throws IOException {
        String Result = "";
        if (RequestContentType.equals(new String("json"))) {
            paramers.setJsonParamer();
            Result = paramers.getJsonParamer();
        }
        if (RequestContentType.equals(new String("form表单"))) {
            Result = paramers.getQueryString();
        }
        if (RequestContentType.equals(new String("xml"))) {

        } else {

        }
        return Result;
    }

    private String GetBodyPostData(HashMap<String, String> BodyMap, String RequestContentType, String Apiid) {
        String Result = "";
        if (RequestContentType.equals(new String("json"))) {
            //获取冗余字段，替换json
            ArrayList<HashMap<String, String>> ApiParamasList = getcaseData("select * from api_params  where  propertytype= 'Body' " + " and apiid=" + Apiid);
            if (ApiParamasList.size() > 0) {
                String JsonCompelete = ApiParamasList.get(0).get("keynamebak");
                DocumentContext documentContext = JsonPath.parse(JsonCompelete);
                //获取参数的数据值
                for (String Key : BodyMap.keySet()) {
                    String JsonPathStr = "$." + Key;
                    documentContext.set(JsonPathStr, BodyMap.get(Key));
                }
                Result = documentContext.jsonString();
            }
        }
        if (RequestContentType.equals(new String("xml"))) {

        } else {

        }
        return Result;
    }

    //处理条件入口
    public void FixCondition(RequestObject requestObject) throws Exception {
        Long ObjectID = Long.parseLong(requestObject.getCaseid());
        ArrayList<HashMap<String, String>> testconditionList = GetConditionByPlanIDAndConditionType(ObjectID, "前置条件", "测试用例");
        if (testconditionList.size() > 0) {
            long ConditionID = Long.parseLong(testconditionList.get(0).get("id"));
//            SubCondition sub=new APISubCondition();
//            sub.DoSubCondition(ConditionID,requestObject);
            //处理接口条件
            logger.info("开始处理用例前置条件-API子条件-============：");
            APICondition(ConditionID, requestObject);
            logger.info("完成处理用例前置条件-API子条件-============：");
            //处理数据库条件
            DBCondition(ConditionID, requestObject);
            //处理脚本条件
            logger.info("开始处理用例前置条件-脚本子条件-============：");
            ScriptCondition(ConditionID, requestObject);
            logger.info("完成处理用例前置条件-脚本子条件-============：");
        }
    }

    //处理接口条件
    public void APICondition(long ConditionID, RequestObject requestObject) throws Exception {
        ArrayList<HashMap<String, String>> conditionApiList = GetApiConditionByConditionID(ConditionID);
        Long PlanID = Long.parseLong(requestObject.getTestplanid());
        Long CaseID = Long.parseLong(requestObject.getCaseid());
        logger.info("条件报告API子条件数量-============：" + conditionApiList.size());
        for (HashMap<String, String> conditionApi : conditionApiList) {
            long Start = 0;
            long End = 0;
            long CostTime = 0;
            String Respone = "";
            String ConditionResultStatus = "成功";
            RequestObject re=null;
            try {
                Start = new Date().getTime();
                String CondionCaseID=conditionApi.get("caseid");
                re= GetCaseRequestData(requestObject.getTestplanid(),CondionCaseID,requestObject.getSlaverid(),requestObject.getBatchid(),requestObject.getBatchname(),requestObject.getTestplanname());
                ResponeData responeData=request(re);
                Respone = responeData.getRespone();
                CostTime = End - Start;
                SaveApiSubCondition(requestObject, PlanID, CaseID, ConditionID, conditionApi, Respone, ConditionResultStatus, CostTime);
            } catch (Exception ex) {
                ConditionResultStatus = "失败";
                Respone = ex.getMessage();
                CostTime = End - Start;
                SaveApiSubCondition(re, PlanID, CaseID, ConditionID, conditionApi, Respone, ConditionResultStatus, CostTime);
                throw new Exception("接口子条件执行异常：" + ex.getMessage());
            }
//            TestconditionReport testconditionReport = new TestconditionReport();
//            testconditionReport.setTestplanid(PlanID);
//            testconditionReport.setPlanname(requestObject.getTestplanname());
//            testconditionReport.setBatchname(requestObject.getBatchname());
//            testconditionReport.setConditionid(new Long(ConditionID));
//            testconditionReport.setConditiontype("前置条件");
//            testconditionReport.setConditionresult(Respone);
//            testconditionReport.setConditionstatus(ConditionResultStatus);
//            testconditionReport.setRuntime(CostTime);
//            testconditionReport.setSubconditionid(Long.parseLong(conditionApi.get("id")));
//            testconditionReport.setSubconditiontype("接口");
//            testconditionReport.setStatus("已完成");
//            logger.info("条件报告保存子条件进行中状态-============：" + testconditionReport.getPlanname() + "|" + testconditionReport.getBatchname() + "|" + requestObject.getCasename());
//            SubConditionReportSave(testconditionReport);
//            //根据用例是否有中间变量，如果有变量，解析（json，xml，html）保存变量值表，没有变量直接保存条件结果表
//            ArrayList<HashMap<String, String>> apicasesVariablesList  = GetApiCaseVaribales(CaseID);
//            if (apicasesVariablesList.size()>0) {
//                logger.info("条件报告子条件处理变量-============：" + apicasesVariablesList.get(0).get("variablesname"));
//                String Variablesid=apicasesVariablesList.get(0).get("id");
//                ArrayList<HashMap<String, String>> VariablesList  = GetVaribales(Variablesid);
//                if(VariablesList.size()>0)
//                {
//                    String VariablesPath = VariablesList.get(0).get("variablesexpress");
//                    logger.info("条件报告子条件处理变量表达式-============：" + VariablesPath + " 响应数据类型" + requestObject.getResponecontenttype());
//                    TestAssert testAssert=new TestAssert(logger);
//                    String ParseValue = testAssert.ParseJson(VariablesPath, Respone);
//                    logger.info("条件报告子条件处理变量取值-============：" + ParseValue);
//                    TestvariablesValue testvariablesValue = new TestvariablesValue();
//                    testvariablesValue.setPlanid(PlanID);
//                    testvariablesValue.setPlanname(requestObject.getTestplanname());
//                    testvariablesValue.setBatchname(requestObject.getBatchname());
//                    testvariablesValue.setCaseid(CaseID);
//                    testvariablesValue.setCasename(requestObject.getCasename());
//                    testvariablesValue.setVariablesid(Long.parseLong(VariablesList.get(0).get("id")));
//                    testvariablesValue.setVariablesname(VariablesList.get(0).get("testvariablesname"));
//                    testvariablesValue.setVariablesvalue(ParseValue);
//                    testvariablesValue.setMemo("test");
//                    testVariablesValueSave(testvariablesValue);
//                }
//            }
        }
    }

    private void SaveApiSubCondition(RequestObject requestObject, Long PlanID, Long CaseID, Long ConditionID, HashMap<String, String> conditionApi, String Respone, String ConditionResultStatus, long CostTime) {
        TestconditionReport testconditionReport = new TestconditionReport();
        testconditionReport.setTestplanid(PlanID);
        testconditionReport.setPlanname(requestObject.getTestplanname());
        testconditionReport.setBatchname(requestObject.getBatchname());
        testconditionReport.setConditionid(new Long(ConditionID));
        testconditionReport.setConditiontype("前置条件");
        testconditionReport.setConditionresult(Respone);
        testconditionReport.setConditionstatus(ConditionResultStatus);
        testconditionReport.setRuntime(CostTime);
        testconditionReport.setSubconditionid(Long.parseLong(conditionApi.get("id")));
        testconditionReport.setSubconditionname(conditionApi.get("subconditionname"));
        testconditionReport.setSubconditiontype("接口");
        testconditionReport.setStatus("已完成");
        logger.info("条件报告保存子条件已完成状态-============：" + testconditionReport.getPlanname() + "|" + testconditionReport.getBatchname() + "|" + requestObject.getCasename());
        SubConditionReportSave(testconditionReport);
        //根据用例是否有中间变量，如果有变量，解析（json，xml，html）保存变量值表，没有变量直接保存条件结果表
        ArrayList<HashMap<String, String>> apicasesVariablesList = GetApiCaseVaribales(CaseID);
        if (apicasesVariablesList.size() > 0) {
            logger.info("条件报告子条件处理变量-============：" + apicasesVariablesList.get(0).get("variablesname"));
            String Variablesid = apicasesVariablesList.get(0).get("id");
            ArrayList<HashMap<String, String>> VariablesList = GetVaribales(Variablesid);
            if (VariablesList.size() > 0) {
                String VariablesPath = VariablesList.get(0).get("variablesexpress");
                logger.info("条件报告子条件处理变量表达式-============：" + VariablesPath + " 响应数据类型" + requestObject.getResponecontenttype());
                TestAssert testAssert = new TestAssert(logger);
                String ParseValue = testAssert.ParseJson(VariablesPath, Respone);
                logger.info("条件报告子条件处理变量取值-============：" + ParseValue);
                TestvariablesValue testvariablesValue = new TestvariablesValue();
                testvariablesValue.setPlanid(PlanID);
                testvariablesValue.setPlanname(requestObject.getTestplanname());
                testvariablesValue.setBatchname(requestObject.getBatchname());
                testvariablesValue.setCaseid(CaseID);
                testvariablesValue.setCasename(requestObject.getCasename());
                testvariablesValue.setVariablesid(Long.parseLong(VariablesList.get(0).get("id")));
                testvariablesValue.setVariablesname(VariablesList.get(0).get("testvariablesname"));
                testvariablesValue.setVariablesvalue(ParseValue);
                testvariablesValue.setMemo("test");
                testVariablesValueSave(testvariablesValue);
            }
        }
    }

    //处理脚本条件
    public void ScriptCondition(long ConditionID, RequestObject requestObject) throws Exception {
        Long PlanID = Long.parseLong(requestObject.getTestplanid());
        Long CaseID = Long.parseLong(requestObject.getCaseid());
        ArrayList<HashMap<String, String>> conditionScriptList = GetScriptConditionByConditionID(ConditionID);
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
                logger.info("条件报告脚本子条件:-============：" + JavaSource);
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
            //更新条件结果表
//            TestconditionReport testconditionReport = new TestconditionReport();
//            testconditionReport.setTestplanid(PlanID);
//            testconditionReport.setPlanname(requestObject.getCasename());
//            testconditionReport.setBatchname(requestObject.getBatchname());
//            testconditionReport.setConditionid(new Long(ConditionID));
//            testconditionReport.setConditiontype("前置条件");
//            testconditionReport.setSubconditionid(Long.parseLong(conditionScript.get("id")));
//            testconditionReport.setSubconditiontype("脚本");
//            logger.info("条件报告保存子条件进行中状态-============：" + testconditionReport.getPlanname() + "|" + testconditionReport.getBatchname());
//
//            testconditionReport.setConditionresult(Respone);
//            testconditionReport.setConditionstatus(ConditionResultStatus);
//            testconditionReport.setRuntime(CostTime);
//            testconditionReport.setStatus("已完成");
//            SubConditionReportSave(testconditionReport);
//            logger.info("条件报告更新子条件结果-============：" + testconditionReport.getPlanname() + "|" + testconditionReport.getBatchname());
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
        logger.info(SubconditionType + "条件报告保存子条件进行中状态-============：" + testconditionReport.getPlanname() + "|" + testconditionReport.getBatchname());

        testconditionReport.setConditionresult(Respone);
        testconditionReport.setConditionstatus(ConditionResultStatus);
        testconditionReport.setRuntime(CostTime);
        testconditionReport.setStatus("已完成");
        SubConditionReportSave(testconditionReport);
        logger.info(SubconditionType + "条件报告更新子条件结果-============：" + testconditionReport.getPlanname() + "|" + testconditionReport.getBatchname());

    }

    public void DBCondition(long ConditionID, RequestObject requestObject) {
        Long PlanID = Long.parseLong(requestObject.getTestplanid());
        Long CaseID = Long.parseLong(requestObject.getCaseid());
        ArrayList<HashMap<String, String>> conditionDbListList = GetDBConditionByConditionID(ConditionID);
        for (HashMap<String, String> conditionDb : conditionDbListList) {
            TestconditionReport testconditionReport = new TestconditionReport();
            long Start = 0;
            long End = 0;
            long CostTime = 0;
            String Respone = "";
            String ConditionResultStatus = "成功";
            Long Assembleid = Long.parseLong(conditionDb.get("assembleid"));
            ArrayList<HashMap<String, String>> enviromentAssemblelist = getcaseData("select * from enviroment_assemble where id=" + Assembleid);
            if (enviromentAssemblelist.size() == 0) {
                Respone = "未找到环境组件，请检查是否存在或已被删除";
                ConditionResultStatus = "失败";
                SaveSubCondition("数据库", requestObject, PlanID, ConditionID, conditionDb, Respone, ConditionResultStatus, CostTime);
                break;
            }
            String AssembleType = enviromentAssemblelist.get(0).get("assembletype");
            Long Envid = Long.parseLong(conditionDb.get("enviromentid"));
            String Sql = conditionDb.get("dbcontent");
            String ConnnectStr = enviromentAssemblelist.get(0).get("connectstr");
            ArrayList<HashMap<String, String>> macdepunitlist = getcaseData("select * from macdepunit where envid=" + Envid + " and assembleid=" + Assembleid);
            if (macdepunitlist.size() == 0) {
                Respone = "未找到环境组件部署，请检查是否存在或已被删除";
                ConditionResultStatus = "失败";
                SaveSubCondition("数据库", requestObject, PlanID, ConditionID, conditionDb, Respone, ConditionResultStatus, CostTime);
                break;
            }

            Long MachineID = Long.parseLong(macdepunitlist.get(0).get("machineid"));
            ArrayList<HashMap<String, String>> machinelist = getcaseData("select * from machine where id=" + MachineID);
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
            }
            if (AssembleType.equals("oracle")) {
                DBUrl = "";
            }
            // 根据访问方式来确定ip还是域名
            if (deployunitvisittype.equals("ip")) {
                String IP = machinelist.get(0).get("ip");
                DBUrl = DBUrl + IP + ":" + port + "/" + dbname + "?useUnicode=true&useSSL=false&allowMultiQueries=true&characterEncoding=utf-8&useLegacyDatetimeCode=false&serverTimezone=UTC";
            } else {
                String Domain = macdepunitlist.get(0).get("domain");
                DBUrl = DBUrl + Domain + "/" + dbname + "?useUnicode=true&useSSL=false&allowMultiQueries=true&characterEncoding=utf-8&useLegacyDatetimeCode=false&serverTimezone=UTC";
            }
            try {
                Start = new Date().getTime();
                DataSource ds = new SimpleDataSource(DBUrl, username, pass);
                int nums = Db.use(ds).execute(Sql);
                Respone = "成功执行，影响条数：" + nums;
            } catch (Exception ex) {
                ConditionResultStatus = "失败";
                Respone = ex.getMessage();
            } finally {
                End = new Date().getTime();
            }
            CostTime = End - Start;
            //更新条件结果表
            SaveSubCondition("数据库", requestObject, PlanID, ConditionID, conditionDb, Respone, ConditionResultStatus, CostTime);
        }
    }

    // 发送http请求
    public ResponeData request(RequestObject requestObject) throws Exception {
        ResponeData result = null;
        if (requestObject.getProtocal().equals("http") || requestObject.getProtocal().equals("https")) {
            if (requestObject.getRequestmMthod().equals("get")) {
                logger.info(logplannameandcasename + "get请求，request url is ....." + Httphelp.getrequesturl(requestObject.getResource(), requestObject.getApistyle(), requestObject.getParamers()));
            }
            logger.info(logplannameandcasename + "doService url is ....." + Httphelp.getrequesturl(requestObject.getResource(), requestObject.getApistyle(), requestObject.getParamers()));
            result = Httphelp.doService(requestObject.getProtocal(), requestObject.getResource(), requestObject.getRequestmMthod(), requestObject.getApistyle(), requestObject.getParamers(), requestObject.getPostData(), requestObject.getRequestcontenttype(), requestObject.getHeader(), 30000, 30000);
        }
        return result;
    }


    public String  FixAssert(TestAssert TestAssert, List<ApicasesAssert> apicasesAssertList,ResponeData responeData) throws Exception {
        String AssertInfo="";
        for (ApicasesAssert  apicasesAssert : apicasesAssertList) {

            if(apicasesAssert.getAsserttype().equals("Respone"))
            {
                AssertInfo = TestAssert.ParseResponeResult(responeData,apicasesAssert);
            }
            if(apicasesAssert.getAsserttype().equals("Json"))
            {
                AssertInfo = TestAssert.ParseJsonResult(responeData,apicasesAssert);
            }
            if(apicasesAssert.getAsserttype().equals("Xml"))
            {
                AssertInfo = TestAssert.ParseXmlResult(responeData,apicasesAssert);
            }
        }
        return AssertInfo;
    }

    //初始化数据库连接
    public void GetDBConnection(String mysqluel, String mysqlusername, String mysqlpass) {
        MysqlConnectionUtils.initDbResource(mysqluel, mysqlusername, mysqlpass);
    }

    //获取数据库用例相关数据
    public ArrayList<HashMap<String, String>> getcaseData(String Sql) {
        logger.info(logplannameandcasename + "Sql is:  " + Sql);
        ArrayList<HashMap<String, String>> list = null;
        try {
            list = MysqlConnectionUtils.query(Sql);
        } catch (Exception e) {
            logger.info(logplannameandcasename + "Sql is:  " + Sql + "  数据库异常：" + e.getMessage());
        }
        logger.info(logplannameandcasename + "list size is:  " + list.size());
        return list;
    }

    // 获取用例期望值
    public String getcaseValue(String key, ArrayList<HashMap<String, String>> list) {
        HashMap<String, String> hs = list.get(0);
        return hs.get(key).trim();
    }

    //根据变量名获取caseid
    private String GetCaseIdByVariablesName(String VariablesName)
    {
        String CaseID="";
        try {
            String sql = "select caseid from apicases_variables where  variablesname='" + VariablesName + "'";
            logger.info(logplannameandcasename + "根据变量名获取caseid result sql is...........: " + sql);
            ArrayList<HashMap<String, String>> result = MysqlConnectionUtils.query(sql);
            if (result.size() > 0) {
                CaseID = result.get(0).get("caseid");
            }
        } catch (Exception e) {
            logger.info(logplannameandcasename + "根据变量名获取caseid异常...........: " + e.getMessage());
        }
        return CaseID;
    }

    //获取变量值
    private String GetVariablesValues(String PlanID, String TestCaseId, String BatchName, String VariablesName) {
        String VariablesResult = "";
        try {
            String sql = "select variablesvalue from testvariables_value where planid=" + PlanID +" and caseid="+TestCaseId+ " and batchname= '" + BatchName + "'" + " and variablesname='" + VariablesName + "'";
            logger.info(logplannameandcasename + "查询计划下的批次中条件接口获取的中间变量 result sql is...........: " + sql);
            ArrayList<HashMap<String, String>> result = MysqlConnectionUtils.query(sql);
            if (result.size() > 0) {
                VariablesResult = result.get(0).get("variablesvalue");
            }
        } catch (Exception e) {
            logger.info(logplannameandcasename + "查询计划下的批次中条件接口获取的中间变量异常...........: " + e.getMessage());
        }
        return VariablesResult;
    }



    //获取条件
    private  ArrayList<HashMap<String, String>>  GetConditionByPlanIDAndConditionType(Long Caseid,String ConditionType,String ObjectType)
    {
        ArrayList<HashMap<String, String>> result=new ArrayList<>();
        try {
            String sql = "select * from testcondition where objectid=" + Caseid+" and conditiontype='"+ ConditionType+"' and objecttype='"+ObjectType+"'";
            logger.info(logplannameandcasename + "获取条件 result sql is...........: " + sql);
            result = MysqlConnectionUtils.query(sql);
        } catch (Exception e) {
            logger.info(logplannameandcasename + "获取条件异常...........: " + e.getMessage());
        }
        return result;
    }

    //获取接口条件
    private  ArrayList<HashMap<String, String>>  GetApiConditionByConditionID(Long ConditionID)
    {
        ArrayList<HashMap<String, String>> result=new ArrayList<>();
        try {
            String sql = "select * from condition_api where conditionid=" + ConditionID ;
            logger.info(logplannameandcasename + "获取接口条件 result sql is...........: " + sql);
            result = MysqlConnectionUtils.query(sql);
        } catch (Exception e) {
            logger.info(logplannameandcasename + "获取接口条件异常...........: " + e.getMessage());
        }
        return result;
    }

    //获取脚本条件
    private  ArrayList<HashMap<String, String>>  GetScriptConditionByConditionID(Long ConditionID)
    {
        ArrayList<HashMap<String, String>> result=new ArrayList<>();
        try {
            String sql = "select * from condition_script where conditionid=" + ConditionID ;
            logger.info(logplannameandcasename + "获取脚本条件 result sql is...........: " + sql);
            result = MysqlConnectionUtils.query(sql);
        } catch (Exception e) {
            logger.info(logplannameandcasename + "获取脚本条件异常...........: " + e.getMessage());
        }
        return result;
    }

    //获取数据库条件
    private  ArrayList<HashMap<String, String>>  GetDBConditionByConditionID(Long ConditionID)
    {
        ArrayList<HashMap<String, String>> result=new ArrayList<>();
        try {
            String sql = "select * from condition_db where conditionid=" + ConditionID ;
            logger.info(logplannameandcasename + "获取数据库条件 result sql is...........: " + sql);
            result = MysqlConnectionUtils.query(sql);
        } catch (Exception e) {
            logger.info(logplannameandcasename + "获取数据库条件异常...........: " + e.getMessage());
        }
        return result;
    }



    //保存条件结果
    public void  SubConditionReportSave(TestconditionReport testconditionReport)
    {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        String sql = "insert testcondition_report (conditionid,conditiontype,subconditionid,conditionresult,conditionstatus,runtime,create_time,lastmodify_time,creator,batchname,planname,testplanid,subconditiontype,status,subconditionname)" +
                " values(" + testconditionReport.getConditionid() + ", '" + testconditionReport.getConditiontype() + "', " + testconditionReport.getSubconditionid() + ", '" + testconditionReport.getConditionresult() + "', '" + testconditionReport.getConditionstatus() + "', " + testconditionReport.getRuntime() + ", '" + dateNowStr + "', '" + dateNowStr + "','admin'"+", '"+testconditionReport.getBatchname()+"',  '"+testconditionReport.getPlanname()+"',"+testconditionReport.getTestplanid()+", '"+testconditionReport.getSubconditiontype()+"', '"+testconditionReport.getStatus()+"', '"+testconditionReport.getSubconditionname()+"')";
        logger.info(logplannameandcasename + "接口条件报告结果 result sql is...........: " + sql);
        logger.info(logplannameandcasename + "接口条件报告结果 result sql is...........: " + MysqlConnectionUtils.update(sql));
    }

    //保存变量结果
    public void  testVariablesValueSave(TestvariablesValue testvariablesValue)
    {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        String sql = "insert testvariables_value (planid,planname,caseid,casename,variablesid,variablesname,variablesvalue,memo,create_time,lastmodify_time,creator,batchname)" +
                " values(" + testvariablesValue.getPlanid() + ", '" + testvariablesValue.getPlanname() + "', " + testvariablesValue.getCaseid() + ", '" + testvariablesValue.getCasename() + "', " + testvariablesValue.getVariablesid() + ", '" + testvariablesValue.getVariablesname() + "', '" + testvariablesValue.getMemo() + ", '" + dateNowStr + "', '" + dateNowStr + "','admin')"+", '"+testvariablesValue.getBatchname()+"'";
        logger.info(logplannameandcasename + "保存变量结果 result sql is...........: " + sql);
        logger.info(logplannameandcasename + "保存变量结果 result sql is...........: " + MysqlConnectionUtils.update(sql));
    }

    //查询用例变量
    public ArrayList<HashMap<String, String>>  GetApiCaseVaribales(Long CaseID) {
        ArrayList<HashMap<String, String>> result=new ArrayList<>();
        try {
            String sql = "select *  from apicases_variables where caseid=" + CaseID ;
            logger.info(logplannameandcasename + "查询用例变量 result sql is...........: " + sql);
            result = MysqlConnectionUtils.query(sql);
        } catch (Exception e) {
            logger.info(logplannameandcasename + "查询用例变量异常...........: " + e.getMessage());
        }
        return result;
    }

    //查询变量
    public ArrayList<HashMap<String, String>>  GetVaribales(String VaribaleID) {
        ArrayList<HashMap<String, String>> result=new ArrayList<>();
        try {
            String sql = "select *  from testvariables where id=" + VaribaleID ;
            logger.info(logplannameandcasename + "查询变量 result sql is...........: " + sql);
            result = MysqlConnectionUtils.query(sql);
        } catch (Exception e) {
            logger.info(logplannameandcasename + "查询变量异常...........: " + e.getMessage());
        }
        return result;
    }

    // 获取用例Header，params，Body，Dubbo数据
    public HashMap<String, String> fixhttprequestdatas(String MapType, ArrayList<HashMap<String, String>> casedatalist) {
        HashMap<String, String> DataMap = new HashMap<>();
        for (HashMap<String, String> data : casedatalist) {
            String propertytype = data.get("propertytype");
            if (propertytype.equals(MapType)) {
                DataMap.put(data.get("apiparam").trim(), data.get("apiparamvalue").trim());
            }
        }
        return DataMap;
    }

    // 记录用例测试结果
    public void savetestcaseresult(boolean status, long time, String respone, String assertvalue, String errorinfo, RequestObject requestObject, JavaSamplerContext context) {
        //GetDBConnection();
        try {
            String resulttable = "";
            String casetype = "";
            String testplanid = "";
            String caseid = "";
            String slaverid = "";
            String expect = "";
            String batchname = "";
            String header = "";
            String params = "";
            if (requestObject == null) {
                casetype = context.getParameter("casetype");
                testplanid = context.getParameter("testplanid");
                caseid = context.getParameter("caseid");
                slaverid = context.getParameter("slaverid");
                expect = context.getParameter("expect");
                batchname = context.getParameter("batchname");
            } else {
                casetype = requestObject.getCasetype();// context.getParameter("casetype");
                testplanid = requestObject.getTestplanid();// context.getParameter("testplanid");
                caseid = requestObject.getCaseid();// context.getParameter("caseid");
                slaverid = requestObject.getSlaverid();// context.getParameter("slaverid");
                expect = requestObject.getExpect();// context.getParameter("expect");
                batchname = requestObject.getBatchname();// context.getParameter("batchname");
                Map<String, String> headermap = requestObject.getHeader().getParams();
                for (String key : headermap.keySet()) {
                    header = header + key + " ：" + headermap.get(key);
                }
                params=requestObject.getPostData();
                if(params==null)
                {
                    params="";
                }
            }

            if (casetype.equals(new String("功能"))) {
                resulttable = "apicases_report";
            }
            if (casetype.equals(new String("性能"))) {
                resulttable = "apicases_report_performance";
            }
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateNowStr = sdf.format(d);
            String sql = "";
            if (status) {
                sql = "insert " + resulttable + " (caseid,testplanid,batchname,slaverid,status,respone,assertvalue,runtime,expect,errorinfo,create_time,lastmodify_time,creator,requestheader,requestdatas)" +
                        " values(" + caseid + "," + testplanid + ", '" + batchname + "', " + slaverid + ", '成功" + "' , '" + respone + "' ,'" + assertvalue + "', " + time + ",'" + expect + "','" + errorinfo + "','" + dateNowStr + "', '" + dateNowStr + "','admin', '" + header + "', '" + params + "')";
            } else {
                sql = "insert  " + resulttable + " (caseid,testplanid,batchname,slaverid,status,respone,assertvalue,runtime,expect,errorinfo,create_time,lastmodify_time,creator,requestheader,requestdatas)" +
                        " values(" + caseid + "," + testplanid + ", '" + batchname + "', " + slaverid + ", '失败" + "' , '" + respone + "','" + assertvalue + "'," + time + ",'" + expect + "','" + errorinfo + "','" + dateNowStr + "','" + dateNowStr + "','admin', '" + header + "', '" + params + "')";
            }
            logger.info(logplannameandcasename + "测试结果 result sql is...........: " + sql);
            System.out.println("case result sql is: " + sql);
            logger.info(logplannameandcasename + "记录用例测试结果 result sql is...........: " + MysqlConnectionUtils.update(sql));
        } catch (Exception ex) {
            logger.info(logplannameandcasename + "记录用例测试结果异常...........: " + ex.getMessage());
        }
    }

    // 记录用例测试结果
    public void SaveReportStatics(ApicasesReportstatics apicasesReportstatics) {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        String sql = "insert apicases_reportstatics (testplanid,deployunitid,batchname,slaverid,totalcases,totalpasscases,totalfailcases,runtime,create_time,lastmodify_time,creator)" +
                " values(" + apicasesReportstatics.getTestplanid() + "," + apicasesReportstatics.getDeployunitid() + ", '" + apicasesReportstatics.getBatchname() + "', " + apicasesReportstatics.getSlaverid() + ", " + apicasesReportstatics.getTotalcases() + ", " + apicasesReportstatics.getTotalpasscases() + ", " + apicasesReportstatics.getTotalfailcases() + ", " + apicasesReportstatics.getRuntime() + ", '" + dateNowStr + "', '" + dateNowStr + "','admin')";
        logger.info(logplannameandcasename + "功能测试统计结果 result sql is...........: " + sql);
        System.out.println("case result sql is: " + sql);
        logger.info(logplannameandcasename + "功能测试统计结果 result sql is...........: " + MysqlConnectionUtils.update(sql));
    }

    //查询此计划下的批次调度是否已经全部完成，如果完成，刷新计划批次状态为finish
    public void PlanBatchAllDipatchFinish(ApicasesReportstatics apicasesReportstatics) {
        long DispatchNotFinishNums = 0;
        try {
            String sql = "select count(*) as nums from dispatch where execplanid=" + apicasesReportstatics.getTestplanid() + " and batchname= '" + apicasesReportstatics.getBatchname() + "' and status in('待分配','已分配')";
            logger.info(logplannameandcasename + "查询计划下的批次调度是否已经全部完成 result sql is...........: " + sql);
            ArrayList<HashMap<String, String>> result = MysqlConnectionUtils.query(sql);
            DispatchNotFinishNums = Long.parseLong(getcaseValue("nums", result));
        } catch (Exception e) {
            logger.info(logplannameandcasename + "查询计划下的批次调度是否已经全部完成异常...........: " + e.getMessage());
        }
        if (DispatchNotFinishNums > 0) {
            logger.info(logplannameandcasename + "查询计划下的批次调度未完成数量：" + DispatchNotFinishNums);
        } else {
            UpdateReportStatics(apicasesReportstatics.getTestplanid(), apicasesReportstatics.getBatchname(), "已完成");
        }
    }

    //查询此计划下的批次调度是否已经全部完成，如果完成，刷新计划批次状态为finish
    public void PlanBatchAllDipatchFinish(String Testplanid, String batchname) {
        long DispatchNotFinishNums = 0;
        try {
            String sql = "select count(*) as nums from dispatch where execplanid=" + Testplanid + " and batchname= '" + batchname + "' and status in('待分配','已分配')";
            logger.info(logplannameandcasename + "查询计划下的批次调度是否已经全部完成 result sql is...........: " + sql);
            ArrayList<HashMap<String, String>> result = MysqlConnectionUtils.query(sql);
            DispatchNotFinishNums = Long.parseLong(getcaseValue("nums", result));
        } catch (Exception e) {
            logger.info(logplannameandcasename + "查询计划下的批次调度是否已经全部完成异常...........: " + e.getMessage());
        }
        if (DispatchNotFinishNums > 0) {
            logger.info(logplannameandcasename + "查询计划下的批次调度未完成数量：" + DispatchNotFinishNums);
        } else {
            UpdateReportStatics(Testplanid, batchname, "已完成");
        }
    }

    // 更新计划批次状态
    public void UpdateReportStatics(String Planid, String BatchName, String status) {
        String UpdateSql = "update  executeplanbatch set status='" + status + "' where executeplanid=" + Planid + " and batchname= '" + BatchName + "'";
        logger.info(logplannameandcasename + "更新计划批次状态结果完成  sql is...........: " + UpdateSql);
        logger.info(logplannameandcasename + "更新计划批次状态结果完成 result sql is...........: " + MysqlConnectionUtils.update(UpdateSql));
    }

    // 更新Slaver状态
    public void UpdateSlaverStatus(String Slaverid, String status) {
        String UpdateSql = "update  slaver set status='" + status + "' where id=" + Slaverid;
        logger.info(logplannameandcasename + "更新Slaver状态结果完成  sql is...........: " + UpdateSql);
        logger.info(logplannameandcasename + "更新Slaver状态结果完成 result sql is...........: " + MysqlConnectionUtils.update(UpdateSql));
    }

    //处理前置条件
    public void fixprecondition(String planid, String testcaseid) throws Exception {
        ArrayList<HashMap<String, String>> preconditionlist = getcaseData("select * from apicases_condition where basetype ='前置条件' and target='用例' and  caseid=" + testcaseid);
        fixcondition(preconditionlist, "前置", planid);
    }

    //处理后置条件
    public void fixpostcondition(String planid, String testcaseid) throws Exception {
        ArrayList<HashMap<String, String>> postconditionlist = getcaseData("select * from apicases_condition where basetype ='后置条件' and target='用例' and  caseid=" + testcaseid);
        fixcondition(postconditionlist, "后置", planid);
    }

    //处理前后置条件
    public void fixcondition(ArrayList<HashMap<String, String>> list, String type, String testplanid) throws Exception {
        for (HashMap<String, String> hs : list) {
            // 数据库
            if (hs.get("conditionbasetype").equals(new String("数据库"))) {
                String sql = hs.get("conditionvalue");
                String[] sqlarray = sql.split(";");
                logger.info(logplannameandcasename + type + "条件数据库sql语句。。。。。。" + sql);
                String assembleid = hs.get("envassemid");
                String machineipsql = "select d.ip,b.connectstr from  enviroment_assemble b join macdepunit c join machine d join executeplan e on e.envid=c.envid and c.machineid=d.id and b.id=c.assembleid where c.assembleid =" + assembleid + " and e.id=" + testplanid;
                logger.info(logplannameandcasename + type + "获取数据库机器ip和连接字sql：。。。。。。" + machineipsql);

                ArrayList<HashMap<String, String>> ipconnectstrlist = getcaseData(machineipsql);
                String dbip = getcaseValue("ip", ipconnectstrlist);
                String connectstrvalue = getcaseValue("connectstr", ipconnectstrlist);
                logger.info(logplannameandcasename + type + "条件数据库连接字。。。。。。" + connectstrvalue);

                String[] connectstrarray = connectstrvalue.split(",");
                HashMap<String, String> tmpmap = new HashMap<String, String>();
                for (String value : connectstrarray) {
                    if (value.contains("=")) {
                        tmpmap.put(value.split("=")[0], value.split("=")[1]);
                    } else {
                        throw new Exception(type + "连接字不符合规范：" + connectstrvalue);
                    }
                }
                String username = tmpmap.get("username");
                String password = tmpmap.get("password");
                String dbname = tmpmap.get("db");
                String port = tmpmap.get("port");
                String dbconnect = "jdbc:mysql://" + dbip + ":" + port + "/" + dbname;
                logger.info(logplannameandcasename + type + "条件数据库连接url。。。。。。" + dbconnect);

                if (hs.get("conditiontype").equals(new String("mysql"))) {
                    MysqlConnectionUtils.getConnectionbycon(dbconnect, username, password);
                    for (String execsql : sqlarray) {
                        MysqlConnectionUtils.execsql(execsql);
                    }
                }
            }
            if (hs.get("conditionbasetype").equals(new String("接口"))) {
                // 接口
                logger.info(logplannameandcasename + type + "条件接口。。。。。。");
                // 根据caseid获取请求准备数据，然后发送请求
                String planid = hs.get("execplanid");

                String conditioncaseid = hs.get("conditioncaseid");
                RequestObject newob = GetCaseRequestData(planid, conditioncaseid, "", "", "", "");
//                String result = request(newob);
//                logger.info(logplannameandcasename + type + "条件接口返回：。。。。。。" + result);
            }
        }
    }

    // 记录前后置条件测试结果
    public String savetestcaseconditionresult(String testcaseid, String planid, String batchnameid, String batchnames, String slaverids, String status, String error, String condition, String testcasetype) {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        String sql = "";
        sql = "insert apicases_condition_report (caseid,testplanid,batchid,batchname,slaverid,conditiontype,casetype,status,errorinfo,create_time,lastmodify_time,creator)" +
                " values(" + testcaseid + "," + planid + ", " + batchnameid + ", '" + batchnames + "', " + slaverids + ", '" + condition + "', '" + testcasetype + "', '" + status + "','" + error + "','" + dateNowStr + "', '" + dateNowStr + "','admin')";
        logger.info(logplannameandcasename + "前后置条件测试结果 result sql is...........: " + sql);
        logger.info(logplannameandcasename + "前后置条件测试结果 result  is...........: " + MysqlConnectionUtils.update(sql));
        return MysqlConnectionUtils.update(sql);
    }


    // 更新用例调度结果
    public void updatedispatchcasestatus(String testplanid, String caseid, String slaverid, String batchid) {
        try {
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateNowStr = sdf.format(d);
            String sql = "";
            sql = "update dispatch set status='已完成',lastmodify_time='" + dateNowStr + "' where slaverid=" + slaverid + " and execplanid=" + testplanid + " and batchid=" + batchid + " and testcaseid=" + caseid;
            logger.info(logplannameandcasename + "更新调度用例状态 result sql is...........: " + sql);
            System.out.println("case result sql is: " + sql);
            logger.info(logplannameandcasename + "更新用例调度结果 is...........: " + MysqlConnectionUtils.update(sql));
        } catch (Exception ex) {
            logger.info(logplannameandcasename + "更新用例调度结果异常...........: " + ex.getMessage());
        }
    }

    //生成性能报告目录
    public void genealperformacestaticsreport(String testclass, String batchname, String testplanid, String batchid, String slaverid, String caseid, String casereportfolder, double costtime) throws Exception {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        String sql = "";
        sql = "insert performancereportsource (planid,batchid,batchname,slaverid,caseid,testclass,runtime,source,status,create_time,lastmodify_time)" +
                " values(" + testplanid + "," + batchid + ", '" + batchname + "', " + slaverid + ", " + caseid + " , '" + testclass + "' ," + costtime + " , '" + casereportfolder + "', '待解析', '" + dateNowStr + "', '" + dateNowStr + "')";
        logger.info(logplannameandcasename + "保存性能结果 sql is...........: " + sql);
        logger.info(logplannameandcasename + "保存性能结果 is...........: " + MysqlConnectionUtils.update(sql));
    }
}
