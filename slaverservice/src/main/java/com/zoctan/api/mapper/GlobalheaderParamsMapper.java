package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.GlobalheaderParams;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

public interface GlobalheaderParamsMapper extends MyMapper<GlobalheaderParams> {
    int ifexist(Condition condition);
    void updateGlobalheaderParams(GlobalheaderParams params);
    List<GlobalheaderParams> findGlobalheaderParamsWithName(final Map<String, Object> params);
}