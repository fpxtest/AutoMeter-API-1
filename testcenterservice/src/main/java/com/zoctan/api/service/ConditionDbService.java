package com.zoctan.api.service;

import com.zoctan.api.core.service.Service;
import com.zoctan.api.entity.ConditionDb;

import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2021/05/30
*/
public interface ConditionDbService extends Service<ConditionDb> {

    List<ConditionDb> finddbconditionWithName(final Map<String, Object> params);


}
