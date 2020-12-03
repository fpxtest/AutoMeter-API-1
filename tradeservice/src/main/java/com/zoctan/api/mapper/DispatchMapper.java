package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.Dispatch;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DispatchMapper extends MyMapper<Dispatch> {

    Integer findbusythreadnums(@Param("status") String status);

    List<Dispatch> getcasebyslaverid(@Param("slaverid") Long slaverid , @Param("status") String status, @Param("maxthread")Long maxthread);

    void updatedispatchstatus(@Param("status") String status,@Param("slaverid")Long slaverid,@Param("execplanid")Long execplanid,@Param("batchid")Long batchid,@Param("testcaseid")Long testcaseid);
}