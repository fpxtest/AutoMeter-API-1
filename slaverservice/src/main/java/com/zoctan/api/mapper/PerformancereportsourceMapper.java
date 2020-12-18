package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.Performancereportsource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PerformancereportsourceMapper extends MyMapper<Performancereportsource> {
    List<Performancereportsource> findperformancereportsource(@Param("slaverid") Long slaverid);

    void updateperformancereportsourcedone(@Param("planid") Long planid,@Param("slaverid")Long slaverid,@Param("batchid")Long batchid,@Param("caseid")Long caseid);
}