package com.zoctan.api.service;

import com.zoctan.api.core.service.Service;
import com.zoctan.api.entity.GlobalheaderParams;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2022/07/09
*/
public interface GlobalheaderParamsService extends Service<GlobalheaderParams> {
    int ifexist(Condition condition);
    void updateGlobalheaderParams(GlobalheaderParams params);
    List<GlobalheaderParams> findGlobalheaderParamsWithName(final Map<String, Object> params);

}
