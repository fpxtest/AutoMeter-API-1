package com.api.autotest.dto;

import com.api.autotest.common.utils.HttpHeader;
import com.api.autotest.common.utils.HttpParamers;

/**
 * Created by fanseasn on 2020/11/30.
 */
/*
 @author Season
 @DESCRIPTION 
 @create 2020/11/30
*/
public class RequestObject {

    public String getRequestmMthod() {
        return RequestmMthod;
    }

    public void setRequestmMthod(String requestmMthod) {
        RequestmMthod = requestmMthod;
    }

    private String RequestmMthod;

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    private String resource;

    public String getApistyle() {
        return apistyle;
    }

    public void setApistyle(String apistyle) {
        this.apistyle = apistyle;
    }

    private String apistyle;


    public HttpParamers getParamers() {
        return paramers;
    }

    public void setParamers(HttpParamers paramers) {
        this.paramers = paramers;
    }

    private HttpParamers paramers;


    public String getRequestcontenttype() {
        return requestcontenttype;
    }

    public void setRequestcontenttype(String requestcontenttype) {
        this.requestcontenttype = requestcontenttype;
    }

    private String requestcontenttype;


    public HttpHeader getHeader() {
        return header;
    }

    public void setHeader(HttpHeader header) {
        this.header = header;
    }

    private HttpHeader header;
}
