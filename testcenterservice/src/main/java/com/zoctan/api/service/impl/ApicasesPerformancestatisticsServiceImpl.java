package com.zoctan.api.service.impl;

import com.zoctan.api.mapper.ApicasesPerformancestatisticsMapper;
import com.zoctan.api.entity.ApicasesPerformancestatistics;
import com.zoctan.api.service.ApicasesPerformancestatisticsService;
import com.zoctan.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2020/12/07
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class ApicasesPerformancestatisticsServiceImpl extends AbstractService<ApicasesPerformancestatistics> implements ApicasesPerformancestatisticsService {
@Resource
private ApicasesPerformancestatisticsMapper apicasesPerformancestatisticsMapper;

    @Override
    public List<ApicasesPerformancestatistics> listallresult() {
        return apicasesPerformancestatisticsMapper.listallresult();
    }

    @Override
    public List<ApicasesPerformancestatistics> findApicasereportWithName(Map<String, Object> params) {
        return apicasesPerformancestatisticsMapper.findApicasereportWithName(params);
    }

    @Override
    public List<ApicasesPerformancestatistics> getresultbyidandname(long testplanid, String batchname,long projectid) {
        return apicasesPerformancestatisticsMapper.getresultbyidandname(testplanid, batchname,projectid);
    }
}
