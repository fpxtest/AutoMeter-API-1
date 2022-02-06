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
        try
        {
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
            //处理全局Header参数
            header = GetHeaderFromTestPlanParam(header, planparamslist, PlanId, BatchName);
            for (String Key :header.getParams().keySet()) {
                logger.info(logplannameandcasename + "TestHttpRequestData 全局参数Header Key :  " + Key+" Value: "+header.getParams().get(Key));
            }

//        PostData = GetBodyFromTestPlanParam(PostData, planparamslist);
//        logger.info(logplannameandcasename + "TestHttpRequestData 全局参数Body :  " + PostData);
            //处理全局参数
            Result.setHeader(header);
            Result.setParamers(paramers);
            Result.setPostData(PostData);
        }
        catch (Exception ex)
        {
            logger.info(logplannameandcasename + "功能获取GetFuntionHttpRequestData异常 :  " + ex.getMessage());
        }
        return Result;
    }

    //性能用例获取Http请求数据
    public RequestObject GetPerformanceHttpRequestData(RequestObject newob)  {
        try
        {
            String requestcontenttype = newob.getRequestcontenttype();
            String headjson = newob.getHeadjson();
            String paramsjson = newob.getParamjson();
            String bodyjson = newob.getBodyjson();

            logger.info(logplannameandcasename + "TestHttpRequestData headjson： " +headjson);

            //Header
            HttpHeader header = new HttpHeader();
            header = AddHeaderByRequestContentType(header, requestcontenttype);
            if(!headjson.isEmpty())
            {
                logger.info(logplannameandcasename + "TestHttpRequestData 开始 JSON.parse(headjson) " );
                Map headermaps = (Map) JSON.parse(headjson);
                logger.info(logplannameandcasename + "TestHttpRequestData 结束 JSON.parse(headjson) " );
                for (Object key : headermaps.entrySet()) {
                    header.addParam(((Map.Entry) key).getKey().toString(), ((Map.Entry) key).getValue());
                    logger.info(logplannameandcasename + "TestHttpRequestData headjsonmap key is: "+ ((Map.Entry) key).getKey().toString()+" values is: "+((Map.Entry) key).getValue());
                }
            }
            newob.setHeader(header);
            //Params
            HttpParamers params = new HttpParamers();
            if(!paramsjson.isEmpty())
            {
                logger.info(logplannameandcasename + "TestHttpRequestData paramsjson： " +paramsjson);

                logger.info(logplannameandcasename + "TestHttpRequestData 开始 JSON.parse(paramsjson) " );
                Map paramsmaps = (Map) JSON.parse(paramsjson);
                logger.info(logplannameandcasename + "TestHttpRequestData 结束 JSON.parse(paramsjson) " );
                for (Object key : paramsmaps.entrySet()) {
                    params.addParam(((Map.Entry) key).getKey().toString(), ((Map.Entry) key).getValue());
                    logger.info(logplannameandcasename + "TestHttpRequestData headjsonmap key is: "+ ((Map.Entry) key).getKey().toString()+" values is: "+((Map.Entry) key).getValue());
                }
            }
            newob.setParamers(params);
            //Body
            String PostData="";
            if(!bodyjson.isEmpty())
            {
                logger.info(logplannameandcasename + "TestHttpRequestData bodyjson： " +bodyjson);
                logger.info(logplannameandcasename + "TestHttpRequestData 开始 JSON.parse(bodyjson) " );
                Map bodyparamsmaps = (Map) JSON.parse(bodyjson);
                logger.info(logplannameandcasename + "TestHttpRequestData 结束 JSON.parse(bodyjson) " );
                if(requestcontenttype.equalsIgnoreCase("Form表单"))
                {
                    HttpParamers Bodyparams = new HttpParamers();
                    for (Object key : bodyparamsmaps.entrySet()) {
                        params.addParam(((Map.Entry) key).getKey().toString(), ((Map.Entry) key).getValue());
//                        Bodyparams.addParam(((Map.Entry) map).getKey().toString(),  ((Map.Entry) map).getValue());
                        logger.info(logplannameandcasename + "TestHttpRequestData Form表单 headjsonmap key is: "+ ((Map.Entry) key).getKey().toString()+" values is: "+((Map.Entry) key).getValue());
                    }
                    if (Bodyparams.getParams().size() > 0) {
                        try {
                            PostData = Bodyparams.getQueryString();
                            logger.info(logplannameandcasename + "TestHttpRequestData Form表单 性能 Bodyparamers  PostData:  " + PostData);
                        } catch (UnsupportedEncodingException e) {
                            logger.info(logplannameandcasename + "TestHttpRequestData Form表单 性能 Bodyparamers表单编码异常 :  " + e.getMessage());
                        }
                    }
                }
                else
                {
                    for (Object map : bodyparamsmaps.entrySet()) {
                        logger.info(logplannameandcasename + "TestHttpRequestData 非Form表单 headjsonmap key is: "+ ((Map.Entry) map).getKey().toString()+" values is: "+((Map.Entry) map).getValue());
                        PostData=((Map.Entry) map).getValue().toString();
                        logger.info(logplannameandcasename + "TestHttpRequestData 非Form表单 PostData is: "+bodyparamsmaps.get(map).toString());
                    }
                }
            }
            newob.setPostData(PostData);
        }
        catch (Exception ex)
        {
            logger.info(logplannameandcasename + "TestHttpRequestData 性能异常 " + ex.getMessage());
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
                if(Caseid.isEmpty())
                {
                    Result="未找到变量："+Value+"绑定的接口用例，请检查变量管理-变量管理中是否存在此变量";
                    return Result;
                }
                String ValueType = testMysqlHelp.GetVariablesDataType(Value);
                if(ValueType.isEmpty())
                {
                    Result="未找到变量："+Value+"绑定的接口用例，请检查变量管理-用例变量中是否存在此变量绑定的接口用例";
                    return Result;
                }
                //根据用例参数值是否以$开头，如果是则认为是变量通过变量表取到变量值
                String VariablesNameValue = testMysqlHelp.GetVariablesValues(PlanId, Caseid, BatchName, Value);
                if(VariablesNameValue.isEmpty())
                {
                    Result="未找到变量："+Value+"的值，请检查变量管理-变量结果中是否存在此变量值";
                    return Result;
                }
                else
                {
                    Result=GetDataByType(VariablesNameValue,ValueType);
                }
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
            //如果有相同的参数，则以全局参数的覆盖之,如果没有则添加
            header.getParams().put(key, ObjectValue);
            logger.info(logplannameandcasename + "TestHttpRequestData 全局参数Header中添加Key is :  " + key + "   Value  is:   " + ObjectValue);
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
        if (RequestContentType.equalsIgnoreCase("Form表单")) {
            httpHeader.addParam("Content-Type", "application/x-www-form-urlencoded");
        }
        return httpHeader;
    }

    //根据数据类型转换
    private Object GetDataByType(String Data,String ValueType)
    {
        Object Result=new Object();
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
