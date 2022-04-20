package com.zoctan.api.service.impl;

import com.zoctan.api.mapper.PerformancereportsourceMapper;
import com.zoctan.api.entity.Performancereportsource;
import com.zoctan.api.service.PerformancereportsourceService;
import com.zoctan.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author SeasonFan
* @date 2022/04/19
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class PerformancereportsourceServiceImpl extends AbstractService<Performancereportsource> implements PerformancereportsourceService {
@Resource
private PerformancereportsourceMapper performancereportsourceMapper;

}
