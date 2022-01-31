package com.api.autotest.core;

import com.alibaba.fastjson.JSON;
import com.api.autotest.common.utils.HttpHeader;
import com.api.autotest.common.utils.HttpParamers;
import com.api.autotest.dto.RequestObject;
import org.apache.log.Logger;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.api.autotest.core.TestCaseData.logplannameandcasename;


public class TestHttpRequestData {

    private Logger logger = null;
    TestMysqlHelp testMysqlHelp = null;

    public TestHttpRequestData(Logger log, TestMysqlHelp mysqlHelp) {
        testMysqlHelp = mysqlHelp;
        logger = log;
    }

    //功能用例获取Http请求数据
    public RequestObject GetFuntionHttpRequestData(RequestObject requestObject) {
        RequestObject Result = requestObject;
        String TestCaseId = requestObject.getCaseid();
        String PlanId = requestObject.getTestplanid();
        String BatchName = requestObject.getBatchname();
        String requestcontenttype = requestObject.getRequestcontenttype();
        ArrayList<HashMap<String, String>> caselist = testMysqlHelp.getcaseData("select * from apicases where id=" + TestCaseId);
        String APIId = testMysqlHelp.getcaseValue("apiid", caselist);

        ArrayList<HashMap<String, String>> casedatalist = testMysqlHelp.getcaseData("select * from api_casedata where caseid=" + TestCaseId);
        ArrayList<HashMap<String, String>> caseptlist = testMysqlHelp.getcaseData("select DISTINCT propertytype  from api_params where apiid=" + APIId);
        ArrayList<HashMap<String, String>> planparamslist = testMysqlHelp.getcaseData("select * from executeplan_params where executeplanid=" + PlanId);

        List<String> PropertyList = GetCaseDataPropertyType(caseptlist);
        HttpHeader header = new HttpHeader();
        header = AddHeaderByRequestContentType(header, requestcontenttype);
        //Params参数：Url拼接
        HttpParamers paramers = new HttpParamers();
        //Body参数
        HttpParamers Bodyparamers = new HttpParamers();
        String PostData = "";
        for (String property : PropertyList) {
            if (property.equalsIgnoreCase("Header")) {
                //值支持变量
                header = GetHttpHeader(casedatalist, header, PlanId, BatchName);
                for (String Key :header.getParams().keySet()) {
                    logger.info(logplannameandcasename + "TestHttpRequestData Header Key :  " + Key+" Value: "+header.getParams().get(Key));
                }
            }
            if (property.equalsIgnoreCase("Params")) {
                //值支持变量
                paramers = GetHttpParams(casedatalist, paramers, PlanId, BatchName, "Params");
                for (String Key :paramers.getParams().keySet()) {
                    logger.info(logplannameandcasename + "TestHttpRequestData Params Key :  " + Key+" Value: "+paramers.getParams().get(Key));
                }
            }
            if (property.equalsIgnoreCase("Body")) {
                // 设置Body
                if (requestcontenttype.equalsIgnoreCase("Form表单")) {
                    //值支持变量
                    Bodyparamers = GetHttpParams(casedatalist, Bodyparamers, PlanId, BatchName, "Body");
                    if (Bodyparamers.getParams().size() > 0) {
                        try {
                            PostData = Bodyparamers.getQueryString();
                            logger.info(logplannameandcasename + "TestHttpRequestData Bodyparamers  PostData:  " + PostData);
                        } catch (UnsupportedEncodingException e) {
                            logger.info(logplannameandcasename + "TestHttpRequestData Bodyparamers表单编码异常 :  " + e.getMessage());
                        }
                    }
                } else {
                    HashMap<String, String> bodymap = testMysqlHelp.fixhttprequestdatas("Body", casedatalist);
                    for (String Key : bodymap.keySet()) {
                        PostData = bodymap.get(Key);
                        logger.info(logplannameandcasename + "TestHttpRequestData Body  PostData:  " + PostData);
                    }
                }
            }
        }
        //处理全局参数
        header = GetHeaderFromTestPlanParam(header, planparamslist, PlanId, BatchName);
        for (String Key :header.getParams().keySet()) {
            logger.info(logplannameandcasename + "TestHttpRequestData 全局参数Header Key :  " + Key+" Value: "+header.getParams().get(Key));
        }

        PostData = GetBodyFromTestPlanParam(PostData, planparamslist);
        logger.info(logplannameandcasename + "TestHttpRequestData 全局参数Body :  " + PostData);
        //处理全局参数

        Result.setHeader(header);
        Result.setParamers(paramers);
        Result.setPostData(PostData);
        return Result;
    }

