package com.zoctan.api.service.impl;

import com.zoctan.api.mapper.ApiMapper;
import com.zoctan.api.entity.Api;
import com.zoctan.api.service.ApiService;
import com.zoctan.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author Zoctan
* @date 2020/04/17
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class ApiServiceImpl extends AbstractService<Api> implements ApiService {
@Resource
private ApiMapper apiMapper;

}
