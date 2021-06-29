package com.zoctan.api.service.impl;

import com.zoctan.api.mapper.RuntimeparamsrecordMapper;
import com.zoctan.api.entity.Runtimeparamsrecord;
import com.zoctan.api.service.RuntimeparamsrecordService;
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
public class RuntimeparamsrecordServiceImpl extends AbstractService<Runtimeparamsrecord> implements RuntimeparamsrecordService {
@Resource
private RuntimeparamsrecordMapper runtimeparamsrecordMapper;

}
