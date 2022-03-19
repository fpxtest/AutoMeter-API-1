package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.Slaver;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SlaverMapper extends MyMapper<Slaver> {
    List<Slaver> findslaverWithName(final Map<String, Object> params);

    List<Slaver> findslaverWithType(@Param("stype") final String stype);

    void updateSlaver(Slaver params);

    void updateSlaverStatus(@Param("id") Long id,@Param("status") String status);

    List<Slaver> findslaveralive(@Param("stype") final String stype, @Param("status") final String status);

    Integer getslavernum();

}