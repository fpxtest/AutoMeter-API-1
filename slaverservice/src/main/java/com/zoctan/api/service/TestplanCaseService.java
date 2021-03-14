package com.zoctan.api.service;

import com.zoctan.api.core.service.Service;
import com.zoctan.api.entity.ExecuteplanTestcase;

/**
* @author Zoctan
* @date 2020/04/17
*/
public interface TestPlanCaseService extends Service<ExecuteplanTestcase> {

    void executeplancase(String casetype,long slaverid,long batchid,long planid,long caseid,Long thread,Long loop,String deployname,String jmeterpath,String jmxpath,String jmxcasename,String batchname,String jmeterperformancereportpath,String mysqlurl,String mysqlusername,String mysqlpassword);

}
