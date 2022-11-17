package com.zoctan.api.service;

import com.zoctan.api.entity.ApicasesAssert;
import com.zoctan.api.entity.ExecuteplanTestcase;
import com.zoctan.api.entity.ProjectAccount;
import com.zoctan.api.core.service.Service;

import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2022/11/16
*/
public interface ProjectAccountService extends Service<ProjectAccount> {
    List<ProjectAccount> findaccountbyprojectid(final Map<String, Object> params);
    void saveprojectaccount(final List<ProjectAccount> projectaccount);

}
