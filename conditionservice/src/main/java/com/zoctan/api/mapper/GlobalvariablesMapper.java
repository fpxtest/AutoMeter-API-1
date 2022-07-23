package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.Globalvariables;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

public interface GlobalvariablesMapper extends MyMapper<Globalvariables> {
    int ifexist(Condition condition);
    List<Globalvariables> findGlobalvariablesWithName(final Map<String, Object> params);
    void updateGlobalvariables(Globalvariables params);
}