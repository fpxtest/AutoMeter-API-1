package com.zoctan.api.service.impl;

import com.zoctan.api.mapper.SlaverMapper;
import com.zoctan.api.entity.Slaver;
import com.zoctan.api.service.SlaverService;
import com.zoctan.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2020/09/26
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class SlaverServiceImpl extends AbstractService<Slaver> implements SlaverService {
@Resource
private SlaverMapper slaverMapper;

    @Override
    public List<Slaver> findslaverWithName(Map<String, Object> params) {
        return this.slaverMapper.findslaverWithName(params);

    }

    @Override
    public void updateSlaver(Slaver params) {
        this.slaverMapper.updateSlaver(params);
    }

    @Override
    public List<Slaver> findslaveralive(String stype, String status) {
        return slaverMapper.findslaveralive(stype, status);
    }

    @Override
    public Integer getslavernum() {
        return slaverMapper.getslavernum();
    }
}
