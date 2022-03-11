package com.zoctan.api.service.impl;

import com.zoctan.api.entity.Enviroment;
import com.zoctan.api.mapper.VariablesMapper;
import com.zoctan.api.entity.Variables;
import com.zoctan.api.service.VariablesService;
import com.zoctan.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2022/02/15
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class VariablesServiceImpl extends AbstractService<Variables> implements VariablesService {
@Resource
private VariablesMapper variablesMapper;


    @Override
    public List<Variables> findvariablesWithName(Map<String, Object> params) {
        return variablesMapper.findvariablesWithName(params);
    }

    @Override
    public void updatevariables(Variables params) {
        variablesMapper.updatevariables(params);
    }

    @Override
    public int ifexist(Condition con) {
        return countByCondition(con);
    }
}
