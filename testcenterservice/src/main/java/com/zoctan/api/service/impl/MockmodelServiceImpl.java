package com.zoctan.api.service.impl;

import com.zoctan.api.mapper.MockmodelMapper;
import com.zoctan.api.entity.Mockmodel;
import com.zoctan.api.service.MockmodelService;
import com.zoctan.api.core.service.AbstractService;
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
public class MockmodelServiceImpl extends AbstractService<Mockmodel> implements MockmodelService {
@Resource
private MockmodelMapper mockmodelMapper;

    @Override
    public List<Mockmodel> findMockmodelWithName(Map<String, Object> params) {
        return mockmodelMapper.findMockmodelWithName(params);
    }

    @Override
    public void updateMockmodel(Mockmodel params) {
        mockmodelMapper.updateMockmodel(params);
    }

    @Override
    public int ifexist(Condition con) {
        return countByCondition(con);
    }
}
