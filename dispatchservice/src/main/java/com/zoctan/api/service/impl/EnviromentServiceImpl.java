package com.zoctan.api.service.impl;

import com.zoctan.api.mapper.EnviromentMapper;
import com.zoctan.api.entity.Enviroment;
import com.zoctan.api.service.EnviromentService;
import com.zoctan.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author Zoctan
* @date 2020/04/18
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class EnviromentServiceImpl extends AbstractService<Enviroment> implements EnviromentService {
@Resource
private EnviromentMapper enviromentMapper;

}
