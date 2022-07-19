package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.Envmachine;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EnvmachineMapper extends MyMapper<Envmachine> {

    /**
     * 获取环境服务器
     *
     * @return 环境服务器列表
     */
    List<Envmachine> listAllEnvAndMac();


    /**
     * 按环境名或者服务器名获取环境服务器内容
     *
     * @param params 参数
     * @return 环境服务器列表
     */
    List<Envmachine> findEnvAndMacWithName(final Map<String, Object> params);

    /**
     * 更新环境服务器内容
     *
     * @param params 参数
     * @return 环境服务器列表
     */
    void updateEnvAndMac(Envmachine params);

    Envmachine findexist(@Param("enviromentname") String enviromentname, @Param("machinename") String machinename);

    Envmachine findexistwithoutself(@Param("enviromentname") String enviromentname, @Param("machinename") String machinename, @Param("id") Long id);

    List<Envmachine> findmachinebyid(@Param("machineid") long machineid);

    List<Envmachine> findmachinebyenvid(@Param("envid") long envid);

}