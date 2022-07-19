package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.Testcondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TestconditionMapper extends MyMapper<Testcondition> {
    List<Testcondition> findtestconditionWithName(final Map<String, Object> params);

    void updateTestcondition(Testcondition params);

    List<Testcondition> getallTestcondition();

    List<Testcondition> GetConditionByPlanIDAndConditionType(@Param("objectid")Long objectid, @Param("conditiontype")String conditiontype, @Param("objecttype")String objecttype);
}