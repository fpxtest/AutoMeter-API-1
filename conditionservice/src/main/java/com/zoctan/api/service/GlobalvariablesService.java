package com.zoctan.api.service;

import com.zoctan.api.core.service.Service;
import com.zoctan.api.entity.Globalvariables;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2022/07/11
*/
public interface GlobalvariablesService extends Service<Globalvariables> {
    int ifexist(Condition condition);
    List<Globalvariables> findGlobalvariablesWithName(final Map<String, Object> params);
    void updateGlobalvariables(Globalvariables params);
}
