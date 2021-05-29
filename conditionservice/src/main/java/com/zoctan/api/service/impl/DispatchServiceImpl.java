package com.zoctan.api.service.impl;

import com.zoctan.api.core.service.AbstractService;
import com.zoctan.api.entity.Dispatch;
import com.zoctan.api.mapper.DispatchMapper;
import com.zoctan.api.service.DispatchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author SeasonFan
* @date 2020/11/21
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class DispatchServiceImpl extends AbstractService<Dispatch> implements DispatchService {
@Resource
private DispatchMapper dispatchMapper;

}
