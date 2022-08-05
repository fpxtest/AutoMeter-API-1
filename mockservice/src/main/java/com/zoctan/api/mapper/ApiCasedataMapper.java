package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.ApiCasedata;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ApiCasedataMapper extends MyMapper<ApiCasedata> {

    List<ApiCasedata> GetCaseDatasByCaseID(@Param("caseid") Long caseid);

    ApiCasedata GetCaseDatasByCaseIDAndApiparamAndType(@Param("caseid") Long caseid, @Param("apiparam") String apiparam, @Param("propertytype") String propertytype);

    void UpdateByCaseIDAndApiparam(@Param("caseid") Long caseid, @Param("apiparam") String apiparam, @Param("propertytype") String propertytype, @Param("apiparamvalue") String apiparamvalue);
}