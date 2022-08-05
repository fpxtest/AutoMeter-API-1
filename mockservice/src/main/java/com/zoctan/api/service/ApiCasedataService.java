package com.zoctan.api.service;

import com.zoctan.api.core.service.Service;
import com.zoctan.api.entity.ApiCasedata;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Zoctan
* @date 2020/09/21
*/
public interface ApiCasedataService extends Service<ApiCasedata> {

    List<ApiCasedata>  GetCaseDatasByCaseID(@Param("caseid") Long caseid);

    ApiCasedata  GetCaseDatasByCaseIDAndApiparamAndType(@Param("caseid") Long caseid,@Param("apiparam") String apiparam,@Param("propertytype")String propertytype);

    void UpdateByCaseIDAndApiparam(@Param("caseid") Long caseid,@Param("apiparam") String apiparam,@Param("propertytype")String propertytype,@Param("apiparamvalue")String apiparamvalue);

}
