package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.Executeplanbatch;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

public interface ExecuteplanbatchMapper extends MyMapper<Executeplanbatch> {
    int ifexist(Condition condition);

    List<Executeplanbatch> getbatchbyplan(@Param("executeplanid") Long executeplanid);

    Executeplanbatch getbatchidbyplanidandbatchname(@Param("executeplanid") Long executeplanid, @Param("batchname") String batchname);


}