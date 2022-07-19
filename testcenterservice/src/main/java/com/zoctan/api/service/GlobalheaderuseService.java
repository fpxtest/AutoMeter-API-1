package com.zoctan.api.service;

import com.zoctan.api.entity.Apicases;
import com.zoctan.api.entity.ApicasesDebugCondition;
import com.zoctan.api.entity.ExecuteplanParams;
import com.zoctan.api.entity.Globalheaderuse;
import com.zoctan.api.core.service.Service;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2022/07/12
*/
public interface GlobalheaderuseService extends Service<Globalheaderuse> {

    List<Globalheaderuse> findexistcaseglobalheader(final Map<String, Object> params);

    void saveconditionscase(final List<Globalheaderuse> casedataList);

    void deletacases(final List<Globalheaderuse> casedataList);

    List<Globalheaderuse> findnotexistcase(long globalheaderid,long deployunitid);

    List<Globalheaderuse> searchheaderbyepid(final Map<String, Object> params);

    int ifexist(Condition condition);
}
