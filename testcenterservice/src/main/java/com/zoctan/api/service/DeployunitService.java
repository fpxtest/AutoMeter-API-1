package com.zoctan.api.service;

import com.zoctan.api.core.service.Service;
import com.zoctan.api.entity.Deployunit;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author Zoctan
* @date 2020/04/17
*/
public interface DeployunitService extends Service<Deployunit> {
    /**
     * 按条件查询字典名和值
     *
     * @param params 参数
     * @return 用户列表
     */
    Deployunit findDeployNameValueWithCode(String params);

    /**
     * 按条件查询字典内容
     *
     * @param params 参数
     * @return 用户列表
     */
    List<Deployunit> findDeployWithName(final Map<String, Object> params);

    /**
     * 更新字典内容
     *
     * @param params 参数
     * @return 用户列表
     */
    void updateDeploy(Deployunit params);

    int ifexist(Condition condition);

    Integer getdeploynum(long projectid);

    List<String> getstaticsdeploynames(long projectid);


}
