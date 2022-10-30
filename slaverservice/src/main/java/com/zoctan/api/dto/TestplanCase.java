package com.zoctan.api.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Zoctan
 * @date 2018/10/16
 */
public class TestplanCase  {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  public Long getExecplanid() {
    return execplanid;
  }

  public void setExecplanid(Long execplanid) {
    this.execplanid = execplanid;
  }

  private Long execplanid;


  public Long getTestcaseid() {
    return testcaseid;
  }

  public void setTestcaseid(Long testcaseid) {
    this.testcaseid = testcaseid;
  }

  private Long testcaseid;

  public Long getSlaverid() {
    return slaverid;
  }

  public void setSlaverid(Long slaverid) {
    this.slaverid = slaverid;
  }

  private Long slaverid;


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

  public String getSlavername() {
    return slavername;
  }

  public void setSlavername(String slavername) {
    this.slavername = slavername;
  }

  private String slavername;

  private String planname;

  public String getDeployunitname() {
    return deployunitname;
  }

  public void setDeployunitname(String deployunitname) {
    this.deployunitname = deployunitname;
  }

  private String deployunitname;

  public String getExpect() {
    return expect;
  }

  public void setExpect(String expect) {
    this.expect = expect;
  }

  private String expect;

  public String getCasejmxname() {
    return casejmxname;
  }

  public void setCasejmxname(String casejmxname) {
    this.casejmxname = casejmxname;
  }

  private String casejmxname;

  public String getCasename() {
    return casename;
  }

  public void setCasename(String casename) {
    this.casename = casename;
  }

  private String casename;

  public String getBatchname() {
    return batchname;
  }

  public void setBatchname(String batchname) {
    this.batchname = batchname;
  }

  private String batchname;

  public String getPlantype() {
    return plantype;
  }

  public void setPlantype(String plantype) {
    this.plantype = plantype;
  }

  private String plantype;


  public Long getThreadnum() {
    return threadnum;
  }

  public void setThreadnum(Long threadnum) {
    this.threadnum = threadnum;
  }

  public Long getLoops() {
    return loops;
  }

  public void setLoops(Long loops) {
    this.loops = loops;
  }

  private Long threadnum;
  private Long loops;
}
