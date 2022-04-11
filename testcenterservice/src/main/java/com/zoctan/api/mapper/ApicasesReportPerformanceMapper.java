package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.ApicasesReportPerformance;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ApicasesReportPerformanceMapper extends MyMapper<ApicasesReportPerformance> {
    List<ApicasesReportPerformance> findApicasereportWithName(final Map<String, Object> params);
    List<ApicasesReportPerformance> listallresult();
    List<ApicasesReportPerformance> listallresultbyplanid(@Param("testplanid") long testplanid);

}