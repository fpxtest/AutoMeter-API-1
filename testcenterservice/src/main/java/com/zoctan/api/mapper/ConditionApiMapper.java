package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.ConditionApi;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

public interface ConditionApiMapper extends MyMapper<ConditionApi> {
    List<ConditionApi> findtestconditionapiWithName(final Map<String, Object> params);

    void updateTestconditionapi(ConditionApi params);

    List<ConditionApi> getallTestconditionapi();

    void deletesubconditionbyconid(@Param("conditionid") Long conditionid);

}