package com.zoctan.api.service;

import com.zoctan.api.core.service.Service;
import com.zoctan.api.entity.Executeplan;

/**
* @author SeasonFan
* @date 2020/09/27
*/
public interface ExecuteplanService extends Service<Executeplan> {


    void updatetestplanstatus(Long id, String status);


}
