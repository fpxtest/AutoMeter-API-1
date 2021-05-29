package com.zoctan.api.service.impl;

import com.zoctan.api.mapper.EnvmachineMapper;
import com.zoctan.api.entity.Envmachine;
import com.zoctan.api.service.EnvmachineService;
import com.zoctan.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author Zoctan
* @date 2020/04/20
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class EnvmachineServiceImpl extends AbstractService<Envmachine> implements EnvmachineService {
@Resource
private EnvmachineMapper envmachineMapper;

}
