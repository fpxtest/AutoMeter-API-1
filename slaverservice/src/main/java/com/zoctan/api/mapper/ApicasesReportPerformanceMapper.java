package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.ApicasesReport;
import com.zoctan.api.entity.ApicasesReportPerformance;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ApicasesReportPerformanceMapper extends MyMapper<ApicasesReportPerformance> {
    List<ApicasesReportPerformance> findApicasereportWithName(final Map<String, Object> params);
    List<ApicasesReportPerformance> listallresult();
    void addcasereport(ApicasesReport ar);
    void adddynamiccaseperformancereport(long caseid, long testplanid, String batchname, long slaverid, String status, String respone, String assertvalue, long runtime, String expect, String errorinfo, Date create_time, Date lastmodify_time, String creator, String requestheader, String requestdatas, String url, String requestmethod, @Param("tableName") String tablename);

}