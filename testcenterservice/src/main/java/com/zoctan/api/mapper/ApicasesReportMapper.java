package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.ApicasesReport;

import java.util.List;
import java.util.Map;

public interface ApicasesReportMapper extends MyMapper<ApicasesReport> {

    /**
     * 按发布单元名或者协议名获取用例报告
     *
     * @param params 参数
     * @return 用例报告列表
     */
    List<ApicasesReport> findApiCasereportWithName(final Map<String, Object> params);

}