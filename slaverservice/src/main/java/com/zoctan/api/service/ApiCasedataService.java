package com.zoctan.api.service;

import com.zoctan.api.core.service.Service;
import com.zoctan.api.dto.Casedata;
import com.zoctan.api.entity.ApiCasedata;

import java.util.List;
import java.util.Map;

/**
* @author Zoctan
* @date 2020/09/21
*/
public interface ApiCasedataService extends Service<ApiCasedata> {
    /**
     *
     *
     * @param casedata 用例参数数据
     */
    void save(Casedata casedata);

    List<ApiCasedata> getparamvaluebycaseidandtype(final Map<String, Object> params);

    List<ApiCasedata> getcasedatabycaseid(Long caseid);

    void deletcasedatabyid(Long caseid);

}
