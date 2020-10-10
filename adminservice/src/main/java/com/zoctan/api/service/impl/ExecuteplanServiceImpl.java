package com.zoctan.api.service.impl;

import com.zoctan.api.mapper.ExecuteplanMapper;
import com.zoctan.api.entity.Executeplan;
import com.zoctan.api.service.ExecuteplanService;
import com.zoctan.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2020/09/27
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class ExecuteplanServiceImpl extends AbstractService<Executeplan> implements ExecuteplanService {
@Resource
private ExecuteplanMapper executeplanMapper;

    @Override
    public List<Executeplan> findexplanWithName(Map<String, Object> params) {
        return this.executeplanMapper.findexplanWithName(params);
    }

    @Override
    public void updateexecuteplanname(Executeplan params) {
        this.executeplanMapper.updateexecuteplanname(params);
    }
}
