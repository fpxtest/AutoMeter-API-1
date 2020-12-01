package com.zoctan.api.service;

import com.zoctan.api.core.service.Service;
import com.zoctan.api.entity.ApicasesCondition;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2020/11/03
*/
public interface ApicasesConditionService extends Service<ApicasesCondition> {
    int ifexist(Condition condition);

    List<ApicasesCondition> findconditionWithName(final Map<String, Object> params);

}
