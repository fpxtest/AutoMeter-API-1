package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.Performancereportfilelog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PerformancereportfilelogMapper extends MyMapper<Performancereportfilelog> {
    List<Performancereportfilelog> findrecentperformancereportfile(@Param("slaverid") long slaverid);

}