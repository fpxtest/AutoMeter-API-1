package com.zoctan.api.service;

import com.zoctan.api.entity.TestconditionReport;
import com.zoctan.api.core.service.Service;
import com.zoctan.api.entity.Testvariables;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2021/07/17
*/
public interface TestconditionReportService extends Service<TestconditionReport> {

    List<TestconditionReport> findTestconditionReportWithName(final Map<String, Object> params);
    List<TestconditionReport> findconditionreport(long planid,String batchname);


}
