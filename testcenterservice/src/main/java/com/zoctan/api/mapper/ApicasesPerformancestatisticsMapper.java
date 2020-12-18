package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.ApicasesPerformancestatistics;

import java.util.List;

public interface ApicasesPerformancestatisticsMapper extends MyMapper<ApicasesPerformancestatistics> {
    List<ApicasesPerformancestatistics> listallresult();

}