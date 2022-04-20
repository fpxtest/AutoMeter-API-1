package com.zoctan.api.service;

import com.zoctan.api.core.service.Service;
import com.zoctan.api.entity.Performancereportsource;

import java.util.List;

/**
* @author Season
* @date 2020/12/16
*/
public interface PerformancereportsourceService extends Service<Performancereportsource> {

    List<Performancereportsource> findperformancereportsource(Long slaverid);

    List<Performancereportsource> findperformancereportsourcebyids(long caseid,long slaverid,long planid,Long batchid);

    void updateperformancereportsourcedone(Long planid,Long slaverid,Long batchid,Long caseid);
}
