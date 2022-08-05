package com.zoctan.api.service.impl;

import com.zoctan.api.core.service.AbstractService;
import com.zoctan.api.entity.Mockapi;
import com.zoctan.api.mapper.MockapiMapper;
import com.zoctan.api.service.MockapiService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2022/08/03
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class MockapiServiceImpl extends AbstractService<Mockapi> implements MockapiService {
@Resource
private MockapiMapper mockapiMapper;


    @Override
    public List<Mockapi> findMockapiWithName(Map<String, Object> params) {
        return mockapiMapper.findMockapiWithName(params);
    }

    @Override
    public void updateMockapi(Mockapi params) {
        mockapiMapper.updateMockapi(params);
    }

    @Override
    public int ifexist(Condition con) {
        return countByCondition(con);
    }
}