    //性能用例获取Http请求数据
    public RequestObject GetPerformanceHttpRequestData(RequestObject newob) throws Exception {
        String requestcontenttype = newob.getRequestcontenttype();
        String testplanid = newob.getTestplanid();
        String batchname = newob.getBatchname();
        String headjson = newob.getHeadjson();
        String paramsjson = newob.getParamjson();
        String bodyjson = newob.getBodyjson();

        HttpHeader header = new HttpHeader();
        if (headjson.equals("NoCaseData")) {
            //表示api设置了header参数，但是用例无数据
            throw new Exception("API的Header参数未设计测试用例数据，请完成用例数据后再运行");
        } else {
            if (headjson.equals("NN")) {
                newob.setHeader(header);
            } else {
                Map headermaps = (Map) JSON.parse(headjson);
                for (Object map : headermaps.entrySet()) {
                    String Value = ((Map.Entry) map).getValue().toString();
                    Object ObjectValue = GetVariablesObjectValues(Value, testplanid, batchname);
                    header.addParam(((Map.Entry) map).getKey().toString(), ObjectValue);
                }
                newob.setHeader(header);
            }
        }
        HttpParamers params = new HttpParamers();
        if (paramsjson.equals("NoCaseData")) {
            throw new Exception("API的Params参数未设计测试用例数据，请完成用例数据后再运行");
        } else {
            String PostData = "";
            if (paramsjson.equals("NN")) {
                newob.setPostData(PostData);
            } else {
                Map paramsmaps = (Map) JSON.parse(paramsjson);
                for (Object map : paramsmaps.entrySet()) {
                    String Value = ((Map.Entry) map).getValue().toString();
                    Object ObjectValue = GetVariablesObjectValues(Value, testplanid, batchname);
                    params.addParam(((Map.Entry) map).getKey().toString(), ObjectValue);
                }
                if (params.getParams().size() > 0) {
                    PostData = GetParasPostData(requestcontenttype, params);
                    newob.setPostData(PostData);
                }
            }
            newob.setParamers(params);
        }
        if (bodyjson.equals("NoCaseData")) {
            throw new Exception("API的Body参数未设计测试用例数据，请完成用例数据后再运行");
        } else {
            String PostData = "";
            if (paramsjson.equals("NN")) {
                newob.setPostData(PostData);
            } else {
                newob.setPostData(bodyjson);
            }
        }
        return newob;
    }


    //获取用例值的参数类型列表
    private List<String> GetCaseDataPropertyType(ArrayList<HashMap<String, String>> casedataptlist) {
        List<String> PropertyType = new ArrayList<>();
        for (HashMap<String, String> hs : casedataptlist) {
            for (String property : hs.keySet()) {
                PropertyType.add(hs.get(property));
            }
        }
        return PropertyType;
    }

    // 设置header
    private HttpHeader GetHttpHeader(ArrayList<HashMap<String, String>> casedatalist, HttpHeader header, String PlanId, String BatchName) {
        HashMap<String, String> headmap = testMysqlHelp.fixhttprequestdatas("Header", casedatalist);
        for (String key : headmap.keySet()) {
            String Value = headmap.get(key);
            Object ObjectValue = GetVariablesObjectValues(Value, PlanId, BatchName);
            header.addParam(key, ObjectValue);
            logger.info(logplannameandcasename + "TestHttpRequestData Header中添加Key is :  " + key + "   Value  is:   " + ObjectValue);
        }
        return header;
    }

    // 设置参数params
    private HttpParamers GetHttpParams(ArrayList<HashMap<String, String>> casedatalist, HttpParamers paramers, String PlanId, String BatchName, String Property) {
        //HashMap<String, String> paramsmap = testMysqlHelp.fixhttprequestdatas(Property, casedatalist);
        for (HashMap<String, String> data : casedatalist) {
            String propertytype = data.get("propertytype");
            if (propertytype.equals(Property)) {
                String Key=data.get("apiparam").trim();
                String Value=data.get("apiparamvalue").trim();
                String DataType=data.get("paramstype").trim();
                Object ObjectValue;
                if (Value.trim().contains("$")) {
                    ObjectValue = GetVariablesObjectValues(Value, PlanId, BatchName);
                }
                else
                {
                    ObjectValue=GetDataByType(Value,DataType);
                }
                paramers.addParam(Key, ObjectValue);
                logger.info(logplannameandcasename + "TestHttpRequestData -"+Property+ "-中添加Key is :  " + Key + "   Value  is:   " + ObjectValue+" 类型："+DataType);
            }
        }
//        for (String key : paramsmap.keySet()) {
//            String Value = paramsmap.get(key);
//            Object ObjectValue = new Object();
//            if (Value.trim().contains("$")) {
//                ObjectValue=GetDataByType(Value,ValueType);
//            }
//            else
//            {
//                ObjectValue = GetVariablesObjectValues(Value, PlanId, BatchName);
//            }
//            paramers.addParam(key, ObjectValue);
//            logger.info(logplannameandcasename + "TestHttpRequestData Params中添加Key is :  " + key + "   Value  is:   " + ObjectValue);
//        }
        return paramers;
    }

