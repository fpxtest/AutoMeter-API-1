package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.ApicasesReport;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ApicasesReportMapper extends MyMapper<ApicasesReport> {
    List<ApicasesReport> findApicasereportWithName(final Map<String, Object> params);

    List<ApicasesReport> findApicasereportWithNameandStatus(@Param("testplanid") long planid, @Param("status") String status, @Param("batchname")String batchname);


    List<ApicasesReport> listallresult();

    Long getApicasetotalsWithName(final Map<String, Object> params);

    Long getApicasenumbystatus(final Map<String, Object> params);

    Long getApicasecosttimes(final Map<String, Object> params);
    List<ApicasesReport> getreportbyplanandbatchstatus(@Param("testplanid") long planid, @Param("status") String status, @Param("batchname")String batchname);

}