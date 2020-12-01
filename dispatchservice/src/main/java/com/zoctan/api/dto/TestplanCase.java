package com.zoctan.api.dto;

/**
 * @author Zoctan
 * @date 2018/10/16
 */
public class TestplanCase  {

  public Long getTestplanid() {
    return testplanid;
  }

  public void setTestplanid(Long testplanid) {
    this.testplanid = testplanid;
  }

  private Long testplanid;

  public Long getCaseid() {
    return caseid;
  }

  public void setCaseid(Long caseid) {
    this.caseid = caseid;
  }

  private Long caseid;

  public Long getBatchid() {
    return batchid;
  }

  public void setBatchid(Long batchid) {
    this.batchid = batchid;
  }

  private Long batchid;


  public String getPlanname() {
    return planname;
  }

  public void setPlanname(String planname) {
    this.planname = planname;
  }

  private String planname;

//  public String getJmeterpath() {
//    return jmeterpath;
//  }
//
//  public void setJmeterpath(String jmeterpath) {
//    this.jmeterpath = jmeterpath;
//  }
//
//  private String jmeterpath;
//
//  public String getJmxpath() {
//    return jmxpath;
//  }
//
//  public void setJmxpath(String jmxpath) {
//    this.jmxpath = jmxpath;
//  }
//
//  private String jmxpath;
//
//  public String getCasejmxname() {
//    return casejmxname;
//  }
//
//  public void setCasejmxname(String casejmxname) {
//    this.casejmxname = casejmxname;
//  }
//
//  private String casejmxname;

  public String getCasename() {
    return casename;
  }

  public void setCasename(String casename) {
    this.casename = casename;
  }

  private String casename;

  //  public String getDeployname() {
//    return deployname;
//  }
//
//  public void setDeployname(String deployname) {
//    this.deployname = deployname;
//  }
//
//  private String deployname;
//
  public String getBatchname() {
    return batchname;
  }

  public void setBatchname(String batchname) {
    this.batchname = batchname;
  }

  private String batchname;
//
//  public String getExpect() {
//    return expect;
//  }
//
//  public void setExpect(String expect) {
//    this.expect = expect;
//  }
//
//  private String expect;


}
