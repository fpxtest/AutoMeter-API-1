package com.zoctan.api.service.impl;

import com.zoctan.api.mapper.ConditionApiMapper;
import com.zoctan.api.entity.ConditionApi;
import com.zoctan.api.service.ConditionApiService;
import com.zoctan.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author SeasonFan
* @date 2021/05/30
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class ConditionApiServiceImpl extends AbstractService<ConditionApi> implements ConditionApiService {
@Resource
private ConditionApiMapper conditionApiMapper;

}
