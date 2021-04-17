package com.zoctan.api.service.impl;

import com.zoctan.api.mapper.StaticsPlanandcasesMapper;
import com.zoctan.api.entity.StaticsPlanandcases;
import com.zoctan.api.service.StaticsPlanandcasesService;
import com.zoctan.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
* @author SeasonFan
* @date 2021/04/14
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class StaticsPlanandcasesServiceImpl extends AbstractService<StaticsPlanandcases> implements StaticsPlanandcasesService {
@Resource
private StaticsPlanandcasesMapper staticsPlanandcasesMapper;

    @Override
    public List<StaticsPlanandcases> getplanstatics(String StaticDate) {
        return staticsPlanandcasesMapper.getplanstatics(StaticDate);
    }
}
