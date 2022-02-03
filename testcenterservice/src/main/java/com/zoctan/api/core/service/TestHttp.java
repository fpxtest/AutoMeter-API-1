package com.zoctan.api.core.service;

import com.zoctan.api.dto.TestResponeData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestHttp {
    public  TestResponeData doService(String Protocal,String ApiStyle,String Url,HttpHeader header,HttpParamers httpParamers,String PostData,String RequestContenType) throws Exception {
        TestResponeData testResponeData=new TestResponeData();
        if(RequestContenType.equalsIgnoreCase("GET"))
        {
            if(httpParamers.getParams().size()==0&&PostData.isEmpty())
            {
                //url无参数
                TestHttp.log.info( "TestHttp GET请求url无参数....." );
                testResponeData= Httphelp.GetWithNoParams(Protocal,Url,header,30000);
                TestHttp.log.info( "TestHttp GET请求url无参数完成....." );
            }
            if(RequestContenType.equalsIgnoreCase("Form表单"))
            {
                //url-Form表单传值,值根据类型转换，可以实现url参数值是json和xml的形式
                String GetParamUrl= Httphelp.GetRequestUrl(Url, ApiStyle, httpParamers);
                TestHttp.log.info( "TestHttp GET请求url....." +GetParamUrl);
                testResponeData= Httphelp.GetWithNoParams(Protocal,GetParamUrl,header,30000);
                TestHttp.log.info( "TestHttp GET请求url完成....." +GetParamUrl);
            }
            else
            {
                //取body，json,xml,text
                String GetParamUrl= Httphelp.GetRequestUrl(Url, ApiStyle, httpParamers);
                TestHttp.log.info( "TestHttp GET请求url取body，json,xml,text....." +GetParamUrl);
                testResponeData= Httphelp.GetWithBody(Protocal,GetParamUrl,PostData,header,30000);
                TestHttp.log.info( "TestHttp GET请求url取body，json,xml,text完成....." +GetParamUrl);

            }
        }
        if(RequestContenType.equalsIgnoreCase("POST"))
        {
            String GetParamUrl= Httphelp.GetRequestUrl(Url, ApiStyle, httpParamers);
            TestHttp.log.info( "TestHttp POST请求url....." +GetParamUrl);
            testResponeData= Httphelp.PostWithBody(Protocal,GetParamUrl,PostData,header,30000);
            TestHttp.log.info( "TestHttp POST请求url完成....." );
        }
        if(RequestContenType.equalsIgnoreCase("PUT"))
        {
            String GetParamUrl= Httphelp.GetRequestUrl(Url, ApiStyle, httpParamers);
            TestHttp.log.info( "TestHttp PUT请求url....." +GetParamUrl);
            testResponeData= Httphelp.doPut(Protocal,GetParamUrl,PostData,header,30000);
            TestHttp.log.info( "TestHttp PUT请求url完成....." +GetParamUrl);
        }
        if(RequestContenType.equalsIgnoreCase("DELETE"))
        {
            String GetParamUrl= Httphelp.GetRequestUrl(Url, ApiStyle, httpParamers);
            TestHttp.log.info( "TestHttp DELETE请求url....." +GetParamUrl);
            testResponeData= Httphelp.doDelete(Protocal,GetParamUrl,PostData,header,30000);
            TestHttp.log.info( "TestHttp DELETE请求url完成....." +GetParamUrl);
        }
        return testResponeData;
    }
}
