package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.Deployunit;
import com.zoctan.api.entity.DeployunitModel;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

public interface DeployunitModelMapper extends MyMapper<DeployunitModel> {
    List<DeployunitModel> findDeployModelWithName(final Map<String, Object> params);
    int ifexist(Condition condition);
    void updateDeploy(DeployunitModel params);

}