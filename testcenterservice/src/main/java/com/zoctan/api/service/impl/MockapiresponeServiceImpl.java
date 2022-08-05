package com.zoctan.api.service.impl;

import com.zoctan.api.mapper.MockapiresponeMapper;
import com.zoctan.api.entity.Mockapirespone;
import com.zoctan.api.service.MockapiresponeService;
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
public class MockapiresponeServiceImpl extends AbstractService<Mockapirespone> implements MockapiresponeService {
@Resource
private MockapiresponeMapper mockapiresponeMapper;


    @Override
    public List<Mockapirespone> findMockapiresponeWithName(Map<String, Object> params) {
        return mockapiresponeMapper.findMockapiresponeWithName(params);
    }

    @Override
    public void updateMockapirespone(Mockapirespone params) {
        mockapiresponeMapper.updateMockapirespone(params);
    }

    @Override
    public void enableapirespone(long apiid,String status ) {
        mockapiresponeMapper.enableapirespone(apiid,status);
    }

    @Override
    public void enablerespone(long id,String status ) {
        mockapiresponeMapper.enablerespone(id,status);
    }

    @Override
    public int ifexist(Condition con) {
        return countByCondition(con);
    }

}
