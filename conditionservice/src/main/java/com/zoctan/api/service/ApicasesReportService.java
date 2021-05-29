package com.zoctan.api.service;

import com.zoctan.api.core.service.Service;
import com.zoctan.api.entity.ApicasesReport;

import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2020/10/16
*/
public interface ApicasesReportService extends Service<ApicasesReport> {
    /**
     * 按条件查询Api内容
     *
     * @param params 参数
     * @return 用户列表
     */
    List<ApicasesReport> findApicasereportWithName(final Map<String, Object> params);

    List<ApicasesReport> listallresult();

    void addcasereport(ApicasesReport ar);

}
