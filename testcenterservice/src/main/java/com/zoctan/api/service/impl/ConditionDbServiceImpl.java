package com.zoctan.api.service.impl;

import com.zoctan.api.entity.ConditionApi;
import com.zoctan.api.mapper.ConditionDbMapper;
import com.zoctan.api.entity.ConditionDb;
import com.zoctan.api.service.ConditionDbService;
import com.zoctan.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2021/05/30
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class ConditionDbServiceImpl extends AbstractService<ConditionDb> implements ConditionDbService {
@Resource
private ConditionDbMapper conditionDbMapper;

    @Override
    public List<ConditionDb> finddbconditionWithName(Map<String, Object> params) {
        return conditionDbMapper.finddbconditionWithName(params);
    }

    @Override
    public void updateTestconditiondb(ConditionDb params) {
        conditionDbMapper.updateTestconditiondb(params);
    }

    @Override
    public void deletesubconditionbyconid(Long conditionid) {
        conditionDbMapper.deletesubconditionbyconid(conditionid);
    }

    @Override
    public int ifexist(Condition condition) {
        return countByCondition(condition);
    }
}
