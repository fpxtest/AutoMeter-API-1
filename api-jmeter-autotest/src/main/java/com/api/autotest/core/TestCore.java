package com.api.autotest.core;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.api.autotest.common.utils.*;
import com.api.autotest.dto.*;
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
        logger.info("InitHttpDatabyJmeter 完成-============：");

        RequestObject newob=testHttpRequestData.GetPerformanceHttpRequestData(requestObject);
        logger.info("GetPerformanceHttpRequestData 完成-============：");

//        RequestObject newob = new RequestObject();
//
//        String casename = context.getParameter("casename");
//        String executeplanname = context.getParameter("executeplanname");
//        logplannameandcasename = executeplanname + "--" + casename + " :";
//        logger.info(logplannameandcasename + "casename is :  " + casename);
//        logger.info(logplannameandcasename + "executeplanname is :  " + executeplanname);
//
//        String testplanid = context.getParameter("testplanid");
//        String caseid = context.getParameter("caseid");
//        String slaverid = context.getParameter("slaverid");
//        String batchid = context.getParameter("batchid");
//        String batchname = context.getParameter("batchname");
//
//        String RequestmMthod = context.getParameter("RequestmMthod");
//        logger.info(logplannameandcasename + "RequestmMthod is :  " + RequestmMthod);
//
//        String resource = context.getParameter("resource");
//        logger.info(logplannameandcasename + "resource is :  " + resource);
//
//        String apistyle = context.getParameter("apistyle");
//        logger.info(logplannameandcasename + "apistyle is :  " + apistyle);
//
//        String requestcontenttype = context.getParameter("requestcontenttype");
//        logger.info(logplannameandcasename + "requestcontenttype is :  " + requestcontenttype);
//
//        String responecontenttype = context.getParameter("responecontenttype");
//        logger.info(logplannameandcasename + "responecontenttype is :  " + responecontenttype);
//
//        String headjson = context.getParameter("headjson");
//        logger.info(logplannameandcasename + "headjson is :  " + headjson);
//
//        String paramsjson = context.getParameter("paramsjson");
//        logger.info(logplannameandcasename + "paramsjson is :  " + paramsjson);
//
//        String bodyjson = context.getParameter("bodyjson");
//        String dubbo = context.getParameter("dubbojson");
//        String expect = context.getParameter("expect");
//
//        List<ApicasesAssert> apicasesAssertList = new ArrayList<>();
//        if (expect.isEmpty()) {
//            newob.setApicasesAssertList(apicasesAssertList);
//        } else {
//            apicasesAssertList = JSONObject.parseArray(expect, ApicasesAssert.class);
//            newob.setApicasesAssertList(apicasesAssertList);
//        }
//        expect = GetAssertInfo(apicasesAssertList);
//        logger.info(logplannameandcasename + "expect is :  " + expect);
//
//        String casetype = context.getParameter("casetype");
//        logger.info(logplannameandcasename + "casetype is :  " + casetype);
//
//        String protocal = context.getParameter("protocal");
//        logger.info(logplannameandcasename + "protocal is :  " + protocal);
//
//        //GetExpectMap(expect);
//        newob.setCaseid(caseid);
//        newob.setCasename(casename);
//        newob.setTestplanid(testplanid);
//        newob.setSlaverid(slaverid);
//        newob.setBatchname(batchname);
//        newob.setBatchid(batchid);
//        newob.setExpect(expect);
//        newob.setCasetype(casetype);
//
//        newob.setResponecontenttype(responecontenttype);
//        if (resource == null) {
//            throw new Exception("测试集合对应的环境中未部署发布单元，请在测试环境中完成部署发布单元后再运行");
//        }
//        newob.setResource(resource);
//        newob.setRequestmMthod(RequestmMthod);
//        newob.setRequestcontenttype(requestcontenttype);
//        newob.setApistyle(apistyle);
//        newob.setProtocal(protocal);
//
//        HttpHeader header = new HttpHeader();
//        if (headjson.equals("NoCaseData")) {
//            //表示api设置了header参数，但是用例无数据
//            throw new Exception("API的Header参数未设计测试用例数据，请完成用例数据后再运行");
//        } else {
//            if (headjson.equals("NN")) {
//                newob.setHeader(header);
//            } else {
//                Map headermaps = (Map) JSON.parse(headjson);
//                for (Object map : headermaps.entrySet()) {
//                    String Value = ((Map.Entry) map).getValue().toString();
//                    Value = GetVariablesValues(Value, testplanid, batchname);
//                    header.addParam(((Map.Entry) map).getKey().toString(), Value);
//                }
//                newob.setHeader(header);
//            }
//        }
//        HttpParamers params = new HttpParamers();
//        if (paramsjson.equals("NoCaseData")) {
//            throw new Exception("API的Params参数未设计测试用例数据，请完成用例数据后再运行");
//        } else {
//            String PostData = "";
//            if (paramsjson.equals("NN")) {
//                newob.setPostData(PostData);
//            } else {
//                Map paramsmaps = (Map) JSON.parse(paramsjson);
//                for (Object map : paramsmaps.entrySet()) {
//                    String Value = ((Map.Entry) map).getValue().toString();
//                    Value = GetVariablesValues(Value, testplanid, batchname);
//                    params.addParam(((Map.Entry) map).getKey().toString(), Value);
//                }
//                if (params.getParams().size() > 0) {
//                    PostData = GetParasPostData(requestcontenttype, params);
//                    newob.setPostData(PostData);
//                }
//            }
//            newob.setParamers(params);
//        }
//        if (bodyjson.equals("NoCaseData")) {
//            throw new Exception("API的Body参数未设计测试用例数据，请完成用例数据后再运行");
//        } else {
//            String PostData = "";
//            if (paramsjson.equals("NN")) {
//                newob.setPostData(PostData);
//            } else {
//                newob.setPostData(bodyjson);
//            }
//        }
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

    // 功能用例拼装请求需要的用例数据
