package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.Enviroment;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EnviromentMapper extends MyMapper<Enviroment> {

    /**
     * 按条件获取微服务名
     *
     * @param params 参数
     * @return 微服务列表
     */
    List<Enviroment> findEnviromentName(String params,long projectid);


    /**
     * 按微服务名或者协议名获取微服务内容
     *
     * @param params 参数
     * @return 微服务列表
     */
    List<Enviroment> findEnviromentWithName(final Map<String, Object> params);

    /**
     * 更新微服务内容
     *
     * @param params 参数
     * @return 微服务列表
     */
    void updateEnviroment(Enviroment params);

    Integer getenviromentnum(@Param("projectid") long projectid);

}