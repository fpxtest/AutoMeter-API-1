package com.zoctan.api.service;

import com.zoctan.api.core.service.Service;
import com.zoctan.api.entity.Variables;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2022/02/15
*/
public interface VariablesService extends Service<Variables> {

    List<Variables> findvariablesWithName(final Map<String, Object> params);

    void updatevariables(Variables params);
    int ifexist(Condition condition);

}
