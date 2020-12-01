package com.zoctan.api.service;

import com.zoctan.api.core.service.Service;
import com.zoctan.api.entity.Executeplanbatch;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

/**
* @author SeasonFan
* @date 2020/10/22
*/
public interface ExecuteplanbatchService extends Service<Executeplanbatch> {
    int ifexist(Condition condition);

    List<Executeplanbatch> getbatchbyplan(Long executeplanid);
}
