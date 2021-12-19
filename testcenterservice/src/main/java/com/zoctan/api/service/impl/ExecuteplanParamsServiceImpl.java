package com.zoctan.api.service.impl;

import com.zoctan.api.mapper.ExecuteplanParamsMapper;
import com.zoctan.api.entity.ExecuteplanParams;
import com.zoctan.api.service.ExecuteplanParamsService;
import com.zoctan.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author SeasonFan
* @date 2021/12/19
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class ExecuteplanParamsServiceImpl extends AbstractService<ExecuteplanParams> implements ExecuteplanParamsService {
@Resource
private ExecuteplanParamsMapper executeplanParamsMapper;

}