//    public RequestObject GetCaseRequestData(String PlanId, String TestCaseId, String SlaverId, String BatchId, String BatchName, String ExecPlanName) {
//        RequestObject ro = new RequestObject();
//        //ArrayList<HashMap<String, String>> planlist = getcaseData("select * from executeplan where id=" + PlanId);
//        ArrayList<HashMap<String, String>> deployunitmachineiplist = getcaseData("select m.ip,a.domain,a.visittype from macdepunit a INNER JOIN apicases b INNER JOIN executeplan c JOIN machine m on a.depunitid=b.deployunitid and  a.envid=c.envid and  m.id=a.machineid where b.id=" + TestCaseId + " and c.id=" + PlanId);
//        ArrayList<HashMap<String, String>> caselist = getcaseData("select * from apicases where id=" + TestCaseId);
//        ArrayList<HashMap<String, String>> caseassertlist = getcaseData("select * from apicases_assert where caseid=" + TestCaseId);
//        ArrayList<HashMap<String, String>> apilist = getcaseData("select b.visittype,b.apistyle,b.path,b.requestcontenttype,b.responecontenttype from apicases a inner join api b on a.apiid=b.id where a.id=" + TestCaseId);
//        ArrayList<HashMap<String, String>> deployunitlist = getcaseData("select b.protocal,b.port,b.id from apicases a inner join deployunit b on a.deployunitid=b.id where a.id=" + TestCaseId);
//
//        //获取断言记录
//        TestAssert testAssert = new TestAssert(logger);
//        List<ApicasesAssert> apicasesAssertList = testAssert.GetApicasesAssertList(caseassertlist);
//        ro.setApicasesAssertList(apicasesAssertList);
//        // url请求资源路径
//        String path = getcaseValue("path", apilist);
//        if (!path.startsWith("/")) {
//            path = "/" + path;
//        }
//        String casetype = getcaseValue("casetype", caselist);
//
//        String CaseName = getcaseValue("casename", caselist);
//
//        String expect = GetAssertInfo(apicasesAssertList); //getcaseValue("expect", caselist);
//
//        String Apiid = getcaseValue("apiid", caselist);
//
//        //获取请求响应的数据格式
//        String requestcontenttype = getcaseValue("requestcontenttype", apilist);
//        String responecontenttype = getcaseValue("responecontenttype", apilist);
//        // http请求方式 get，post
//        String method = getcaseValue("visittype", apilist).toLowerCase();
//        // api风格
//        String apistyle = getcaseValue("apistyle", apilist).toLowerCase();
//        // 协议 http,https,rpc
//        String protocal = getcaseValue("protocal", deployunitlist).toLowerCase();
//        // 发布单元端口
//        String port = getcaseValue("port", deployunitlist);
//        String deployunitid = getcaseValue("id", deployunitlist);
//
//        // 获取发布单元访问方式，ip或者域名
//        String deployunitvisittype = getcaseValue("visittype", deployunitmachineiplist);
//        // 根据访问方式来确定ip还是域名
//        String testserver = "";
//        String resource = "";
//        if (deployunitvisittype.equals(new String("ip"))) {
//            testserver = getcaseValue("ip", deployunitmachineiplist);
//            resource = protocal + "://" + testserver + ":" + port + path;
//        } else {
//            testserver = getcaseValue("domain", deployunitmachineiplist);
//            resource = protocal + "://" + testserver + path;
//        }
//        logger.info(logplannameandcasename + "resource is :  " + resource + "   protocal  is:   " + protocal + "  expect is :      " + expect + "  visittype is: " + method + "   path is: " + path + " casetype is: " + casetype);
//
//        ro.setCaseid(TestCaseId);
//        ro.setCasename(CaseName);
//        ro.setDeployunitid(deployunitid);
//        ro.setTestplanid(PlanId);
//        ro.setSlaverid(SlaverId);
//        ro.setBatchname(BatchName);
//        ro.setTestplanname(ExecPlanName);
//        ro.setBatchid(BatchId);
//        ro.setExpect(expect);
//        ro.setCasetype(casetype);
//        ro.setProtocal(protocal);
//        ro.setApistyle(apistyle);
//        ro.setRequestcontenttype(requestcontenttype);
//        ro.setRequestmMthod(method);
//        ro.setResource(resource);
//        ro.setResponecontenttype(responecontenttype);
//        return ro;
//    }
//
//    public RequestObject GetRequestParamsData(RequestObject requestObject) {
//        RequestObject Result = requestObject;
//        String TestCaseId = requestObject.getCaseid();
//        String PlanId = requestObject.getTestplanid();
//        String BatchName = requestObject.getBatchname();
//        String requestcontenttype = requestObject.getRequestcontenttype();
//        ArrayList<HashMap<String, String>> caselist = getcaseData("select * from apicases where id=" + TestCaseId);
//        String APIId = getcaseValue("apiid", caselist);
//
//
//        ArrayList<HashMap<String, String>> casedatalist = getcaseData("select * from api_casedata where caseid=" + TestCaseId);
//        ArrayList<HashMap<String, String>> caseptlist = getcaseData("select DISTINCT propertytype  from api_params where apiid=" + APIId);
//        ArrayList<HashMap<String, String>> planparamslist = getcaseData("select * from executeplan_params where executeplanid=" + requestObject.getTestplanid());
//
//        List<String> PropertyList = GetCaseDataPropertyType(caseptlist);
//        HttpHeader header = new HttpHeader();
//        HttpParamers paramers = new HttpParamers();
//        String PostData = "";
//        for (String property : PropertyList) {
//            if (property.equals("Header")) {
//                header = GetHttpHeader(casedatalist, header, PlanId, TestCaseId, BatchName);
//            }
//            if (property.equals("Params")) {
//                paramers = GetHttpParams(casedatalist, paramers, PlanId, TestCaseId, BatchName);
//                if (paramers.getParams().size() > 0) {
//                    PostData = GetParasPostData(requestcontenttype, paramers);
//                }
//            }
//            logger.info(logplannameandcasename + "PostData is after params :  " + PostData );
//
//            if (property.equals("Body")) {
//                // 设置Body
//                HashMap<String, String> bodymap = fixhttprequestdatas("Body", casedatalist);
//                for (String Key : bodymap.keySet()) {
//                    PostData = bodymap.get(Key);
//                }
//                logger.info(logplannameandcasename + "PostData is after body :  " + PostData );
//            }
//        }
//        //处理全局参数
//        header = GetHeaderFromTestPlanParam(header, planparamslist, PlanId, BatchName);
//        PostData = GetBodyFromTestPlanParam(PostData, planparamslist);
//
//        logger.info(logplannameandcasename + "PostData is after 全局参数 :  " + PostData );
//
//        Result.setHeader(header);
//        Result.setParamers(paramers);
//        Result.setPostData(PostData);
//        return Result;
//    }
//
////    private  JSONObject jsonLoop(Object object,String PlanId,String BatchName) {
////        JSONObject jsonObject=null;
////        if (object instanceof JSONObject) {
////            jsonObject = (JSONObject) object;
////            for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
////                Object o = entry.getValue();
////                if (o instanceof String) {
////                    String Value=entry.getValue().toString();
////                    Value = GetVariablesValues(Value, PlanId, BatchName);
////                    jsonObject.put(entry.getKey(),Value);
////                }
////
////                if (o instanceof JSONArray) {
////                    if(!o.toString().contains("{"))
////                    {
////                        System.out.println("key:" + entry.getKey() + "，value:" + entry.getValue());
////                    }
////                    else
////                    {
////                        jsonLoop(o,PlanId,BatchName);
////                    }
////                }
////            }
////        }
////        if (object instanceof JSONArray) {
////            JSONArray jsonArray = (JSONArray) object;
////            for (int i = 0; i < jsonArray.size(); i++) {
////                jsonLoop(jsonArray.get(i),PlanId,BatchName);
////            }
////        }
////        return jsonObject;
////    }
//    //全局Header参数
//    private HttpHeader GetHeaderFromTestPlanParam(HttpHeader header, ArrayList<HashMap<String, String>> planparamslist, String PlanId, String BatchName) {
//        HashMap<String, String> headmapfromparam = getparamsdatabytype("Header", planparamslist);
//        for (String key : headmapfromparam.keySet()) {
//            String Value = headmapfromparam.get(key);
//            Object ObjectValue = GetVariablesObjectValues(Value, PlanId, BatchName);
//            //如果有相同的参数，则以全局参数的覆盖之
//            if (header.getParams().containsKey(key)) {
//                header.getParams().put(key, ObjectValue);
//                logger.info(logplannameandcasename + "全局参数Header中添加Key is :  " + key + "   Value  is:   " + ObjectValue);
//            }
//        }
//        return header;
//    }
//
//    //全局Body参数
//    private String GetBodyFromTestPlanParam(String PostData, ArrayList<HashMap<String, String>> planparamslist) {
//        HashMap<String, String> bodymapfromparam = getparamsdatabytype("Body", planparamslist);
//        for (String key : bodymapfromparam.keySet()) {
//            String Value = bodymapfromparam.get(key);
//            PostData = Value;
//            logger.info(logplannameandcasename + "全局参数BodyPostData is :  " + PostData);
//        }
//        return PostData;
//    }
//
//    //获取断言信息
//    private String GetAssertInfo(List<ApicasesAssert> apicasesAssertList) {
//        String expectValue = "";
//        if (apicasesAssertList.size() > 0) {
//            for (ApicasesAssert apicasesAssert : apicasesAssertList) {
//                if (apicasesAssert.getAsserttype().equals(new String("Respone"))) {
//                    expectValue = expectValue + "【断言类型：" + apicasesAssert.getAsserttype() + "， 断言子类型：" + apicasesAssert.getAssertsubtype() + "， 断言条件：" + apicasesAssert.getAssertcondition() + "， 断言值：" + apicasesAssert.getAssertvalues() + "， 断言值类型：" + apicasesAssert.getAssertvaluetype() + "】";
//                } else {
//                    expectValue = expectValue + "【断言类型：" + apicasesAssert.getAsserttype() + "， 断言表达式：" + apicasesAssert.getExpression() + "， 断言条件：" + apicasesAssert.getAssertcondition() + "， 断言值：" + apicasesAssert.getAssertvalues() + "， 断言值类型：" + apicasesAssert.getAssertvaluetype() + "】";
//                }
//            }
//        }
//        return expectValue;
//    }
//
//
//    //获取参数值的具体内容，支持$变量
//    private Object GetVariablesObjectValues(String Value, String PlanId, String BatchName) {
//        Object Result = "";
//        if(Value.trim().contains("$"))
//        {
//            if(Value.trim().length()==1)
//            {
//                Result=Value;
//            }
//            else
//            {
//                Value = Value.substring(1);
//                String Caseid = GetCaseIdByVariablesName(Value);
//                String ValueType = GetVariablesDataType(Value);
//                //根据用例参数值是否以$开头，如果是则认为是变量通过变量表取到变量值
//                String VariablesNameValue = GetVariablesValues(PlanId, Caseid, BatchName, Value);
//                if(ValueType.equals("Number"))
//                {
//                    try
//                    {
//                        Result=Long.parseLong(VariablesNameValue);
//                    }
//                    catch (Exception ex)
//                    {
//                        Result="变量值："+VariablesNameValue+" 不是数字类型，请检查！";
//                    }
//                }
//                if(ValueType.equals("String"))
//                {
//                    Result=VariablesNameValue;
//                }
//                if(ValueType.equals("Array"))
//                {
//                    String[] Array=VariablesNameValue.split(",");
//                    Result=Array;
//                }
//                if(ValueType.equals("Bool"))
//                {
//                    try
//                    {
//                        Result=Boolean.parseBoolean(VariablesNameValue);
//                    }
//                    catch (Exception ex)
//                    {
//                        Result="变量值："+VariablesNameValue+" 不是布尔类型，请检查！";
//                    }
//                }
//            }
//        }
//        else
//        {
//            Result=Value;
//        }
//        return Result;
//    }
//
//    //获取参数值的具体内容，支持$变量，以及$变量和字符串拼接
//    private String GetVariablesValues(String Value, String PlanId, String BatchName) {
//        String Result = "";
//        if (Value.contains("+")) {
//            String[] Array = Value.split("\\+");
//            for (String str : Array) {
//                if (str.contains("$")) {
//                    String VariablesName = str.substring(1);
//                    String Caseid = GetCaseIdByVariablesName(VariablesName);
//                    //根据用例参数值是否以$开头，如果是则认为是变量通过变量表取到变量值
//                    String VariablesNameValue = GetVariablesValues(PlanId, Caseid, BatchName, VariablesName);
//                    Result = Result + VariablesNameValue;
//                } else {
//                    Result = Result + str;
//                }
//            }
//        } else {
//            if (Value.contains("$")) {
//                String VariablesName = Value.substring(1);
//                String Caseid = GetCaseIdByVariablesName(VariablesName);
//                //根据用例参数值是否以$开头，如果是则认为是变量通过变量表取到变量值
//                String VariablesNameValue = GetVariablesValues(PlanId, Caseid, BatchName, VariablesName);
//                Result = VariablesNameValue;
//            } else {
//                Result = Value;
//            }
//        }
//        return Result;
//    }
//
//
//    private HttpHeader GetHttpHeader(ArrayList<HashMap<String, String>> casedatalist, HttpHeader header, String PlanId, String TestCaseId, String BatchName) {
//        // 设置header
//        HashMap<String, String> headmap = fixhttprequestdatas("Header", casedatalist);
//        for (String key : headmap.keySet()) {
//            String Value = headmap.get(key);
//            Object ObjectValue = GetVariablesObjectValues(Value, PlanId, BatchName);
//            header.addParam(key, ObjectValue);
//            logger.info(logplannameandcasename + "Header中添加Key is :  " + key + "   Value  is:   " + ObjectValue);
//        }
//        return header;
//    }
//
//    private HttpParamers GetHttpParams(ArrayList<HashMap<String, String>> casedatalist, HttpParamers paramers, String PlanId, String TestCaseId, String BatchName) {
//        // 设置参数params
//        HashMap<String, String> paramsmap = fixhttprequestdatas("Params", casedatalist);
//        for (String key : paramsmap.keySet()) {
//            String Value = paramsmap.get(key);
//            Object ObjectValue = GetVariablesObjectValues(Value, PlanId, BatchName);
//            paramers.addParam(key, ObjectValue);
//            logger.info(logplannameandcasename + "Params中添加Key is :  " + key + "   Value  is:   " + ObjectValue);
//        }
//        return paramers;
//    }
//
//    private List<String> GetCaseDataPropertyType(ArrayList<HashMap<String, String>> casedataptlist) {
//        List<String> PropertyType = new ArrayList<>();
//        for (HashMap<String, String> hs : casedataptlist) {
//            for (String property : hs.keySet()) {
//                PropertyType.add(hs.get(property));
//            }
//        }
//        return PropertyType;
//    }
//
//    private String GetParasPostData(String RequestContentType, HttpParamers paramers) {
//        String Result = "";
//        if (RequestContentType.equalsIgnoreCase("json")) {
//            paramers.setJsonParamer();
//            Result = paramers.getJsonParamer();
//        }
//        if (RequestContentType.equalsIgnoreCase("form表单")) {
//            Result = paramers.getQueryString();
//        }
//        if (RequestContentType.equalsIgnoreCase("xml")) {
//
//        } else {
//
//        }
//        return Result;
//    }

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
                    if(conditionorder.get("subconditiontype").equals("接口"))
                    {
                        logger.info("TestCore 开始处理用例前置条件顺序-API子条件-============：");
                        testCondition.APICondition(ConditionID, requestObject);
                        logger.info("TestCore 完成处理用例前置条件顺序-API子条件-============：");
                    }
                    if(conditionorder.get("subconditiontype").equals("数据库"))
                    {
                        logger.info("TestCore 开始处理用例前置条件顺序-数据库子条件-============：");
                        testCondition.DBCondition(ConditionID, requestObject);
                        logger.info("TestCore 完成处理用例前置条件顺序-数据库子条件-============：");
                    }
                    if(conditionorder.get("subconditiontype").equals("脚本"))
                    {
                        logger.info("TestCore 开始处理用例前置条件顺序-脚本子条件-============：");
                        testCondition.ScriptCondition(ConditionID, requestObject);
                        logger.info("TestCore 完成处理用例前置条件顺序-脚本子条件-============：");
                    }
                }
            }
            else
            {
                //处理接口条件
                logger.info("TestCore 开始处理用例前置条件-API子条件-============：");
                testCondition.APICondition(ConditionID, requestObject);
                logger.info("TestCore 完成处理用例前置条件-API子条件-============：");
                //处理数据库条件
                testCondition.DBCondition(ConditionID, requestObject);
                //处理脚本条件
                logger.info("TestCore 开始处理用例前置条件-脚本子条件-============：");
                testCondition.ScriptCondition(ConditionID, requestObject);
                logger.info("TestCore 完成处理用例前置条件-脚本子条件-============：");
            }
        }
    }

    //处理接口条件
