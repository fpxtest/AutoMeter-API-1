package com.zoctan.api.service;

import com.zoctan.api.core.service.Service;
import com.zoctan.api.entity.ApicasesReportPerformance;

import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2020/12/14
*/
public interface ApicasesReportPerformanceService extends Service<ApicasesReportPerformance> {

    /**
     * 按条件查询Api内容
     *
     * @param params 参数
     * @return 用户列表
     */
    List<ApicasesReportPerformance> findApicasereportWithName(final Map<String, Object> params);
    List<ApicasesReportPerformance> finddynamicresult(long planid,String batchname,String tableName,long projectid);
    List<ApicasesReportPerformance> finddynamicresultbystatus(long planid,String batchname,String tableName,String status);
    List<ApicasesReportPerformance> listallresult();

}
