package com.zoctan.api.service.impl;

import com.zoctan.api.mapper.TesterMapper;
import com.zoctan.api.entity.Tester;
import com.zoctan.api.service.TesterService;
import com.zoctan.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author Zoctan
* @date 2020/04/22
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class TesterServiceImpl extends AbstractService<Tester> implements TesterService {
@Resource
private TesterMapper testerMapper;

    @Override
    public List<Tester> findtesterName(String params) {
        return this.testerMapper.findtesterName(params);
    }

    @Override
    public List<Tester> findTesterWithName(Map<String, Object> params) {
        return this.testerMapper.findTesterWithName(params);
    }

    @Override
    public void updateTester(Tester params) {
        testerMapper.updateTester(params);

    }
}
