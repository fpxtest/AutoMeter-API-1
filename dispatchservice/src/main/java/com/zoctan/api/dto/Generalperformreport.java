package com.zoctan.api.dto;

/**
 * @author Zoctan
 * @date 2018/10/16
 */
public class Generalperformreport {



  private String planid;

  public String getPlanid() {
    return planid;
  }

  public void setPlanid(String planid) {
    this.planid = planid;
  }

  public String getCaseid() {
    return caseid;
  }

  public void setCaseid(String caseid) {
    this.caseid = caseid;
  }

  public String getBatchname() {
    return batchname;
  }

  public void setBatchname(String batchname) {
    this.batchname = batchname;
  }

  public String getFolder() {
    return folder;
  }

  public void setFolder(String folder) {
    this.folder = folder;
  }

  private String caseid;
  private String batchname;
  private String folder;









}
