package com.zoctan.api.service;

import com.zoctan.api.entity.Deployunit;
import com.zoctan.api.entity.DeployunitModel;
import com.zoctan.api.core.service.Service;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2022/11/24
*/
public interface DeployunitModelService extends Service<DeployunitModel> {

    List<DeployunitModel> findDeployModelWithName(final Map<String, Object> params);
    int ifexist(Condition condition);
    void updateDeploy(DeployunitModel params);

}
