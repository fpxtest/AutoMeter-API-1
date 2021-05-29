package com.zoctan.api.dto;

/**
 * @author Zoctan
 * @date 2018/10/16
 */
public class Testplanandbatch {


  public Long getPlanid() {
    return planid;
  }

  public void setPlanid(Long planid) {
    this.planid = planid;
  }

  private Long planid;

  public String getBatchname() {
    return batchname;
  }

  public void setBatchname(String batchname) {
    this.batchname = batchname;
  }

  private String batchname;


}
