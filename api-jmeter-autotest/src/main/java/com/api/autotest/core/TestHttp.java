package com.api.autotest.core;

import com.api.autotest.common.utils.HttpHeader;
import com.api.autotest.common.utils.HttpParamers;
import com.api.autotest.common.utils.Httphelp;
import com.api.autotest.dto.RequestObject;
import com.api.autotest.dto.ResponeData;
import org.apache.log.Logger;
import static com.api.autotest.core.TestCaseData.logplannameandcasename;

public class TestHttp {
    private Logger logger = null;
    public TestHttp(Logger log)
    {
        logger=log;
    }
//    public ResponeData request(RequestObject requestObject) throws Exception {
//        ResponeData result = new ResponeData();
//        if (requestObject.getProtocal().equals("http") || requestObject.getProtocal().equals("https")) {
//            if (requestObject.getRequestmMthod().equals("get")) {
//                logger.info(logplannameandcasename + "TestHttp get请求，request url is ....." + Httphelp.GetRequestUrl(requestObject.getResource(), requestObject.getApistyle(), requestObject.getParamers()));
//            }
//            logger.info(logplannameandcasename + "TestHttp doService url is ....." + Httphelp.GetRequestUrl(requestObject.getResource(), requestObject.getApistyle(), requestObject.getParamers()));
//            result = Httphelp.doService(requestObject.getProtocal(), requestObject.getResource(), requestObject.getRequestmMthod(), requestObject.getApistyle(), requestObject.getParamers(), requestObject.getPostData(), requestObject.getRequestcontenttype(), requestObject.getHeader(), 30000, 30000);
//        }
//        return result;
//    }

    public  ResponeData doService(RequestObject requestObject) throws Exception {
        if(requestObject.getProtocal().isEmpty())
        {
            throw new Exception("当前用例所属的API所在的发布单元不存在，请检查是否已经被删除");
        }
        if(requestObject.getApistyle().isEmpty()&&requestObject.getRequestmMthod().isEmpty())
        {
            throw new Exception("当前用例所属的API不存在，请检查是否已经被删除");
        }
        if(requestObject.getDeployunitvisittype().isEmpty())
        {
            throw new Exception("当前用例所属的API所在的发布单元在环境部署中不存在，请检查是否已经被删除");
        }
        if(requestObject.getMachineip().isEmpty())
        {
            throw new Exception("当前用例所在的发布单元部署环境的服务器不存在，请检查是否已经被删除");
        }
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
                logger.info(logplannameandcasename + "TestHttp GET请求url无参数....." );
                responeData=Httphelp.GetWithNoParams(Protocal,Url,header,30000);
                logger.info(logplannameandcasename + "TestHttp GET请求url无参数完成....." );
            }
            else
            {
                if(requestObject.getRequestcontenttype().equalsIgnoreCase("Form表单"))
                {
                    //url-Form表单传值,值根据类型转换，可以实现url参数值是json和xml的形式
                    String GetParamUrl= Httphelp.GetRequestUrl(Url, ApiStyle, httpParamers);
                    logger.info(logplannameandcasename + "TestHttp GET请求url....." +GetParamUrl);
                    responeData=Httphelp.GetWithNoParams(Protocal,GetParamUrl,header,30000);
                    logger.info(logplannameandcasename + "TestHttp GET请求url完成....." +GetParamUrl);
                }
                else
                {
                    //取body，json,xml,text
                    String GetParamUrl= Httphelp.GetRequestUrl(Url, ApiStyle, httpParamers);
                    logger.info(logplannameandcasename + "TestHttp GET请求url取body，json,xml,text....." +GetParamUrl);
                    responeData=Httphelp.GetWithBody(Protocal,GetParamUrl,PostData,header,30000);
                    logger.info(logplannameandcasename + "TestHttp GET请求url取body，json,xml,text完成....." +GetParamUrl);

                }
            }
        }
        if(requestObject.getRequestmMthod().equalsIgnoreCase("POST"))
        {
            String GetParamUrl= Httphelp.GetRequestUrl(Url, ApiStyle, httpParamers);
            logger.info(logplannameandcasename + "TestHttp POST请求url....." +GetParamUrl);
            responeData=Httphelp.PostWithBody(Protocal,GetParamUrl,PostData,header,30000);
            logger.info(logplannameandcasename + "TestHttp POST请求url完成....." );
        }
        if(requestObject.getRequestmMthod().equalsIgnoreCase("PUT"))
        {
            String GetParamUrl= Httphelp.GetRequestUrl(Url, ApiStyle, httpParamers);
            logger.info(logplannameandcasename + "TestHttp PUT请求url....." +GetParamUrl);
            responeData=Httphelp.doPut(Protocal,GetParamUrl,PostData,header,30000);
            logger.info(logplannameandcasename + "TestHttp PUT请求url完成....." +GetParamUrl);
        }
        if(requestObject.getRequestmMthod().equalsIgnoreCase("DELETE"))
        {
            String GetParamUrl= Httphelp.GetRequestUrl(Url, ApiStyle, httpParamers);
            logger.info(logplannameandcasename + "TestHttp DELETE请求url....." +GetParamUrl);
            responeData=Httphelp.doDelete(Protocal,GetParamUrl,PostData,header,30000);
            logger.info(logplannameandcasename + "TestHttp DELETE请求url完成....." +GetParamUrl);
        }
        return responeData;
    }
}
