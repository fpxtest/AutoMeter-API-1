package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.Apicases;

import java.util.List;
import java.util.Map;

public interface ApicasesMapper extends MyMapper<Apicases> {
    /**
     * 按发布单元名或者协议名获取发布单元内容
     *
     * @param params 参数
     * @return 发布单元列表
     */
    List<Apicases> findApiCaseWithName(final Map<String, Object> params);

    /**
     * 更新发布单元内容
     *
     * @param params 参数
     * @return 发布单元列表
     */
    void updateApicase(Apicases params);
}