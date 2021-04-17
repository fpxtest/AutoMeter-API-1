package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.Deployunit;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

public interface DeployunitMapper extends MyMapper<Deployunit> {

    /**
     * 按条件获取发布单元名
     *
     * @param deployunitname 参数
     * @return 发布单元列表
     */
    Deployunit findDeployNameValueWithCode(@Param("deployunitname") String deployunitname);

    /**
     * 按发布单元名或者协议名获取发布单元内容
     *
     * @param params 参数
     * @return 发布单元列表
     */
    List<Deployunit> findDeployWithName(final Map<String, Object> params);

    /**
     * 更新发布单元内容
     *
     * @param params 参数
     * @return 发布单元列表
     */
    void updateDeploy(Deployunit params);


    int ifexist(Condition condition);

    Integer getdeploynum();

    List<String> getstaticsdeploynames();

    Deployunit findDeployUnitWithid(@Param("id")Long id);


}