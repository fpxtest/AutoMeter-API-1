package com.zoctan.api.service;

import com.zoctan.api.core.service.Service;
import com.zoctan.api.entity.ApicasesPerformancestatistics;

import java.util.List;

/**
* @author SeasonFan
* @date 2020/12/07
*/
public interface ApicasesPerformancestatisticsService extends Service<ApicasesPerformancestatistics> {
    List<ApicasesPerformancestatistics> listallresult();


}
