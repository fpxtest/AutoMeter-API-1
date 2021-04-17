package com.zoctan.api.dto;

/**
 * @author Zoctan
 * @date 2018/10/16
 */
public class CaseReportStatics {


  public String getPlanname() {
    return planname;
  }

  public void setPlanname(String planname) {
    this.planname = planname;
  }

  private String planname;

  public Long getTestcasenums() {
    return testcasenums;
  }

  public void setTestcasenums(Long testcasenums) {
    this.testcasenums = testcasenums;
  }

  public Long getPassnums() {
    return passnums;
  }

  public void setPassnums(Long passnums) {
    this.passnums = passnums;
  }

  public Long getFailnums() {
    return failnums;
  }

  public void setFailnums(Long failnums) {
    this.failnums = failnums;
  }

  private Long testcasenums;

  private Long passnums;

  private Long failnums;

  public Long getCosttimes() {
    return costtimes;
  }

  public void setCosttimes(Long costtimes) {
    this.costtimes = costtimes;
  }

  private Long costtimes;



  public String getBatchname() {
    return batchname;
  }

  public void setBatchname(String batchname) {
    this.batchname = batchname;
  }

  private String batchname;




}
