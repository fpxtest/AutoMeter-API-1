package com.zoctan.api.service;

import com.zoctan.api.entity.ApiParams;
import com.zoctan.api.entity.ExecuteplanParams;
import com.zoctan.api.core.service.Service;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2021/12/19
*/
public interface ExecuteplanParamsService extends Service<ExecuteplanParams> {
    void updateParams(ExecuteplanParams params);

    List<ExecuteplanParams> getParamsbyepid(final Map<String, Object> params);

    int ifexist(Condition condition);

    void removeplanparams(final long executeplanid);

}
