package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.Dispatch;
import com.zoctan.api.entity.Slaver;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DispatchMapper extends MyMapper<Dispatch> {

    void insertBatchDispatch(@Param("dispatchList") final List<Dispatch> dispatchList);

    Integer findbusythreadnums(@Param("slaverid") Long slaverid, @Param("status") String status);

    List<Dispatch> getcasebyslaverid(@Param("slaverid") Long slaverid, @Param("status") String status, @Param("plantype") String plantype, @Param("maxthread") Long maxthread);

    List<Dispatch> getcasebyrunmode(@Param("status") String status, @Param("usetype") String usetype, @Param("runmode") String runmode);

    void updatedispatchstatus(@Param("status") String status, @Param("slaverid") Long slaverid, @Param("execplanid") Long execplanid, @Param("batchid") Long batchid, @Param("testcaseid") Long testcaseid);

    Integer findbusyslavernums(@Param("slaverlist") final List<Slaver> slaverlist, @Param("status") String status);
}
