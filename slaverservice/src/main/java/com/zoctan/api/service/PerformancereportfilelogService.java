package com.zoctan.api.service;

import com.zoctan.api.entity.Performancereportfilelog;
import com.zoctan.api.core.service.Service;

import java.util.List;

/**
* @author Season
* @date 2022/04/15
*/
public interface PerformancereportfilelogService extends Service<Performancereportfilelog> {

    List<Performancereportfilelog> findrecentperformancereportfile(long slaverid);

}
