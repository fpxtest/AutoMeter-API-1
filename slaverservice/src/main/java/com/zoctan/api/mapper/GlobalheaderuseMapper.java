package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.Globalheaderuse;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

public interface GlobalheaderuseMapper extends MyMapper<Globalheaderuse> {
    int ifexist(Condition condition);

    List<Globalheaderuse> findexistcaseglobalheader(final Map<String, Object> params);

    void saveconditionscase(@Param("casedataList")final List<Globalheaderuse> casedataList);

    void deletacases(@Param("casedataList") final List<Globalheaderuse> casedataList);

    List<Globalheaderuse> searchheaderbyepid(final Map<String, Object> params);

    List<Globalheaderuse> findnotexistcase(@Param("globalheaderid") long globalheaderid,@Param("deployunitid") long deployunitid);
}