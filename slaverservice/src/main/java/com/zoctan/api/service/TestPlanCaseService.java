package com.zoctan.api.service;

import com.zoctan.api.core.service.Service;
import com.zoctan.api.dto.TestplanCase;

/**
* @author Zoctan
* @date 2020/04/17
*/
public interface TestPlanCaseService extends Service<TestplanCase> {

    void ExecuteHttpPerformancePlanCase(String CaseType,long SlaverId,long BatchId,long PlanId,long CaseId,Long Thread,Long Loop,String DeployName,String JmeterPath,String JmxPath,String JmxCaseName,String BatchName,String JmeterPerformanceReportPath,String MysqlUrl,String MysqlUserName,String MysqlPassword);

    void ExecuteHttpPlanFunctionCase(Long Slaverid, String JmeterPath,String JmxPath,String DispatchIds,String MysqlUrl,String MysqlUsername,String MysqlPassword,int JmeterLogFileNum);



}
