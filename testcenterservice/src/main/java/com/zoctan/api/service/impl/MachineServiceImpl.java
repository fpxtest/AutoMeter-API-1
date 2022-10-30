package com.zoctan.api.service.impl;

import com.zoctan.api.mapper.MachineMapper;
import com.zoctan.api.entity.Machine;
import com.zoctan.api.service.MachineService;
import com.zoctan.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author Zoctan
* @date 2020/04/15
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class MachineServiceImpl extends AbstractService<Machine> implements MachineService {
@Resource
private MachineMapper machineMapper;

    @Override
    public Machine findmachinebymachinename(String machinename) {
        return machineMapper.findmachinebymachinename(machinename);
    }

    @Override
    public Machine findmachinebymachineandip(String machinename, String ip, Long ID,long projectid) {
        return machineMapper.findmachinebymachineandip(machinename, ip, ID,projectid);
    }

    @Override
    public Machine findmachinebyip(String ip) {
        return machineMapper.findmachinebyip(ip);
    }

    @Override
    public int ifexist(Condition con) {
        return countByCondition(con);
    }

    @Override
    public List<Machine> findMachineWithName(Map<String, Object> params) {
        return machineMapper.findMachineWithName(params);
    }

    @Override
    public void updateMachine(Machine params) {
        machineMapper.updateMachine(params);
    }

    @Override
    public Integer getmachinenum(long projectid) {
        return machineMapper.getmachinenum(projectid);
    }

}
