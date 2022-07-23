package com.zoctan.api.service.impl;

import com.zoctan.api.core.service.AbstractService;
import com.zoctan.api.entity.Globalvariables;
import com.zoctan.api.mapper.GlobalvariablesMapper;
import com.zoctan.api.service.GlobalvariablesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2022/07/11
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class GlobalvariablesServiceImpl extends AbstractService<Globalvariables> implements GlobalvariablesService {
@Resource
private GlobalvariablesMapper globalvariablesMapper;

    @Override
    public int ifexist(Condition con) {
        return countByCondition(con);
    }


    @Override
    public List<Globalvariables> findGlobalvariablesWithName(Map<String, Object> params) {
        return globalvariablesMapper.findGlobalvariablesWithName(params);
    }

    @Override
    public void updateGlobalvariables(Globalvariables params) {
        globalvariablesMapper.updateGlobalvariables(params);
    }
}
