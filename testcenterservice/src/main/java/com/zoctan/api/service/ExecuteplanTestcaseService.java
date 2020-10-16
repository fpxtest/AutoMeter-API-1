package com.zoctan.api.service;

import com.zoctan.api.core.service.Service;
import com.zoctan.api.entity.ExecuteplanTestcase;

import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2020/10/12
*/
public interface ExecuteplanTestcaseService extends Service<ExecuteplanTestcase> {

    /**
     *
     *
     * @param testcase 用例参数数据
     */
    void savetestplancase(final List<ExecuteplanTestcase> testcase);

    List<ExecuteplanTestcase> findcasebydeployandapi(final Map<String, Object> params);

    void removeexecuteplantestcase(final List<ExecuteplanTestcase> testcase);


}
