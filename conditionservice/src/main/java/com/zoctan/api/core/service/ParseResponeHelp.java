package com.zoctan.api.core.service;

import cn.hutool.core.util.XmlUtil;
import com.jayway.jsonpath.JsonPath;
import com.zoctan.api.controller.TestconditionController;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;

import javax.xml.xpath.XPathConstants;

@Slf4j
public class ParseResponeHelp {
    public String ParseRespone(String ResponeResultType,String Respone,String Path) throws Exception {
        String Result="";
        if (ResponeResultType.equalsIgnoreCase("json")||ResponeResultType.equalsIgnoreCase("application/json;charset=utf-8")||ResponeResultType.equalsIgnoreCase("application/json")) {
            Result = ParseJsonRespone(Path, Respone);
        }
        if (ResponeResultType.equalsIgnoreCase("xml")||ResponeResultType.equalsIgnoreCase("application/xml;charset=utf-8")||ResponeResultType.equalsIgnoreCase("application/xml")) {
            Result = ParseXmlRespone(Path, Respone);
            //处理xml
        }
        return Result;
    }

    public String ParseJsonRespone(String JSPath,String JsonRespone) throws Exception {
        String Result="";
        try {
             Result= JsonPath.read(JsonRespone,JSPath).toString();
            ParseResponeHelp.log.info("接口子条件条件报告子条件处理变量表达式-============：" + JSPath + " 响应数据类型" + JsonRespone+" Result is:"+Result);
        }
        catch (Exception ex)
        {
            Result=ex.getMessage();
            throw new Exception("变量管理中此变量值表达式JsonPath："+JSPath+" 在接口子条件的请求响应: "+JsonRespone+" 中未匹配到对应的值");
        }
        return Result;
    }


    public String ParseXmlRespone(String  XPath,String ActualXml) throws Exception {
        String Result="";
        try {
            Document docResult= XmlUtil.readXML(ActualXml);
            Result = XmlUtil.getByXPath(XPath, docResult, XPathConstants.STRING).toString();
        }
        catch (Exception ex)
        {
            Result=ex.getMessage();
            throw new Exception("变量管理中此变量值表达式XPath："+XPath+" 在接口子条件的请求响应："+ActualXml+" 中未匹配到对应的值");
        }
        return Result;
    }
}
