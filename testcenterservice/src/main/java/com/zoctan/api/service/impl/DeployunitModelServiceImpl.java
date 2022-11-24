package com.zoctan.api.service.impl;

import com.zoctan.api.entity.Deployunit;
import com.zoctan.api.mapper.DeployunitModelMapper;
import com.zoctan.api.entity.DeployunitModel;
import com.zoctan.api.service.DeployunitModelService;
import com.zoctan.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2022/11/24
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class DeployunitModelServiceImpl extends AbstractService<DeployunitModel> implements DeployunitModelService {
@Resource
private DeployunitModelMapper deployunitModelMapper;

    @Override
    public List<DeployunitModel> findDeployModelWithName(Map<String, Object> params) {
        return deployunitModelMapper.findDeployModelWithName(params);
    }

    @Override
    public int ifexist(Condition condition) {
        return countByCondition(condition);
    }

    @Override
    public void updateDeploy(DeployunitModel params)
    {
        deployunitModelMapper.updateDeploy(params);
    }


}
