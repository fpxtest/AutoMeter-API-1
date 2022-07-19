package com.zoctan.api.service;

import com.zoctan.api.entity.Globalheader;
import com.zoctan.api.core.service.Service;
import com.zoctan.api.entity.Machine;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2022/07/09
*/
public interface GlobalheaderService extends Service<Globalheader> {
    int ifexist(Condition condition);
    List<Globalheader> findGlobalheaderWithName(final Map<String, Object> params);
    void updateGlobalheader(Globalheader params);
}
