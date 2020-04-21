package com.zoctan.api.service.impl;

import com.zoctan.api.mapper.TestMapper;
import com.zoctan.api.entity.Test;
import com.zoctan.api.service.TestService;
import com.zoctan.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author Zoctan
* @date 2020/04/14
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class TestServiceImpl extends AbstractService<Test> implements TestService {
@Resource
private TestMapper testMapper;

}
