package com.zoctan.api.service;

import com.zoctan.api.core.service.Service;
import com.zoctan.api.entity.Dispatch;

import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2020/11/21
*/
public interface DispatchService extends Service<Dispatch> {
    List<Dispatch> findDispatchWithName(final Map<String, Object> params);


}
