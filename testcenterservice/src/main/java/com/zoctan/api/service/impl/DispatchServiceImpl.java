package com.zoctan.api.service.impl;

import com.zoctan.api.mapper.DispatchMapper;
import com.zoctan.api.entity.Dispatch;
import com.zoctan.api.service.DispatchService;
import com.zoctan.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2020/11/21
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class DispatchServiceImpl extends AbstractService<Dispatch> implements DispatchService {
@Resource
private DispatchMapper dispatchMapper;

    @Override
    public List<Dispatch> findDispatchWithName(Map<String, Object> params) {
        return dispatchMapper.findDispatchWithName(params);
    }
}
