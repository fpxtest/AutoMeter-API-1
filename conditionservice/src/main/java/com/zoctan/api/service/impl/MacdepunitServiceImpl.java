package com.zoctan.api.service.impl;

import com.zoctan.api.core.service.AbstractService;
import com.zoctan.api.entity.Macdepunit;
import com.zoctan.api.mapper.MacdepunitMapper;
import com.zoctan.api.service.MacdepunitService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author Zoctan
* @date 2020/05/21
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class MacdepunitServiceImpl extends AbstractService<Macdepunit> implements MacdepunitService {
@Resource
private MacdepunitMapper macdepunitMapper;

    @Override
    public List<Macdepunit> findMacAndDepWithName(Map<String, Object> params) {
        return this.macdepunitMapper.findMacAndDepWithName(params);
    }

    @Override
    public void updateMacAndDep(Macdepunit params) {
        macdepunitMapper.updateMacAndDep(params);
    }

    @Override
    public int ifexist(Condition condition) {
        return countByCondition(condition);
    }

    @Override
    public Integer findmachinenumbyenvidanddeployid(long envid, long depunitid) {
        return macdepunitMapper.findmachinenumbyenvidanddeployid(envid,depunitid);
    }

    @Override
    public Macdepunit getmacdepbyenvidanddepid(long envid, long depunitid) {
        return macdepunitMapper.getmacdepbyenvidanddepid(envid,depunitid);
    }

    @Override
    public Macdepunit getmacdepbyenvidandassmbleid(long envid, long assembleid) {
        return macdepunitMapper.getmacdepbyenvidandassmbleid(envid,assembleid);
    }
}
