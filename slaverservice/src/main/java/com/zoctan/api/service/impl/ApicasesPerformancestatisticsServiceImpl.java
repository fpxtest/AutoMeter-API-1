package com.zoctan.api.service.impl;

import com.zoctan.api.mapper.ApicasesPerformancestatisticsMapper;
import com.zoctan.api.entity.ApicasesPerformancestatistics;
import com.zoctan.api.service.ApicasesPerformancestatisticsService;
import com.zoctan.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author Season
* @date 2020/12/17
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class ApicasesPerformancestatisticsServiceImpl extends AbstractService<ApicasesPerformancestatistics> implements ApicasesPerformancestatisticsService {
@Resource
private ApicasesPerformancestatisticsMapper apicasesPerformancestatisticsMapper;

}
