package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.Deployunit;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

public interface DeployunitMapper extends MyMapper<Deployunit> {

    /**
     * 按条件获取微服务名
     *
     * @param deployunitname 参数
     * @return 微服务列表
     */
    Deployunit findDeployNameValueWithCode(@Param("deployunitname") String deployunitname);

    /**
     * 按微服务名或者协议名获取微服务内容
     *
     * @param params 参数
     * @return 微服务列表
     */
    List<Deployunit> findDeployWithName(final Map<String, Object> params);

    /**
     * 更新微服务内容
     *
     * @param params 参数
     * @return 微服务列表
     */
    void updateDeploy(Deployunit params);


    int ifexist(Condition condition);

    Integer getdeploynum(@Param("projectid")long projectid);

    List<String> getstaticsdeploynames(@Param("projectid")long projectid);

    Deployunit findDeployUnitWithid(@Param("id") Long id);


}