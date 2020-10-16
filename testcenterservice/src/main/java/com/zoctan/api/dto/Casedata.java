package com.zoctan.api.dto;

import com.zoctan.api.entity.ApiCasedata;

import java.util.List;

/**
 * @author Zoctan
 * @date 2018/10/16
 */
public class Casedata extends ApiCasedata {
  /** 角色对应的权限Id列表 */
  private List<ApiCasedata> casedataMap;

  public List<ApiCasedata> getCasedataMap() {
    return casedataMap;
  }

  public void setCasedataMap(List<ApiCasedata> casedataMap) {
    this.casedataMap = casedataMap;
  }



}
