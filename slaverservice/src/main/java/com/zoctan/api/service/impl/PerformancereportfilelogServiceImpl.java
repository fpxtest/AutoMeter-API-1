package com.zoctan.api.service.impl;

import com.zoctan.api.mapper.PerformancereportfilelogMapper;
import com.zoctan.api.entity.Performancereportfilelog;
import com.zoctan.api.service.PerformancereportfilelogService;
import com.zoctan.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
* @author Season
* @date 2022/04/15
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class PerformancereportfilelogServiceImpl extends AbstractService<Performancereportfilelog> implements PerformancereportfilelogService {
@Resource
private PerformancereportfilelogMapper performancereportfilelogMapper;


    @Override
    public List<Performancereportfilelog> findrecentperformancereportfile(long slaverid) {
        return performancereportfilelogMapper.findrecentperformancereportfile(slaverid);
    }
}
