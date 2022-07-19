package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.Enviroment;
import com.zoctan.api.entity.Variables;

import java.util.List;
import java.util.Map;

public interface VariablesMapper extends MyMapper<Variables> {
    List<Variables> findvariablesWithName(final Map<String, Object> params);

    void updatevariables(Variables params);

}