package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.Performancereportsource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PerformancereportsourceMapper extends MyMapper<Performancereportsource> {
    List<Performancereportsource> findperformancereportsource(@Param("slaverid") Long slaverid);
    List<Performancereportsource> findperformancereportsourcebyids(@Param("caseid") long caseid,@Param("slaverid")long slaverid,@Param("planid")long planid,@Param("batchid")Long batchid);
    void updateperformancereportsourcedone(@Param("planid") Long planid,@Param("slaverid")Long slaverid,@Param("batchid")Long batchid,@Param("caseid")Long caseid);
}