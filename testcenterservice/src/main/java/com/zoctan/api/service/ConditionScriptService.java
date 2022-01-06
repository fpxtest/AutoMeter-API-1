package com.zoctan.api.service;

import com.zoctan.api.entity.ConditionApi;
import com.zoctan.api.entity.ConditionScript;
import com.zoctan.api.core.service.Service;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2021/11/20
*/
public interface ConditionScriptService extends Service<ConditionScript> {
    List<ConditionScript> findtestconditionapiWithName(final Map<String, Object> params);

    void updateTestconditionapi(ConditionScript params);
    void deletesubconditionbyconid(Long conditionid);

    int ifexist(Condition condition);
}