//    public void APICondition(long ConditionID, RequestObject requestObject) throws Exception {
//        ArrayList<HashMap<String, String>> conditionApiList = GetApiConditionByConditionID(ConditionID);
//        Long PlanID = Long.parseLong(requestObject.getTestplanid());
//        logger.info("条件报告API子条件数量-============：" + conditionApiList.size());
//        for (HashMap<String, String> conditionApi : conditionApiList) {
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
//                ResponeData responeData = request(re);
//                Respone = responeData.getRespone();
//                CostTime = End - Start;
//                SaveApiSubCondition(re, requestObject.getCasename(), PlanID, requestObject.getTestplanname(), requestObject.getBatchname(), Long.parseLong(CondionCaseID), ConditionID, conditionApi, Respone, ConditionResultStatus, CostTime);
//            } catch (Exception ex) {
//                ConditionResultStatus = "失败";
//                End = new Date().getTime();
//                Respone = ex.getMessage();
//                CostTime = End - Start;
//                SaveApiSubCondition(re,requestObject.getCasename(), PlanID, requestObject.getTestplanname(), requestObject.getBatchname(), Long.parseLong(CondionCaseID), ConditionID, conditionApi, Respone, ConditionResultStatus, CostTime);
//                throw new Exception("接口子条件执行异常：" + ex.getMessage());
//            }
//        }
//    }
//
//    private void SaveApiSubCondition(RequestObject requestObject, String CaseName, Long PlanID, String PlanName, String BatchName, Long CaseID, Long ConditionID, HashMap<String, String> conditionApi, String Respone, String ConditionResultStatus, long CostTime) {
//        TestconditionReport testconditionReport = new TestconditionReport();
//        testconditionReport.setTestplanid(PlanID);
//        testconditionReport.setPlanname(CaseName);
//        testconditionReport.setBatchname(BatchName);
//        testconditionReport.setConditionid(new Long(ConditionID));
//        testconditionReport.setConditiontype("前置条件");
//        testconditionReport.setConditionresult(Respone);
//        testconditionReport.setConditionstatus(ConditionResultStatus);
//        testconditionReport.setRuntime(CostTime);
//        testconditionReport.setSubconditionid(Long.parseLong(conditionApi.get("id")));
//        testconditionReport.setSubconditionname(conditionApi.get("subconditionname"));
//        testconditionReport.setSubconditiontype("接口");
//        testconditionReport.setStatus("已完成");
//        logger.info("条件报告保存子条件已完成状态-============：" + testconditionReport.getPlanname() + "|" + testconditionReport.getBatchname() + "|" + requestObject.getCasename());
//        SubConditionReportSave(testconditionReport);
//        //根据用例是否有中间变量，如果有变量，解析（json，xml，html）保存变量值表，没有变量直接保存条件结果表
//        ArrayList<HashMap<String, String>> apicasesVariablesList = GetApiCaseVaribales(CaseID);
//        if (apicasesVariablesList.size() > 0) {
//            logger.info("条件报告子条件处理变量-============：" + apicasesVariablesList.get(0).get("variablesname"));
//            String Variablesid = apicasesVariablesList.get(0).get("id");
//            ArrayList<HashMap<String, String>> VariablesList = GetVaribales(Variablesid);
//            if (VariablesList.size() > 0) {
//                String VariablesPath = VariablesList.get(0).get("variablesexpress");
//                logger.info("条件报告子条件处理变量表达式-============：" + VariablesPath + " 响应数据类型" + requestObject.getResponecontenttype());
//                TestAssert testAssert = new TestAssert(logger);
//                String ParseValue = testAssert.ParseRespone(requestObject.getResponecontenttype(), VariablesPath, Respone);
//                logger.info("条件报告子条件处理变量取值-============：" + ParseValue);
//                TestvariablesValue testvariablesValue = new TestvariablesValue();
//                testvariablesValue.setPlanid(PlanID);
//                testvariablesValue.setPlanname(PlanName);
//                testvariablesValue.setBatchname(BatchName);
//                testvariablesValue.setCaseid(CaseID);
//                testvariablesValue.setCasename(requestObject.getCasename());
//                testvariablesValue.setVariablesid(Long.parseLong(VariablesList.get(0).get("id")));
//                testvariablesValue.setVariablesname(VariablesList.get(0).get("testvariablesname"));
//                testvariablesValue.setVariablesvalue(ParseValue);
//                testvariablesValue.setMemo("test");
//                testVariablesValueSave(testvariablesValue);
//            }
//        }
//    }
//    //处理脚本条件
//    public void ScriptCondition(long ConditionID, RequestObject requestObject) throws Exception {
//        Long PlanID = Long.parseLong(requestObject.getTestplanid());
//        Long CaseID = Long.parseLong(requestObject.getCaseid());
//        ArrayList<HashMap<String, String>> conditionScriptList = GetScriptConditionByConditionID(ConditionID);
//        for (HashMap<String, String> conditionScript : conditionScriptList) {
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
//                logger.info("条件报告脚本子条件:-============：" + JavaSource);
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
//        }
//    }
//
//    private void SaveSubCondition(String SubconditionType, RequestObject requestObject, Long PlanID, Long ConditionID, HashMap<String, String> conditionScript, String Respone, String ConditionResultStatus, long CostTime) {
//        //更新条件结果表
//        TestconditionReport testconditionReport = new TestconditionReport();
//        testconditionReport.setTestplanid(PlanID);
//        testconditionReport.setPlanname(requestObject.getCasename());
//        testconditionReport.setBatchname(requestObject.getBatchname());
//        testconditionReport.setConditionid(new Long(ConditionID));
//        testconditionReport.setConditiontype("前置条件");
//        testconditionReport.setSubconditionid(Long.parseLong(conditionScript.get("id")));
//        testconditionReport.setSubconditionname(conditionScript.get("subconditionname"));
//        testconditionReport.setSubconditiontype(SubconditionType);
//        logger.info(SubconditionType + "条件报告保存子条件进行中状态-============：" + testconditionReport.getPlanname() + "|" + testconditionReport.getBatchname());
//
//        testconditionReport.setConditionresult(Respone.replace("'","''"));
//        testconditionReport.setConditionstatus(ConditionResultStatus);
//        testconditionReport.setRuntime(CostTime);
//        testconditionReport.setStatus("已完成");
//        SubConditionReportSave(testconditionReport);
//        logger.info(SubconditionType + "条件报告更新子条件结果-============：" + testconditionReport.getPlanname() + "|" + testconditionReport.getBatchname());
//
//    }
//
//    public void DBCondition(long ConditionID, RequestObject requestObject) throws Exception {
//        Long PlanID = Long.parseLong(requestObject.getTestplanid());
//        ArrayList<HashMap<String, String>> conditionDbListList = GetDBConditionByConditionID(ConditionID);
//        for (HashMap<String, String> conditionDb : conditionDbListList) {
//            long Start = 0;
//            long End = 0;
//            long CostTime = 0;
//            String Respone = "";
//            String ConditionResultStatus = "成功";
//            Long Assembleid = Long.parseLong(conditionDb.get("assembleid"));
//            try {
//                ArrayList<HashMap<String, String>> enviromentAssemblelist = getcaseData("select * from enviroment_assemble where id=" + Assembleid);
//                if (enviromentAssemblelist.size() == 0) {
//                    Respone = "未找到环境组件，请检查是否存在或已被删除";
//                    ConditionResultStatus = "失败";
//                    SaveSubCondition("数据库", requestObject, PlanID, ConditionID, conditionDb, Respone, ConditionResultStatus, CostTime);
//                    break;
//                }
//                String AssembleType = enviromentAssemblelist.get(0).get("assembletype");
//                Long Envid = Long.parseLong(conditionDb.get("enviromentid"));
//                String Sql = conditionDb.get("dbcontent");
//                logger.info(logplannameandcasename + "数据库子条件完整的sql ....." + Sql);
//                String ConnnectStr = enviromentAssemblelist.get(0).get("connectstr");
//                ArrayList<HashMap<String, String>> macdepunitlist = getcaseData("select * from macdepunit where envid=" + Envid + " and assembleid=" + Assembleid);
//                if (macdepunitlist.size() == 0) {
//                    Respone = "未找到环境组件部署，请检查是否存在或已被删除";
//                    ConditionResultStatus = "失败";
//                    SaveSubCondition("数据库", requestObject, PlanID, ConditionID, conditionDb, Respone, ConditionResultStatus, CostTime);
//                    break;
//                }
//
//                Long MachineID = Long.parseLong(macdepunitlist.get(0).get("machineid"));
//                ArrayList<HashMap<String, String>> machinelist = getcaseData("select * from machine where id=" + MachineID);
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
//                String username = ConnetcArray[0];
//                String pass = ConnetcArray[1];
//                String port = ConnetcArray[2];
//                String dbname = ConnetcArray[3];
//                String DBUrl = "";
//                if (AssembleType.equals("mysql")) {
//                    DBUrl = "jdbc:mysql://";
//                    // 根据访问方式来确定ip还是域名
//                    if (deployunitvisittype.equals("ip")) {
//                        String IP = machinelist.get(0).get("ip");
//                        DBUrl = DBUrl + IP + ":" + port + "/" + dbname + "?useUnicode=true&useSSL=false&allowMultiQueries=true&characterEncoding=utf-8&useLegacyDatetimeCode=false&serverTimezone=UTC";
//                    } else {
//                        String Domain = macdepunitlist.get(0).get("domain");
//                        DBUrl = DBUrl + Domain + "/" + dbname + "?useUnicode=true&useSSL=false&allowMultiQueries=true&characterEncoding=utf-8&useLegacyDatetimeCode=false&serverTimezone=UTC";
//                    }
//                }
//                if (AssembleType.equals("oracle")) {
//                    DBUrl = "jdbc:oracle:thin:@";
//                    // 根据访问方式来确定ip还是域名
//                    if (deployunitvisittype.equals("ip")) {
//                        String IP = machinelist.get(0).get("ip");
//                        DBUrl = DBUrl + IP + ":" + port + ":" + dbname ;
//                    } else {
//                        String Domain = macdepunitlist.get(0).get("domain");
//                        DBUrl = DBUrl + Domain + ":" + dbname ;
//                    }
//                }
//                Start = new Date().getTime();
//                DataSource ds = new SimpleDataSource(DBUrl, username, pass);
//
//                String[] SqlArr = Sql.split(";");
//                for (String ExecSql : SqlArr) {
//                    logger.info(logplannameandcasename + "数据库子条件执行sql ....." + ExecSql);
//                    if((!ExecSql.isEmpty())||(ExecSql.equals("")))
//                    {
//                        int nums = Db.use(ds).execute(ExecSql);
//                        Respone = Respone + " 成功执行Sql:" + Sql + " 影响条数：" + nums;
//                    }
//                }
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
//        }
//    }

    // 发送http请求
    public ResponeData request(RequestObject requestObject) throws Exception {
        ResponeData result = testHttp.doService(requestObject);
//        if (requestObject.getProtocal().equals("http") || requestObject.getProtocal().equals("https")) {
//            if (requestObject.getRequestmMthod().equals("get")) {
//                logger.info(logplannameandcasename + "get请求，request url is ....." + Httphelp.getrequesturl(requestObject.getResource(), requestObject.getApistyle(), requestObject.getParamers()));
//            }
//            logger.info(logplannameandcasename + "doService url is ....." + Httphelp.getrequesturl(requestObject.getResource(), requestObject.getApistyle(), requestObject.getParamers()));
//            result = Httphelp.doService(requestObject.getProtocal(), requestObject.getResource(), requestObject.getRequestmMthod(), requestObject.getApistyle(), requestObject.getParamers(), requestObject.getPostData(), requestObject.getRequestcontenttype(), requestObject.getHeader(), 30000, 30000);
//        }
        return result;
    }


    //断言
    public String FixAssert(TestAssert TestAssert, List<ApicasesAssert> apicasesAssertList, ResponeData responeData) throws Exception {
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
        }
        catch (Exception ex)
        {
            logger.info("发送邮件异常-============："+ex.getMessage());
        }
    }

    //初始化数据库连接
