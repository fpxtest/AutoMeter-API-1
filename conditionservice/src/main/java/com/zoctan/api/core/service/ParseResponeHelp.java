package com.zoctan.api.core.service;

import com.jayway.jsonpath.JsonPath;

public class ParseResponeHelp {
    public String ParseRespone(String ResponeResultType,String Respone,String Path)
    {
        String Result="";
        if (ResponeResultType.equals(new String("json"))) {
            Result = ParseJsonRespone(Path, Respone);
        }
        if (ResponeResultType.equals(new String("xml"))) {
            //处理xml
        }
        return Result;
    }

    public String ParseJsonRespone(String JSPath,String JsonRespone)
    {
        String Result="";
        try {
             Result= JsonPath.read(JsonRespone,JSPath).toString();
        }
        catch (Exception ex)
        {
            Result=ex.getMessage();
        }
        return Result;
    }


    public void ParseXmlRespone()
    {}
}
