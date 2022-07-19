package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.ConditionDb;
import com.zoctan.api.entity.ConditionDelay;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ConditionDelayMapper extends MyMapper<ConditionDelay> {
    List<ConditionDelay> findtestconditiondelayWithName(final Map<String, Object> params);

    List<ConditionDelay> GetCaseListByConditionID(@Param("conditionid") Long conditionid);

    void updateTestconditiondelay(ConditionDelay params);
}