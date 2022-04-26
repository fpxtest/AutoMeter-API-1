package com.zoctan.api.service;

import com.zoctan.api.entity.Apicases;
import com.zoctan.api.entity.ApicasesCondition;
import com.zoctan.api.entity.ApicasesDebugCondition;
import com.zoctan.api.core.service.Service;
import com.zoctan.api.entity.ExecuteplanTestcase;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2022/04/06
*/
public interface ApicasesDebugConditionService extends Service<ApicasesDebugCondition> {

    List<ApicasesDebugCondition> finddebugcondition(final Map<String, Object> params);

    void saveconditionscase(final List<ApicasesDebugCondition> casedataList);

    void deletacases(final List<ApicasesDebugCondition> casedataList);

    List<Apicases> findnotexistcase(long conditionid,  long deployunitid);

}
