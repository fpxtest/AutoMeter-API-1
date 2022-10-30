package com.zoctan.api.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class JmeterPerformanceObject {


    public Long getProjectid() {
        return projectid;
    }

    public void setProjectid(Long projectid) {
        this.projectid = projectid;
    }

    private Long projectid;
    public long getTestplanid() {
        return testplanid;
    }

    public void setTestplanid(long testplanid) {
        this.testplanid = testplanid;
    }

    public long getCaseid() {
        return caseid;
    }

    public void setCaseid(long caseid) {
        this.caseid = caseid;
    }

    public long getBatchid() {
        return batchid;
    }

    public void setBatchid(long batchid) {
        this.batchid = batchid;
    }

    public long getSlaverid() {
        return slaverid;
    }

    public void setSlaverid(long slaverid) {
        this.slaverid = slaverid;
    }

    public String getBatchname() {
        return batchname;
    }

    public void setBatchname(String batchname) {
        this.batchname = batchname;
    }

    public String getExecuteplanname() {
        return executeplanname;
    }

    public void setExecuteplanname(String executeplanname) {
        this.executeplanname = executeplanname;
    }

    public String getCasename() {
        return casename;
    }

    public void setCasename(String casename) {
        this.casename = casename;
    }

    public String getExpect() {
        return expect;
    }

    public void setExpect(String expect) {
        this.expect = expect;
    }

    public String getProtocal() {
        return protocal;
    }

    public void setProtocal(String protocal) {
        this.protocal = protocal;
    }

    public String getRequestmMthod() {
        return RequestmMthod;
    }

    public void setRequestmMthod(String requestmMthod) {
        RequestmMthod = requestmMthod;
    }

    public String getCasetype() {
        return casetype;
    }

    public void setCasetype(String casetype) {
        this.casetype = casetype;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getApistyle() {
        return apistyle;
    }

    public void setApistyle(String apistyle) {
        this.apistyle = apistyle;
    }

    public String getRequestcontenttype() {
        return requestcontenttype;
    }

    public void setRequestcontenttype(String requestcontenttype) {
        this.requestcontenttype = requestcontenttype;
    }

    public String getResponecontenttype() {
        return responecontenttype;
    }

    public void setResponecontenttype(String responecontenttype) {
        this.responecontenttype = responecontenttype;
    }

    public String getHeadjson() {
        return headjson;
    }

    public void setHeadjson(String headjson) {
        this.headjson = headjson;
    }

    public String getParamsjson() {
        return paramsjson;
    }

    public void setParamsjson(String paramsjson) {
        this.paramsjson = paramsjson;
    }

    public String getBodyjson() {
        return bodyjson;
    }

    public void setBodyjson(String bodyjson) {
        this.bodyjson = bodyjson;
    }

    public String getDubbojson() {
        return dubbojson;
    }

    public void setDubbojson(String dubbojson) {
        this.dubbojson = dubbojson;
    }

    public String getMysqlurl() {
        return mysqlurl;
    }

    public void setMysqlurl(String mysqlurl) {
        this.mysqlurl = mysqlurl;
    }

    public String getMysqlusername() {
        return mysqlusername;
    }

    public void setMysqlusername(String mysqlusername) {
        this.mysqlusername = mysqlusername;
    }

    public String getMysqlpassword() {
        return mysqlpassword;
    }

    public void setMysqlpassword(String mysqlpassword) {
        this.mysqlpassword = mysqlpassword;
    }

    public String getMachineip() {
        return machineip;
    }

    public void setMachineip(String machineip) {
        this.machineip = machineip;
    }

    private String machineip;

    public String getDeployunitvisittype() {
        return deployunitvisittype;
    }

    public void setDeployunitvisittype(String deployunitvisittype) {
        this.deployunitvisittype = deployunitvisittype;
    }

    private String deployunitvisittype;

    private long testplanid;
    private long caseid;
    private long batchid;
    private long slaverid;
    private String batchname;
    private String executeplanname;
    private String casename;
    private String expect;
    private String protocal;
    private String RequestmMthod;
    private String casetype;
    private String resource;
    private String apistyle;
    private String requestcontenttype;
    private String responecontenttype;
    private String headjson;
    private String paramsjson;
    private String bodyjson;
    private String dubbojson;

    public String getPostdata() {
        return postdata;
    }

    public void setPostdata(String postdata) {
        this.postdata = postdata;
    }

    private String postdata;


    public String getRadomvariablejson() {
        return radomvariablejson;
    }

    public void setRadomvariablejson(String radomvariablejson) {
        this.radomvariablejson = radomvariablejson;
    }

    private String radomvariablejson;
    private String mysqlurl;
    private String mysqlusername;
    private String mysqlpassword;

}