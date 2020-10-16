package com.zoctan.api.service.impl;

import com.zoctan.api.mapper.EnvmachineMapper;
import com.zoctan.api.entity.Envmachine;
import com.zoctan.api.service.EnvmachineService;
import com.zoctan.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author Zoctan
* @date 2020/05/21
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class EnvmachineServiceImpl extends AbstractService<Envmachine> implements EnvmachineService {
@Resource
private EnvmachineMapper envmachineMapper;

    @Override
    public List<Envmachine> listAllEnvAndMac() {
        return this.envmachineMapper.listAllEnvAndMac();
    }

    @Override
    public List<Envmachine> findEnvAndMacWithName(Map<String, Object> params) {
        return this.envmachineMapper.findEnvAndMacWithName(params);
    }

    @Override
    public void updateEnvAndMac(Envmachine params) {
        envmachineMapper.updateEnvAndMac(params);
    }
}
