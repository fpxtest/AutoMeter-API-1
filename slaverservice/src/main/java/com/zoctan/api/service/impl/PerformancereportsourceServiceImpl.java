package com.zoctan.api.service.impl;

import com.zoctan.api.mapper.PerformancereportsourceMapper;
import com.zoctan.api.entity.Performancereportsource;
import com.zoctan.api.service.PerformancereportsourceService;
import com.zoctan.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
* @author Season
* @date 2020/12/16
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class PerformancereportsourceServiceImpl extends AbstractService<Performancereportsource> implements PerformancereportsourceService {
@Resource
private PerformancereportsourceMapper performancereportsourceMapper;


    @Override
    public List<Performancereportsource> findperformancereportsource(Long slaverid) {
        return performancereportsourceMapper.findperformancereportsource(slaverid);
    }

    @Override
    public List<Performancereportsource> findperformancereportsourcebyids(long caseid, long slaverid, long planid, Long batchid) {
        return performancereportsourceMapper.findperformancereportsourcebyids(caseid, slaverid, planid, batchid);
    }

    @Override
    public void updateperformancereportsourcedone(Long planid, Long slaverid, Long batchid, Long caseid) {
        performancereportsourceMapper.updateperformancereportsourcedone(planid,slaverid,batchid,caseid);

    }
}
