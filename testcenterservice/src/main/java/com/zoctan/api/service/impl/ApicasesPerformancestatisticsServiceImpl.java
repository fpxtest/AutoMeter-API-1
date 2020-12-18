package com.zoctan.api.service.impl;

import com.zoctan.api.mapper.ApicasesPerformancestatisticsMapper;
import com.zoctan.api.entity.ApicasesPerformancestatistics;
import com.zoctan.api.service.ApicasesPerformancestatisticsService;
import com.zoctan.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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
}
