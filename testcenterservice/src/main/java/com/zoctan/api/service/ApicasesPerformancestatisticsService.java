package com.zoctan.api.service;

import com.zoctan.api.core.service.Service;
import com.zoctan.api.entity.ApicasesPerformancestatistics;

import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2020/12/07
*/
public interface ApicasesPerformancestatisticsService extends Service<ApicasesPerformancestatistics> {
    List<ApicasesPerformancestatistics> listallresult();

    List<ApicasesPerformancestatistics> findApicasereportWithName(final Map<String, Object> params);

    List<ApicasesPerformancestatistics> getresultbyidandname(long testplanid,String batchname,long projectid);



}
