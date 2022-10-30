package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.Enviroment;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EnviromentMapper extends MyMapper<Enviroment> {

    /**
     * 按条件获取发布单元名
     *
     * @param params 参数
     * @return 发布单元列表
     */
    List<Enviroment> findEnviromentName(String params,long projectid);


    /**
     * 按发布单元名或者协议名获取发布单元内容
     *
     * @param params 参数
     * @return 发布单元列表
     */
    List<Enviroment> findEnviromentWithName(final Map<String, Object> params);

    /**
     * 更新发布单元内容
     *
     * @param params 参数
     * @return 发布单元列表
     */
    void updateEnviroment(Enviroment params);

    Integer getenviromentnum(@Param("projectid") long projectid);

}