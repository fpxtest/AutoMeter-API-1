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
     * 按条件查询Api用例报告内容
     *
     * @param params 参数
     * @return 用例报告列表
     */
    List<ApicasesReport> findApiCasereportWithName(final Map<String, Object> params);

}
