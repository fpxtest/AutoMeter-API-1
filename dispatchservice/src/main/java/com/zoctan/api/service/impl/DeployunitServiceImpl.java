package com.zoctan.api.service.impl;

import com.zoctan.api.mapper.DeployunitMapper;
import com.zoctan.api.entity.Deployunit;
import com.zoctan.api.service.DeployunitService;
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
public class DeployunitServiceImpl extends AbstractService<Deployunit> implements DeployunitService {
@Resource
private DeployunitMapper deployunitMapper;

    @Override
    public Deployunit findDeployNameValueWithCode(String params) {
        return deployunitMapper.findDeployNameValueWithCode(params);
    }
}
