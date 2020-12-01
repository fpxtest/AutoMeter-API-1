package com.zoctan.api.service.impl;

import com.zoctan.api.core.service.AbstractService;
import com.zoctan.api.entity.ApicasesCondition;
import com.zoctan.api.mapper.ApicasesConditionMapper;
import com.zoctan.api.service.ApicasesConditionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2020/11/03
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class ApicasesConditionServiceImpl extends AbstractService<ApicasesCondition> implements ApicasesConditionService {
@Resource
private ApicasesConditionMapper apicasesConditionMapper;

    @Override
    public int ifexist(Condition con) {
        return countByCondition(con);
    }

    @Override
    public List<ApicasesCondition> findconditionWithName(Map<String, Object> params) {
        return apicasesConditionMapper.findconditionWithName(params);
    }

}
