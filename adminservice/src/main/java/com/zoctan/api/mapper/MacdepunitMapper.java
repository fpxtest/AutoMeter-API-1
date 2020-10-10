package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.Macdepunit;

import java.util.List;
import java.util.Map;

public interface MacdepunitMapper extends MyMapper<Macdepunit> {
    /**
     * 按环境名或者服务器名获取服务器发布单元内容
     *
     * @param params 参数
     * @return 环境服务器列表
     */
    List<Macdepunit> findMacAndDepWithName(final Map<String, Object> params);

    /**
     * 更新服务器发布单元内容
     *
     * @param params 参数
     * @return 环境服务器列表
     */
    void updateMacAndDep(Macdepunit params);
}