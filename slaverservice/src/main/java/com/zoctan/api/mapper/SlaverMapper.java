package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.Slaver;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SlaverMapper extends MyMapper<Slaver> {
    /**
     * 按条件查询字典内容
     *
     * @param params 参数
     * @return 用户列表
     */
    List<Slaver> findslaverWithName(final Map<String, Object> params);

    List<Slaver> findslaverbyip(@Param("ip") final String ip);

    List<Slaver> findslaverbymac(@Param("macaddress") final String macaddress);
    /**
     * 更新字典内容
     *
     * @param params 参数
     * @return 用户列表
     */
    void updateSlaver(Slaver params);

    void updateSlaverStaus(@Param("id") final Long id,@Param("status") final String status);

    void addslaver(Slaver params);
}