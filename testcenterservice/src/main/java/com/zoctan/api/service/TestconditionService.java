package com.zoctan.api.service;

import com.zoctan.api.core.service.Service;
import com.zoctan.api.entity.Testcondition;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2021/05/31
*/
public interface TestconditionService extends Service<Testcondition> {
    List<Testcondition> findtestconditionWithName(final Map<String, Object> params);

    void updateTestcondition(Testcondition params);
    void updatecasename(long caseid,String objecttype ,String newcasename);

    int ifexist(Condition condition);

    List<Testcondition> getallTestcondition();
    List<Testcondition> getallTestconditionByType(String objecttype);
    List<Testcondition> gettestconditionforscripyanddelay(String objecttype);


    List<Testcondition> GetConditionByPlanIDAndConditionType(Long objectid,String conditiontype,String objecttype);


}
