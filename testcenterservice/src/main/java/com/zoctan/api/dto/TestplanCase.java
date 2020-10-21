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

  public String getJmeterpath() {
    return jmeterpath;
  }

  public void setJmeterpath(String jmeterpath) {
    this.jmeterpath = jmeterpath;
  }

  private String jmeterpath;

  public String getJmxpath() {
    return jmxpath;
  }

  public void setJmxpath(String jmxpath) {
    this.jmxpath = jmxpath;
  }

  private String jmxpath;

  public String getDeployname() {
    return deployname;
  }

  public void setDeployname(String deployname) {
    this.deployname = deployname;
  }

  private String deployname;

}
