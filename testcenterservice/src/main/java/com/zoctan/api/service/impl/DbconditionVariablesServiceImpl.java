package com.zoctan.api.service.impl;

import com.zoctan.api.mapper.DbconditionVariablesMapper;
import com.zoctan.api.entity.DbconditionVariables;
import com.zoctan.api.service.DbconditionVariablesService;
import com.zoctan.api.core.service.AbstractService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2022/03/25
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class DbconditionVariablesServiceImpl extends AbstractService<DbconditionVariables> implements DbconditionVariablesService {
@Resource
private DbconditionVariablesMapper dbconditionVariablesMapper;

    @Override
    public List<DbconditionVariables> getbyvariablesid(Map<String, Object> params) {
        return dbconditionVariablesMapper.getbyvariablesid(params);
    }

    @Override
    public void updatedbconditionvariables(DbconditionVariables params) {
        dbconditionVariablesMapper.updatedbconditionvariables(params);
    }

    @Override
    public int ifexist(Condition con) {
        return countByCondition(con);
    }

}
