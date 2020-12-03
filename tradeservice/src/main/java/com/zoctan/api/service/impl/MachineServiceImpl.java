package com.zoctan.api.service.impl;

import com.zoctan.api.mapper.MachineMapper;
import com.zoctan.api.entity.Machine;
import com.zoctan.api.service.MachineService;
import com.zoctan.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author Zoctan
* @date 2020/04/15
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class MachineServiceImpl extends AbstractService<Machine> implements MachineService {
@Resource
private MachineMapper machineMapper;

}
