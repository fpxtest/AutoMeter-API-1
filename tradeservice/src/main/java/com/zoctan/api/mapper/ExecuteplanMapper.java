package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.Executeplan;
import org.apache.ibatis.annotations.Param;

public interface ExecuteplanMapper extends MyMapper<Executeplan> {

    void updatetestplanstatus(@Param("id")Long id, @Param("status")String status);
}