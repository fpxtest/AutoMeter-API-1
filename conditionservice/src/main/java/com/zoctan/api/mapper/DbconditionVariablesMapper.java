package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.DbconditionVariables;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DbconditionVariablesMapper extends MyMapper<DbconditionVariables> {
    List<DbconditionVariables> getbyvariablesid(final Map<String, Object> params);

    List<DbconditionVariables> getbyconditionid(@Param("dbconditionid") long dbconditionid);

    void updatedbconditionvariables(DbconditionVariables params);

}