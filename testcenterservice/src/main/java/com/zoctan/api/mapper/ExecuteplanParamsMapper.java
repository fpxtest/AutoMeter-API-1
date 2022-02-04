package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.ExecuteplanParams;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

public interface ExecuteplanParamsMapper extends MyMapper<ExecuteplanParams> {

    void updateParams(ExecuteplanParams params);

    List<ExecuteplanParams> getParamsbyepid(final Map<String, Object> params);

    int ifexist(Condition condition);

    void removeplanparams(@Param("executeplanid") long executeplanid);

}