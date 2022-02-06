package com.zoctan.api.service.impl;

import com.zoctan.api.core.service.AbstractService;
import com.zoctan.api.entity.ApicasesVariables;
import com.zoctan.api.mapper.ApicasesVariablesMapper;
import com.zoctan.api.service.ApicasesVariablesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2021/07/14
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class ApicasesVariablesServiceImpl extends AbstractService<ApicasesVariables> implements ApicasesVariablesService {
@Resource
private ApicasesVariablesMapper apicasesVariablesMapper;

    @Override
    public List<ApicasesVariables> findApicasesVariablesWithName(Map<String, Object> params) {
        return apicasesVariablesMapper.findApicasesVariablesWithName(params);
    }

    @Override
    public void updateApicasesVariables(ApicasesVariables params) {
        apicasesVariablesMapper.updateApicasesVariables(params);
    }

    @Override
    public int ifexist(Condition con) {
        return countByCondition(con);
    }

}
