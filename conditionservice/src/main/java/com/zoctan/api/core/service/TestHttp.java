package com.zoctan.api.core.service;


import com.zoctan.api.dto.RequestObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestHttp {
    public  ResponeData doService(RequestObject requestObject) throws Exception {
        ResponeData responeData=new ResponeData();
        String Protocal=requestObject.getProtocal();
        String ApiStyle=requestObject.getApistyle();
        String Url=requestObject.getResource();
        HttpHeader header=requestObject.getHeader();
        HttpParamers httpParamers=requestObject.getParamers();
        String PostData=requestObject.getPostData();
        if(requestObject.getRequestmMthod().equalsIgnoreCase("GET"))
        {
            if(httpParamers.getParams().size()==0&&PostData.isEmpty())
            {
                //url无参数
                TestHttp.log.info( "TestHttp GET请求url无参数....." );
                responeData= Httphelp.GetWithNoParams(Protocal,Url,header,30000);
                TestHttp.log.info(  "TestHttp GET请求url无参数完成....." );
            }
            if(requestObject.getRequestcontenttype().equalsIgnoreCase("Form表单"))
            {
                //url-Form表单传值,值根据类型转换，可以实现url参数值是json和xml的形式
                String GetParamUrl= Httphelp.GetRequestUrl(Url, ApiStyle, httpParamers);
                TestHttp.log.info(  "TestHttp GET请求url....." +GetParamUrl);
                responeData= Httphelp.GetWithNoParams(Protocal,GetParamUrl,header,30000);
                TestHttp.log.info(  "TestHttp GET请求url完成....." +GetParamUrl);
            }
            else
            {
                //取body，json,xml,text
                String GetParamUrl= Httphelp.GetRequestUrl(Url, ApiStyle, httpParamers);
                TestHttp.log.info(  "TestHttp GET请求url取body，json,xml,text....." +GetParamUrl);
                responeData= Httphelp.GetWithBody(Protocal,GetParamUrl,PostData,header,30000);
                TestHttp.log.info(  "TestHttp GET请求url取body，json,xml,text完成....." +GetParamUrl);

            }
        }
        if(requestObject.getRequestmMthod().equalsIgnoreCase("POST"))
        {
            String GetParamUrl= Httphelp.GetRequestUrl(Url, ApiStyle, httpParamers);
            TestHttp.log.info(  "TestHttp POST请求url....." +GetParamUrl);
            responeData= Httphelp.PostWithBody(Protocal,GetParamUrl,PostData,header,30000);
            TestHttp.log.info(  "TestHttp POST请求url完成....." );
        }
        if(requestObject.getRequestmMthod().equalsIgnoreCase("PUT"))
        {
            String GetParamUrl= Httphelp.GetRequestUrl(Url, ApiStyle, httpParamers);
            TestHttp.log.info(  "TestHttp PUT请求url....." +GetParamUrl);
            responeData= Httphelp.doPut(Protocal,GetParamUrl,PostData,header,30000);
            TestHttp.log.info(  "TestHttp PUT请求url完成....." +GetParamUrl);
        }
        if(requestObject.getRequestmMthod().equalsIgnoreCase("DELETE"))
        {
            String GetParamUrl= Httphelp.GetRequestUrl(Url, ApiStyle, httpParamers);
            TestHttp.log.info(  "TestHttp DELETE请求url....." +GetParamUrl);
            responeData= Httphelp.doDelete(Protocal,GetParamUrl,PostData,header,30000);
            TestHttp.log.info(  "TestHttp DELETE请求url完成....." +GetParamUrl);
        }
        return responeData;
    }
}
