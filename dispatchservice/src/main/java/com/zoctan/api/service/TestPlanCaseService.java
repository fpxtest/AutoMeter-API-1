package com.zoctan.api.service;

import com.zoctan.api.core.service.Service;
import com.zoctan.api.entity.ExecuteplanTestcase;

/**
* @author Zoctan
* @date 2020/04/17
*/
public interface TestPlanCaseService extends Service<ExecuteplanTestcase> {

    void executeplancase(long planid,long caseid,String deployname,String jmeterpath,String jmxpath,String jmxcasename,String batchname);

}
