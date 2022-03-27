package com.zoctan.api.service;

import com.zoctan.api.entity.ApicasesVariables;
import com.zoctan.api.entity.DbconditionVariables;
import com.zoctan.api.core.service.Service;
import com.zoctan.api.entity.Dbvariables;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2022/03/25
*/
public interface DbconditionVariablesService extends Service<DbconditionVariables> {
    List<DbconditionVariables> getbyvariablesid(final Map<String, Object> params);

    void updatedbconditionvariables(DbconditionVariables params);

    int ifexist(Condition condition);

}
