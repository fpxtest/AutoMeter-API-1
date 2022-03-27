package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.DbconditionVariables;

import java.util.List;
import java.util.Map;

public interface DbconditionVariablesMapper extends MyMapper<DbconditionVariables> {
    List<DbconditionVariables> getbyvariablesid(final Map<String, Object> params);
    void updatedbconditionvariables(DbconditionVariables params);

}