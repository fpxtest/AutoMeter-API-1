package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.ConditionApi;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

public interface ConditionApiMapper extends MyMapper<ConditionApi> {
    List<ConditionApi> findtestconditionapiWithName(final Map<String, Object> params);

    void updateTestconditionapi(ConditionApi params);
    void updatecasename(@Param("caseid") long caseid , @Param("casename")String newcasename);

    List<ConditionApi> getallTestconditionapi();

    void deletesubconditionbyconid(@Param("conditionid") Long conditionid);

}