//    public void GetDBConnection(String mysqluel, String mysqlusername, String mysqlpass) {
//        MysqlConnectionUtils.initDbResource(mysqluel, mysqlusername, mysqlpass);
//    }

    //获取计划批次的数据统计
    public ArrayList<HashMap<String, String>> GetStatic(String planid,String Batchname) {
        ArrayList<HashMap<String, String>> list=testMysqlHelp.GetStatic(planid, Batchname);
//        try {
//            String sql = "select sum(totalcases) as tc,sum(totalpasscases) as tpc ,sum(totalfailcases) as tfc from apicases_reportstatics where testplanid="+planid +" and batchname='" + Batchname + "'";
//            logger.info(logplannameandcasename + "获取统计 result sql is...........: " + sql);
//            list = MysqlConnectionUtils.query(sql);
//        } catch (Exception e) {
//            logger.info(logplannameandcasename + "获取统计异常...........: " + e.getMessage());
//        }
        return list;
    }


    //获取账号数据
    public ArrayList<HashMap<String, String>> findWithUsername(String username) {
        ArrayList<HashMap<String, String>> list=testMysqlHelp.findWithUsername(username);

//        ArrayList<HashMap<String, String>> list = new ArrayList<>();
//        try {
//            String sql = "SELECT a.* FROM account a where a.name = '" + username + "'";
//            logger.info(logplannameandcasename + "获取账号 result sql is...........: " + sql);
//            list = MysqlConnectionUtils.query(sql);
//        } catch (Exception e) {
//            logger.info(logplannameandcasename + "获取账号异常...........: " + e.getMessage());
//        }
        return list;
    }


    //获取数据库用例相关数据
    public ArrayList<HashMap<String, String>> findDicNameValueWithCode(String DicCode) {
        ArrayList<HashMap<String, String>> list=testMysqlHelp.findDicNameValueWithCode(DicCode);

//        ArrayList<HashMap<String, String>> list = new ArrayList<>();
//        try {
//            String sql = "SELECT a.dicitemname,a.dicitmevalue FROM dictionary a where a.diccode = '" + DicCode + "'";
//            logger.info(logplannameandcasename + "获取字典值caseid result sql is...........: " + sql);
//            list = MysqlConnectionUtils.query(sql);
//        } catch (Exception e) {
//            logger.info(logplannameandcasename + "获取字典值异常...........: " + e.getMessage());
//        }
        return list;
    }

    //获取计划批次
    public ArrayList<HashMap<String, String>> GetplanBatchCreator(String planid,String BatchName) {
        ArrayList<HashMap<String, String>> list=testMysqlHelp.GetplanBatchCreator(planid,BatchName);
//        ArrayList<HashMap<String, String>> list = new ArrayList<>();
//        try {
//            String sql = "SELECT a.* FROM executeplanbatch a where a.executeplanid = " + planid + " and a.batchname='"+BatchName+"'";
//            logger.info(logplannameandcasename + "获取计划批次 result sql is...........: " + sql);
//            list = MysqlConnectionUtils.query(sql);
//        } catch (Exception e) {
//            logger.info(logplannameandcasename + "获取计划批次异常...........: " + e.getMessage());
//        }
        return list;
    }

    //获取数据库用例相关数据
    public ArrayList<HashMap<String, String>> getcaseData(String Sql) {
        ArrayList<HashMap<String, String>> list=testMysqlHelp.getcaseData(Sql);
//        logger.info(logplannameandcasename + "Sql is:  " + Sql);
//        ArrayList<HashMap<String, String>> list = new ArrayList<>();
//        try {
//            list = MysqlConnectionUtils.query(Sql);
//            for (HashMap<String, String> maplog:list) {
//                for (String Key: maplog.keySet()) {
//                    logger.info("获取数据的字段名为:  " + Key + "  字段值为：" + maplog.get(Key));
//                }
//            }
//        } catch (Exception e) {
//            logger.info(logplannameandcasename + "Sql is:  " + Sql + "  数据库异常：" + e.getMessage());
//        }
//        logger.info(logplannameandcasename + "list size is:  " + list.size());
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
//        String ValueType = "";
//        try {
//            String sql = "select valuetype from testvariables where  testvariablesname='" + VariablesName + "'";
//            logger.info(logplannameandcasename + "获取变量值类型 result sql is...........: " + sql);
//            ArrayList<HashMap<String, String>> result = MysqlConnectionUtils.query(sql);
//            if (result.size() > 0) {
//                ValueType = result.get(0).get("valuetype");
//            }
//        } catch (Exception e) {
//            logger.info(logplannameandcasename + "获取变量值类型异常...........: " + e.getMessage());
//        }
        return ValueType;
    }

    //根据变量名获取caseid
    private String GetCaseIdByVariablesName(String VariablesName) {
        String CaseID=testMysqlHelp.GetCaseIdByVariablesName(VariablesName);
//        String CaseID = "";
//        try {
//            String sql = "select caseid from apicases_variables where  variablesname='" + VariablesName + "'";
//            logger.info(logplannameandcasename + "根据变量名获取caseid result sql is...........: " + sql);
//            ArrayList<HashMap<String, String>> result = MysqlConnectionUtils.query(sql);
//            if (result.size() > 0) {
//                CaseID = result.get(0).get("caseid");
//            }
//        } catch (Exception e) {
//            logger.info(logplannameandcasename + "根据变量名获取caseid异常...........: " + e.getMessage());
//        }
        return CaseID;
    }

    //获取变量值
    private String GetVariablesValues(String PlanID, String TestCaseId, String BatchName, String VariablesName) {
        String VariablesResult=testMysqlHelp.GetVariablesValues(PlanID,TestCaseId,BatchName,VariablesName);
//        String VariablesResult = "";
//        try {
//            String sql = "select variablesvalue from testvariables_value where planid=" + PlanID + " and caseid=" + TestCaseId + " and batchname= '" + BatchName + "'" + " and variablesname='" + VariablesName + "'";
//            logger.info(logplannameandcasename + "查询计划下的批次中条件接口获取的中间变量 result sql is...........: " + sql);
//            ArrayList<HashMap<String, String>> result = MysqlConnectionUtils.query(sql);
//            if (result.size() > 0) {
//                VariablesResult = result.get(0).get("variablesvalue");
//            }
//        } catch (Exception e) {
//            logger.info(logplannameandcasename + "查询计划下的批次中条件接口获取的中间变量异常...........: " + e.getMessage());
//        }
        return VariablesResult;
    }


    //获取条件
    private ArrayList<HashMap<String, String>> GetConditionByPlanIDAndConditionType(Long Caseid, String ConditionType, String ObjectType) {
        ArrayList<HashMap<String, String>> result = testMysqlHelp.GetConditionByPlanIDAndConditionType(Caseid,ConditionType,ObjectType);
//        ArrayList<HashMap<String, String>> result = new ArrayList<>();
//        try {
//            String sql = "select * from testcondition where objectid=" + Caseid + " and conditiontype='" + ConditionType + "' and objecttype='" + ObjectType + "'";
//            logger.info(logplannameandcasename + "获取条件 result sql is...........: " + sql);
//            result = MysqlConnectionUtils.query(sql);
//        } catch (Exception e) {
//            logger.info(logplannameandcasename + "获取条件异常...........: " + e.getMessage());
//        }
        return result;
    }

    //获取条件顺序
    private ArrayList<HashMap<String, String>> GetConditionOrderByID(Long ConditionID) {
        ArrayList<HashMap<String, String>> result = testMysqlHelp.GetConditionOrderByID(ConditionID);

//        ArrayList<HashMap<String, String>> result = new ArrayList<>();
//        try {
//            String sql = "select * from condition_order where conditionid=" + ConditionID +" order by conditionorder  asc" ;
//            logger.info(logplannameandcasename + "获取条件顺序 result sql is...........: " + sql);
//            result = MysqlConnectionUtils.query(sql);
//        } catch (Exception e) {
//            logger.info(logplannameandcasename + "获取条件顺序异常...........: " + e.getMessage());
//        }
        return result;
    }

    //获取接口条件
    private ArrayList<HashMap<String, String>> GetApiConditionByConditionID(Long ConditionID) {
        ArrayList<HashMap<String, String>> result = testMysqlHelp.GetApiConditionByConditionID(ConditionID);

//        ArrayList<HashMap<String, String>> result = new ArrayList<>();
//        try {
//            String sql = "select * from condition_api where conditionid=" + ConditionID;
//            logger.info(logplannameandcasename + "获取接口条件 result sql is...........: " + sql);
//            result = MysqlConnectionUtils.query(sql);
//        } catch (Exception e) {
//            logger.info(logplannameandcasename + "获取接口条件异常...........: " + e.getMessage());
//        }
        return result;
    }

    //获取脚本条件
    private ArrayList<HashMap<String, String>> GetScriptConditionByConditionID(Long ConditionID) {
        ArrayList<HashMap<String, String>> result = testMysqlHelp.GetScriptConditionByConditionID(ConditionID);

//        ArrayList<HashMap<String, String>> result = new ArrayList<>();
//        try {
//            String sql = "select * from condition_script where conditionid=" + ConditionID;
//            logger.info(logplannameandcasename + "获取脚本条件 result sql is...........: " + sql);
//            result = MysqlConnectionUtils.query(sql);
//        } catch (Exception e) {
//            logger.info(logplannameandcasename + "获取脚本条件异常...........: " + e.getMessage());
//        }
        return result;
    }

    //获取数据库条件
    private ArrayList<HashMap<String, String>> GetDBConditionByConditionID(Long ConditionID) {
        ArrayList<HashMap<String, String>> result = testMysqlHelp.GetDBConditionByConditionID(ConditionID);

//        ArrayList<HashMap<String, String>> result = new ArrayList<>();
//        try {
//            String sql = "select * from condition_db where conditionid=" + ConditionID;
//            logger.info(logplannameandcasename + "获取数据库条件 result sql is...........: " + sql);
//            result = MysqlConnectionUtils.query(sql);
//        } catch (Exception e) {
//            logger.info(logplannameandcasename + "获取数据库条件异常...........: " + e.getMessage());
//        }
        return result;
    }


    //保存条件结果
    public void SubConditionReportSave(TestconditionReport testconditionReport) {
        testMysqlHelp.SubConditionReportSave(testconditionReport);
//        Date d = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String dateNowStr = sdf.format(d);
//        String sql = "insert testcondition_report (conditionid,conditiontype,subconditionid,conditionresult,conditionstatus,runtime,create_time,lastmodify_time,creator,batchname,planname,testplanid,subconditiontype,status,subconditionname)" +
//                " values(" + testconditionReport.getConditionid() + ", '" + testconditionReport.getConditiontype() + "', " + testconditionReport.getSubconditionid() + ", '" + testconditionReport.getConditionresult() + "', '" + testconditionReport.getConditionstatus() + "', " + testconditionReport.getRuntime() + ", '" + dateNowStr + "', '" + dateNowStr + "','admin'" + ", '" + testconditionReport.getBatchname().replace("'","''") + "',  '" + testconditionReport.getPlanname().replace("'","''") + "'," + testconditionReport.getTestplanid() + ", '" + testconditionReport.getSubconditiontype() + "', '" + testconditionReport.getStatus() + "', '" + testconditionReport.getSubconditionname().replace("'","''") + "')";
//        logger.info(logplannameandcasename + "接口条件报告结果 result sql is...........: " + sql);
//        logger.info(logplannameandcasename + "接口条件报告结果 result sql is...........: " + MysqlConnectionUtils.update(sql));
    }

    //保存变量结果
    public void testVariablesValueSave(TestvariablesValue testvariablesValue) {
        testMysqlHelp.testVariablesValueSave(testvariablesValue);
//        Date d = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String dateNowStr = sdf.format(d);
//        String sql = "insert testvariables_value (planid,planname,caseid,casename,variablesid,variablesname,variablesvalue,memo,create_time,lastmodify_time,creator,batchname)" +
//                " values(" + testvariablesValue.getPlanid() + ", '" + testvariablesValue.getPlanname().replace("'","''") + "', " + testvariablesValue.getCaseid() + ", '" + testvariablesValue.getCasename().replace("'","''") + "', " + testvariablesValue.getVariablesid() + ", '" + testvariablesValue.getVariablesname().replace("'","''") + "', '" + testvariablesValue.getMemo().replace("'","''") + ", '" + dateNowStr + "', '" + dateNowStr + "','admin')" + ", '" + testvariablesValue.getBatchname().replace("'","''") + "'";
//        logger.info(logplannameandcasename + "保存变量结果 result sql is...........: " + sql);
//        logger.info(logplannameandcasename + "保存变量结果 result sql is...........: " + MysqlConnectionUtils.update(sql));
    }

    //查询用例变量
    public ArrayList<HashMap<String, String>> GetApiCaseVaribales(Long CaseID) {
        ArrayList<HashMap<String, String>> result = testMysqlHelp.GetApiCaseVaribales(CaseID);
//        ArrayList<HashMap<String, String>> result = new ArrayList<>();
//        try {
//            String sql = "select *  from apicases_variables where caseid=" + CaseID;
//            logger.info(logplannameandcasename + "查询用例变量 result sql is...........: " + sql);
//            result = MysqlConnectionUtils.query(sql);
//        } catch (Exception e) {
//            logger.info(logplannameandcasename + "查询用例变量异常...........: " + e.getMessage());
//        }
        return result;
    }

    //查询变量
    public ArrayList<HashMap<String, String>> GetVaribales(String VaribaleID) {
        ArrayList<HashMap<String, String>> result = testMysqlHelp.GetVaribales(VaribaleID);
//        ArrayList<HashMap<String, String>> result = new ArrayList<>();
//        try {
//            String sql = "select *  from testvariables where id=" + VaribaleID;
//            logger.info(logplannameandcasename + "查询变量 result sql is...........: " + sql);
//            result = MysqlConnectionUtils.query(sql);
//        } catch (Exception e) {
//            logger.info(logplannameandcasename + "查询变量异常...........: " + e.getMessage());
//        }
        return result;
    }

    // 获取用例Header，params，Body，Dubbo数据
    public HashMap<String, String> fixhttprequestdatas(String MapType, ArrayList<HashMap<String, String>> casedatalist) {
        HashMap<String, String> DataMap = testMysqlHelp.fixhttprequestdatas(MapType,casedatalist);

//        HashMap<String, String> DataMap = new HashMap<>();
//        for (HashMap<String, String> data : casedatalist) {
//            String propertytype = data.get("propertytype");
//            if (propertytype.equals(MapType)) {
//                DataMap.put(data.get("apiparam").trim(), data.get("apiparamvalue").trim());
//            }
//        }
        return DataMap;
    }

    public HashMap<String, String> getparamsdatabytype(String MapType, ArrayList<HashMap<String, String>> casedatalist) {
        HashMap<String, String> DataMap = testMysqlHelp.getparamsdatabytype(MapType,casedatalist);

//        HashMap<String, String> DataMap = new HashMap<>();
//        for (HashMap<String, String> data : casedatalist) {
//            String propertytype = data.get("paramstype");
//            if (propertytype.equals(MapType)) {
//                DataMap.put(data.get("keyname").trim(), data.get("keyvalue").trim());
//            }
//        }
        return DataMap;
    }

    // 记录用例测试结果
    public void savetestcaseresult(boolean status, long time, String respone, String assertvalue, String errorinfo, RequestObject requestObject, JavaSamplerContext context) {
        testMysqlHelp.savetestcaseresult(status,time,respone,assertvalue,errorinfo,requestObject,context);
//
//        try {
//            String resulttable = "";
//            String casetype = "";
//            String testplanid = "";
//            String caseid = "";
//            String slaverid = "";
//            String expect = "";
//            String batchname = "";
//            String header = "";
//            String params = "";
//            String Url = "";
//            String Method = "";
//            if (requestObject == null) {
//                casetype = context.getParameter("casetype");
//                testplanid = context.getParameter("testplanid");
//                caseid = context.getParameter("caseid");
//                slaverid = context.getParameter("slaverid");
//                expect = context.getParameter("expect");
//                batchname = context.getParameter("batchname").replace("'","''");
//            } else {
//                casetype = requestObject.getCasetype();// context.getParameter("casetype");
//                testplanid = requestObject.getTestplanid();// context.getParameter("testplanid");
//                caseid = requestObject.getCaseid();// context.getParameter("caseid");
//                slaverid = requestObject.getSlaverid();// context.getParameter("slaverid");
//                expect = requestObject.getExpect();// context.getParameter("expect");
//                batchname = requestObject.getBatchname().replace("'","''");// context.getParameter("batchname");
//                Url = requestObject.getResource().replace("'","''");
//                Method = requestObject.getRequestmMthod();
//                Map<String, Object> headermap = requestObject.getHeader().getParams();
//                for (String key : headermap.keySet()) {
//                    header = header + key + " ：" + headermap.get(key);
//                }
//                header=header.replace("'","''");
//                params = requestObject.getPostData();
//                if (params == null) {
//                    params = "";
//                }
//                params=params.replace("'","''");
//            }
//
//            if (casetype.equals(new String("功能"))) {
//                resulttable = "apicases_report";
//            }
//            if (casetype.equals(new String("性能"))) {
//                resulttable = "apicases_report_performance";
//            }
//            Date d = new Date();
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String dateNowStr = sdf.format(d);
//            String sql = "";
//            if (status) {
//                sql = "insert " + resulttable + " (caseid,testplanid,batchname,slaverid,status,respone,assertvalue,runtime,expect,errorinfo,create_time,lastmodify_time,creator,requestheader,requestdatas,url,requestmethod)" +
//                        " values(" + caseid + "," + testplanid + ", '" + batchname + "', " + slaverid + ", '成功" + "' , '" + respone.replace("'","''") + "' ,'" + assertvalue.replace("'","''") + "', " + time + ",'" + expect.replace("'","''") + "','" + errorinfo + "','" + dateNowStr + "', '" + dateNowStr + "','admin', '" + header + "', '" + params + "', '" + Url + "', '" + Method + "')";
//            } else {
//                sql = "insert  " + resulttable + " (caseid,testplanid,batchname,slaverid,status,respone,assertvalue,runtime,expect,errorinfo,create_time,lastmodify_time,creator,requestheader,requestdatas,url,requestmethod)" +
//                        " values(" + caseid + "," + testplanid + ", '" + batchname + "', " + slaverid + ", '失败" + "' , '" + respone.replace("'","''") + "','" + assertvalue.replace("'","''") + "'," + time + ",'" + expect.replace("'","''") + "','" + errorinfo + "','" + dateNowStr + "','" + dateNowStr + "','admin', '" + header + "', '" + params + "', '" + Url + "', '" + Method + "')";
//            }
//            logger.info(logplannameandcasename + "测试结果 result sql is...........: " + sql);
//            System.out.println("case result sql is: " + sql);
//            logger.info(logplannameandcasename + "记录用例测试结果 result sql is...........: " + MysqlConnectionUtils.update(sql));
//        } catch (Exception ex) {
//            logger.info(logplannameandcasename + "记录用例测试结果异常...........: " + ex.getMessage());
//        }
    }

    // 记录用例测试结果
    public void SaveReportStatics(ApicasesReportstatics apicasesReportstatics) {
        testMysqlHelp.SaveReportStatics(apicasesReportstatics);
//        Date d = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String dateNowStr = sdf.format(d);
//        String sql = "insert apicases_reportstatics (testplanid,deployunitid,batchname,slaverid,totalcases,totalpasscases,totalfailcases,runtime,create_time,lastmodify_time,creator)" +
//                " values(" + apicasesReportstatics.getTestplanid() + "," + apicasesReportstatics.getDeployunitid() + ", '" + apicasesReportstatics.getBatchname().replace("'","''") + "', " + apicasesReportstatics.getSlaverid() + ", " + apicasesReportstatics.getTotalcases() + ", " + apicasesReportstatics.getTotalpasscases() + ", " + apicasesReportstatics.getTotalfailcases() + ", " + apicasesReportstatics.getRuntime() + ", '" + dateNowStr + "', '" + dateNowStr + "','admin')";
//        logger.info(logplannameandcasename + "功能测试统计结果 result sql is...........: " + sql);
//        logger.info(logplannameandcasename + "功能测试统计结果 result sql is...........: " + MysqlConnectionUtils.update(sql));
    }


    //查询此计划下的批次调度是否已经全部完成，如果完成，刷新计划批次状态为finish
    public long PlanBatchAllDipatchFinish(String Testplanid, String batchname) {
        long DispatchNotFinishNums = testMysqlHelp.PlanBatchAllDipatchFinish(Testplanid,batchname);
//        long DispatchNotFinishNums = 0;
//        try {
//            String sql = "select count(*) as nums from dispatch where execplanid=" + Testplanid + " and batchname= '" + batchname + "' and status in('待分配','已分配')";
//            logger.info(logplannameandcasename + "查询计划下的批次调度是否已经全部完成 result sql is...........: " + sql);
//            ArrayList<HashMap<String, String>> result = MysqlConnectionUtils.query(sql);
//            DispatchNotFinishNums = Long.parseLong(getcaseValue("nums", result));
//        } catch (Exception e) {
//            logger.info(logplannameandcasename + "查询计划下的批次调度是否已经全部完成异常...........: " + e.getMessage());
//        }
        return  DispatchNotFinishNums;
    }

    // 更新计划批次状态
    public void UpdateReportStatics(String Planid, String BatchName, String status) {
        testMysqlHelp.UpdateReportStatics(Planid,BatchName,status);
//
//        String UpdateSql = "update  executeplanbatch set status='" + status + "' where executeplanid=" + Planid + " and batchname= '" + BatchName + "'";
//        logger.info(logplannameandcasename + "更新计划批次状态结果完成  sql is...........: " + UpdateSql);
//        logger.info(logplannameandcasename + "更新计划批次状态结果完成 result sql is...........: " + MysqlConnectionUtils.update(UpdateSql));
    }

    // 更新Slaver状态
    public void UpdateSlaverStatus(String Slaverid, String status) {
        testMysqlHelp.UpdateSlaverStatus(Slaverid,status);

//        String UpdateSql = "update  slaver set status='" + status + "' where id=" + Slaverid;
//        logger.info(logplannameandcasename + "更新Slaver状态结果完成  sql is...........: " + UpdateSql);
//        logger.info(logplannameandcasename + "更新Slaver状态结果完成 result sql is...........: " + MysqlConnectionUtils.update(UpdateSql));
    }

    // 更新用例调度结果
    public void updatedispatchcasestatus(String testplanid, String caseid, String slaverid, String batchid) {
        testMysqlHelp.updatedispatchcasestatus(testplanid,caseid,slaverid,batchid);

//        try {
//            Date d = new Date();
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String dateNowStr = sdf.format(d);
//            String sql = "";
//            sql = "update dispatch set status='已完成',lastmodify_time='" + dateNowStr + "' where slaverid=" + slaverid + " and execplanid=" + testplanid + " and batchid=" + batchid + " and testcaseid=" + caseid;
//            logger.info(logplannameandcasename + "更新调度用例状态 result sql is...........: " + sql);
//            System.out.println("case result sql is: " + sql);
//            logger.info(logplannameandcasename + "更新用例调度结果 is...........: " + MysqlConnectionUtils.update(sql));
//        } catch (Exception ex) {
//            logger.info(logplannameandcasename + "更新用例调度结果异常...........: " + ex.getMessage());
//        }
    }

    //生成性能报告目录
    public void genealperformacestaticsreport(String testclass, String batchname, String testplanid, String batchid, String slaverid, String caseid, String casereportfolder, double costtime) throws Exception {
        testMysqlHelp.genealperformacestaticsreport(testclass,batchname,testplanid,batchid,slaverid,caseid,casereportfolder,costtime);

//        Date d = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String dateNowStr = sdf.format(d);
//        String sql = "";
//        sql = "insert performancereportsource (planid,batchid,batchname,slaverid,caseid,testclass,runtime,source,status,create_time,lastmodify_time)" +
//                " values(" + testplanid + "," + batchid + ", '" + batchname.replace("'","''") + "', " + slaverid + ", " + caseid + " , '" + testclass + "' ," + costtime + " , '" + casereportfolder + "', '待解析', '" + dateNowStr + "', '" + dateNowStr + "')";
//        logger.info(logplannameandcasename + "保存性能结果 sql is...........: " + sql);
//        logger.info(logplannameandcasename + "保存性能结果 is...........: " + MysqlConnectionUtils.update(sql));
    }
}