    private String GetParasPostData(String RequestContentType, HttpParamers paramers) throws UnsupportedEncodingException {
        String Result = "";
        if (RequestContentType.equalsIgnoreCase("json")) {
            paramers.setJsonParamer();
            Result = paramers.getJsonParamer();
        }
        if (RequestContentType.equalsIgnoreCase("Form表单")) {
            Result = paramers.getQueryString();
        }
        if (RequestContentType.equalsIgnoreCase("xml")) {

        } else {

        }
        return Result;
    }

    //获取参数值的具体内容，支持$变量
    private Object GetVariablesObjectValues(String Value, String PlanId, String BatchName) {
        Object Result = "";
        if (Value.trim().contains("$")) {
            if (Value.trim().length() == 1) {
                Result = Value;
            } else {
                Value = Value.substring(1);
                String Caseid = testMysqlHelp.GetCaseIdByVariablesName(Value);
                String ValueType = testMysqlHelp.GetVariablesDataType(Value);
                //根据用例参数值是否以$开头，如果是则认为是变量通过变量表取到变量值
                String VariablesNameValue = testMysqlHelp.GetVariablesValues(PlanId, Caseid, BatchName, Value);
                Result=GetDataByType(VariablesNameValue,ValueType);
//                if (ValueType.equals("Number")) {
//                    try {
//                        Result = Long.parseLong(VariablesNameValue);
//                    } catch (Exception ex) {
//                        Result = "变量值：" + VariablesNameValue + " 不是数字类型，请检查！";
//                    }
//                }
//                if (ValueType.equals("String")) {
//                    Result = VariablesNameValue;
//                }
//                if (ValueType.equals("Array")) {
//                    String[] Array = VariablesNameValue.split(",");
//                    Result = Array;
//                }
//                if (ValueType.equals("Bool")) {
//                    try {
//                        Result = Boolean.parseBoolean(VariablesNameValue);
//                    } catch (Exception ex) {
//                        Result = "变量值：" + VariablesNameValue + " 不是布尔类型，请检查！";
//                    }
//                }
            }
        } else {
            Result = Value;
        }
        return Result;
    }

    //获取全局Header
    private HttpHeader GetHeaderFromTestPlanParam(HttpHeader header, ArrayList<HashMap<String, String>> planparamslist, String PlanId, String BatchName) {
        HashMap<String, String> headmapfromparam = testMysqlHelp.getparamsdatabytype("Header", planparamslist);
        for (String key : headmapfromparam.keySet()) {
            String Value = headmapfromparam.get(key);
            Object ObjectValue = GetVariablesObjectValues(Value, PlanId, BatchName);
            //如果有相同的参数，则以全局参数的覆盖之
            if (header.getParams().containsKey(key)) {
                header.getParams().put(key, ObjectValue);
                logger.info(logplannameandcasename + "TestHttpRequestData 全局参数Header中添加Key is :  " + key + "   Value  is:   " + ObjectValue);
            }
        }
        return header;
    }

    //全局Body参数
    private String GetBodyFromTestPlanParam(String PostData, ArrayList<HashMap<String, String>> planparamslist) {
        HashMap<String, String> bodymapfromparam = testMysqlHelp.getparamsdatabytype("Body", planparamslist);
        for (String key : bodymapfromparam.keySet()) {
            String Value = bodymapfromparam.get(key);
            PostData = Value;
            logger.info(logplannameandcasename + "TestHttpRequestData 全局参数BodyPostData is :  " + PostData);
        }
        return PostData;
    }

    //根据请求数据类型增加header
    private HttpHeader AddHeaderByRequestContentType(HttpHeader httpHeader, String RequestContentType) {
        if (RequestContentType.equalsIgnoreCase("json")) {
            httpHeader.addParam("Content-Type", "application/json;charset=utf-8");
        }
        if (RequestContentType.equalsIgnoreCase("xml")) {
            httpHeader.addParam("Content-Type", "application/xml;charset=utf-8");
        }
        return httpHeader;
    }

    //根据数据类型转换
    private Object GetDataByType(String Data,String ValueType)
    {
        Object Result=new RequestObject();
        if (ValueType.equalsIgnoreCase("Number")) {
            try {
                Result = Long.parseLong(Data);
            } catch (Exception ex) {
                Result = "变量值：" + Data + " 不是数字类型，请检查！";
            }
        }
        if (ValueType.equalsIgnoreCase("Json")) {
            try {
                Result = JSON.parse(Data);
            } catch (Exception ex) {
                Result = "变量值：" + Data + " 不是数字类型，请检查！";
            }
        }
        if (ValueType.equalsIgnoreCase("String")||ValueType.isEmpty()) {
            Result = Data;
        }
        if (ValueType.equalsIgnoreCase("Array")) {
            String[] Array = Data.split(",");
            Result = Array;
        }
        if (ValueType.equalsIgnoreCase("Bool")) {
            try {
                Result = Boolean.parseBoolean(Data);
            } catch (Exception ex) {
                Result = "变量值：" + Data + " 不是布尔类型，请检查！";
            }
        }
        return Result;
    }
}
