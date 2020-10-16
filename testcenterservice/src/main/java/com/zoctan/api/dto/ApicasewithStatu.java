package com.zoctan.api.dto;

import com.zoctan.api.entity.Apicases;

/**
 * @author Zoctan
 * @date 2018/10/16
 */
public class ApicasewithStatu extends Apicases {
  public Boolean getStatus() {
    return status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  private Boolean status;

}
