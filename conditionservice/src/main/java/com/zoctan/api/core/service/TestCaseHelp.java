package com.zoctan.api.core.service;

/**
 * Created by fanseasn on 2020/10/17.
 */
/*
 @author Season
 @DESCRIPTION 
 @create 2020/10/17
*/

import com.alibaba.fastjson.JSON;
import com.zoctan.api.dto.RequestObject;
import com.zoctan.api.dto.TestResponeData;
import com.zoctan.api.entity.*;
import com.zoctan.api.service.ApicasesVariablesService;
import com.zoctan.api.service.TestvariablesService;
import com.zoctan.api.service.TestvariablesValueService;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;




/**
 * 用例数据
 */
@Slf4j
public class TestCaseHelp {
    @Resource
    private ApicasesVariablesService apicasesVariablesService;

    @Resource
    private TestvariablesService testvariablesService;

    @Resource
    private TestvariablesValueService testvariablesValueService;


    // 拼装请求需要的用例数据
    public RequestObject GetCaseRequestData(List<ApiCasedata> apiCasedataList, Api api, Apicases apicases, Deployunit deployunit, Macdepunit macdepunit, Machine machine) throws Exception {
        RequestObject ro=new RequestObject();
        try
       {
           // url请求资源路径
           String path = api.getPath();
           if (!path.startsWith("/")) {
               path = "/" + path;
           }
           String casetype=apicases.getCasetype();
           String expect=apicases.getExpect();

           //获取请求响应的数据格式
           String requestcontenttype =api.getRequestcontenttype();
           String responecontenttype =api.getResponecontenttype();
           // http请求方式 get，post
           String method = api.getVisittype();
           // api风格
           String apistyle =api.getApistyle();
           // 协议 http,https,rpc
           String protocal = deployunit.getProtocal().toLowerCase();
           // 发布单元端口
           String port = deployunit.getPort();
           // 获取发布单元访问方式，ip或者域名
           String deployunitvisittype =macdepunit.getVisittype();
           // 根据访问方式来确定ip还是域名
           String testserver = "";
           String resource = "";
           if(deployunitvisittype.equals(new String("ip")))
           {
               testserver =machine.getIp();
               resource = protocal + "://" + testserver + ":" + port + path;
           }
           else
           {
               testserver= macdepunit.getDomain();
               resource = protocal + "://" + testserver  + path;
           }
           HashMap<String, String> headmap=fixhttprequestdatas("Header",apiCasedataList);
           HashMap<String, String> bodymap=fixhttprequestdatas("Body",apiCasedataList);
           HashMap<String, String> paramsmap=fixhttprequestdatas("Params",apiCasedataList);


           //Header
           HttpHeader header = new HttpHeader();
           header = AddHeaderByRequestContentType(header, requestcontenttype);
           if(headmap.size()>0)
           {
               for (String key : headmap.keySet()) {
                   String Value = headmap.get(key);
                   //Object ObjectValue = GetVariablesObjectValues(Value, PlanID.toString(), BatchName);
                   header.addParam(key, Value);
                   //header.addParam(key, headmap.get(key));
               }
           }
           //url参数
           HttpParamers paramers = new  HttpParamers();
           // 设置参数
           if(paramsmap.size()>0)
           {
               for (String key : paramsmap.keySet()) {
                   String Value = paramsmap.get(key);
                   //Object ObjectValue = GetVariablesObjectValues(Value, PlanID.toString(), BatchName);
                   paramers.addParam(key, Value);
               }
           }
           //Body参数
           HttpParamers Bodyparamers = new HttpParamers();
           //Body内容
           String PostData = "";
           // 设置Body
           if(bodymap.size()>0)
           {
               if (requestcontenttype.equalsIgnoreCase("Form表单")) {
                   for (String key : bodymap.keySet()) {
                       String Value = bodymap.get(key);
                       //Object ObjectValue = GetVariablesObjectValues(Value, PlanID.toString(), BatchName);
                       Bodyparamers.addParam(key, Value);
                   }
                   try {
                       PostData=GetParasPostData(requestcontenttype,Bodyparamers);
                   } catch (IOException e) {
                       TestCaseHelp.log.info("处理Body参数数据异常："+e.getMessage());
                       throw new Exception("处理Body参数数据异常："+e.getMessage());
                   }
               }
               else
               {
                   for (String Key : bodymap.keySet()) {
                       PostData = bodymap.get(Key);
                   }
               }
           }
           TestCaseHelp.log.info("处理Body完后，PostData："+PostData);
           ro.setPostData(PostData);
           ro.setExpect(expect);
           ro.setCasetype(casetype);
           ro.setHeader(header);
           ro.setProtocal(protocal);
           ro.setApistyle(apistyle);
           ro.setParamers(paramers);
           ro.setBodyparamers(Bodyparamers);
           ro.setRequestcontenttype(requestcontenttype);
           ro.setRequestmMthod(method);
           ro.setResource(resource);
           ro.setResponecontenttype(responecontenttype);
       }
       catch (Exception ex)
       {
           TestCaseHelp.log.info("GetCaseRequestData异常："+ex.getMessage());
       }
        return ro;
    }

