package com.zoctan.api.dto;


import com.zoctan.api.core.service.HttpHeader;
import com.zoctan.api.core.service.HttpParamers;

/**
 * Created by fanseasn on 2020/11/30.
 */
/*
 @author Season
 @DESCRIPTION 
 @create 2020/11/30
*/
public class RequestObject {


    public String getTestplanid() {
        return testplanid;
    }

    public void setTestplanid(String testplanid) {
        this.testplanid = testplanid;
    }

    public String getCaseid() {
        return caseid;
    }

    public void setCaseid(String caseid) {
        this.caseid = caseid;
    }

    public String getBatchid() {
        return batchid;
    }

    public void setBatchid(String batchid) {
        this.batchid = batchid;
    }

    public String getSlaverid() {
        return slaverid;
    }

    public void setSlaverid(String slaverid) {
        this.slaverid = slaverid;
    }

    public String getBatchname() {
        return batchname;
    }

    public void setBatchname(String batchname) {
        this.batchname = batchname;
    }

    public String getCasetype() {
        return casetype;
    }

    public void setCasetype(String casetype) {
        this.casetype = casetype;
    }

    public String getExpect() {
        return expect;
    }

    public void setExpect(String expect) {
        this.expect = expect;
    }

    private String testplanid = "";

    public String getDeployunitid() {
        return deployunitid;
    }

    public void setDeployunitid(String deployunitid) {
        this.deployunitid = deployunitid;
    }

    private String deployunitid = "";

    private String caseid = "";
    private String batchid = "";
    private String slaverid = "";
    private String batchname = "";

    public String getTestplanname() {
        return testplanname;
    }

    public void setTestplanname(String testplanname) {
        this.testplanname = testplanname;
    }

    private String testplanname = "";

    private String casetype = "";
    private String expect = "";





    public String getRequestmMthod() {
        return RequestmMthod;
    }

    public void setRequestmMthod(String requestmMthod) {
        RequestmMthod = requestmMthod;
    }

    private String RequestmMthod;

    public String getProtocal() {
        return protocal;
    }

    public void setProtocal(String protocal) {
        this.protocal = protocal;
    }

    private String protocal;


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


    public HttpParamers getBodyparamers() {
        return Bodyparamers;
    }

    public void setBodyparamers(HttpParamers bodyparamers) {
        Bodyparamers = bodyparamers;
    }

    private HttpParamers Bodyparamers;


    public String getRequestcontenttype() {
        return requestcontenttype;
    }

    public void setRequestcontenttype(String requestcontenttype) {
        this.requestcontenttype = requestcontenttype;
    }

    private String requestcontenttype;

    public String getResponecontenttype() {
        return responecontenttype;
    }

    public void setResponecontenttype(String responecontenttype) {
        this.responecontenttype = responecontenttype;
    }

    private String responecontenttype;



    public HttpHeader getHeader() {
        return header;
    }

    public void setHeader(HttpHeader header) {
        this.header = header;
    }

    private HttpHeader header;

    public String getPostData() {
        return PostData;
    }

    public void setPostData(String postData) {
        PostData = postData;
    }

    private String PostData;
}
