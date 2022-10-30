package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.ApicasesPerformancestatistics;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ApicasesPerformancestatisticsMapper extends MyMapper<ApicasesPerformancestatistics> {
    List<ApicasesPerformancestatistics> listallresult();

    List<ApicasesPerformancestatistics> findApicasereportWithName(final Map<String, Object> params);

    List<ApicasesPerformancestatistics> getresultbyidandname(@Param("testplanid") long testplanid, @Param("batchname") String batchname,@Param("projectid") long projectid);

}