    // 获取用例Header，params，Body，Dubbo数据
    public HashMap<String, String> fixhttprequestdatas(String MapType,List<ApiCasedata> casedatalist) {
        HashMap<String, String> DataMap=new HashMap<>();
        for (ApiCasedata data : casedatalist) {
            String propertytype= data.getPropertytype();
            if (propertytype.equals(MapType)) {
                DataMap.put(data.getApiparam().trim(), data.getApiparamvalue().trim());
            }
        }
        return DataMap;
    }

    private String GetParasPostData(String RequestContentType, HttpParamers paramers) throws IOException {
        String Result = "";
        if (RequestContentType.equals("json")) {
            paramers.setJsonParamer();
            Result = paramers.getJsonParamer();
        }
        if (RequestContentType.equals("form表单")) {
            Result = paramers.getQueryString("UTF-8");
        }
        if (RequestContentType.equals("xml")) {

        } else {

        }
        return Result;
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

    //获取参数值的具体内容，支持$变量
    private Object GetVariablesObjectValues(String Value, String PlanId, String BatchName) {
        Object Result = "";
        if (Value.trim().contains("$")) {
            if (Value.trim().length() == 1) {
                Result = Value;
            } else {
                String Prix[]=Value.split("\\+");
                for (String PrixStr: Prix) {
                    if(PrixStr.contains("$"))
                    {
                        TestCaseHelp.log.info("TestHttpRequestData $PrixStr :  " + PrixStr );
                        Result=Result.toString()+GetVariablesDataType(PrixStr,PlanId,BatchName);
                        TestCaseHelp.log.info("TestHttpRequestData $PrixStr Result :  " + Result );
                    }
                    else
                    {
                        TestCaseHelp.log.info("TestHttpRequestData PrixStr :  " + PrixStr );
                        Result=Result+PrixStr;
                    }
                }
//                Value = Value.substring(1);
//                String Caseid = testMysqlHelp.GetCaseIdByVariablesName(Value);
//                if(Caseid.isEmpty())
//                {
//                    Result="未找到变量："+Value+"绑定的接口用例，请检查变量管理-变量管理中是否存在此变量";
//                    return Result;
//                }
//                String ValueType = testMysqlHelp.GetVariablesDataType(Value);
//                if(ValueType.isEmpty())
//                {
//                    Result="未找到变量："+Value+"绑定的接口用例，请检查变量管理-用例变量中是否存在此变量绑定的接口用例";
//                    return Result;
//                }
//                //根据用例参数值是否以$开头，如果是则认为是变量通过变量表取到变量值
//                String VariablesNameValue = testMysqlHelp.GetVariablesValues(PlanId, Caseid, BatchName, Value);
//                if(VariablesNameValue.isEmpty())
//                {
//                    Result="未找到变量："+Value+"的值，请检查变量管理-变量结果中是否存在此变量值";
//                    return Result;
//                }
//                else
//                {
//                    Result=GetDataByType(VariablesNameValue,ValueType);
//                }
            }
        } else {
            Result = Value;
        }
        return Result;
    }


    private Object GetVariablesDataType(String Value,String PlanId, String BatchName)
    {
        Object Result="";
        Value = Value.substring(1);
        TestCaseHelp.log.info( "TestHttpRequestData GetVariablesDataType Value :  " + Value );

        ApicasesVariables apicasesVariables = apicasesVariablesService.getBy("variablesname",Value);// testMysqlHelp.GetCaseIdByVariablesName(Value);
        if(apicasesVariables==null)
        {
            Result="未找到变量："+Value+"绑定的接口用例，请检查变量管理-变量管理中是否存在此变量";
            return Result;
        }

        Testvariables testvariables = testvariablesService.getBy("testvariablesname",Value); //testMysqlHelp.GetVariablesDataType(Value);
        if(testvariables==null)
        {
            Result="未找到变量："+Value+"绑定的接口用例，请检查变量管理-用例变量中是否存在此变量绑定的接口用例";
            return Result;
        }
        //根据用例参数值是否以$开头，如果是则认为是变量通过变量表取到变量值
        String Caseid=apicasesVariables.getCaseid().toString();
        TestvariablesValue testvariablesValue = testvariablesValueService.findtestvariablesvalue(Long.parseLong(PlanId),Long.parseLong(Caseid),BatchName,Value);//.fi   testMysqlHelp.GetVariablesValues(PlanId, Caseid, BatchName, Value);
        if(testvariablesValue==null)
        {
            Result="未找到变量："+Value+"的值，请检查变量管理-变量结果中是否存在此变量值";
            return Result;
        }
        else
        {
            String ValueType=testvariables.getTestvariablestype();
            String VariablesNameValue =testvariablesValue.getVariablesvalue();
            Result=GetDataByType(VariablesNameValue,ValueType);
        }
        return Result;
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

    // 发送http请求
    public ResponeData request(RequestObject requestObject) throws Exception {
        ResponeData result=new ResponeData();
        TestHttp testHttp=new TestHttp();
        if (requestObject.getProtocal().equals("http")||requestObject.getProtocal().equals("https")) {
            result = testHttp.doService(requestObject);
        }
        return result;
    }
}