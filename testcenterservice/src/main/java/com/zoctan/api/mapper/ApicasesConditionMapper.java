package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.ApicasesCondition;

import java.util.List;
import java.util.Map;

public interface ApicasesConditionMapper extends MyMapper<ApicasesCondition> {
    List<ApicasesCondition> findconditionWithName(final Map<String, Object> params);
}