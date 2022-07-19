package com.zoctan.api.service.impl;

import com.zoctan.api.core.service.AbstractService;
import com.zoctan.api.entity.GlobalheaderParams;
import com.zoctan.api.mapper.GlobalheaderParamsMapper;
import com.zoctan.api.service.GlobalheaderParamsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2022/07/09
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class GlobalheaderParamsServiceImpl extends AbstractService<GlobalheaderParams> implements GlobalheaderParamsService {
@Resource
private GlobalheaderParamsMapper globalheaderParamsMapper;

    @Override
    public int ifexist(Condition con) {
        return countByCondition(con);
    }


    @Override
    public void updateGlobalheaderParams(GlobalheaderParams params) {
        globalheaderParamsMapper.updateGlobalheaderParams(params);
    }

    @Override
    public List<GlobalheaderParams> findGlobalheaderParamsWithName(Map<String, Object> params) {
        return globalheaderParamsMapper.findGlobalheaderParamsWithName(params);
    }
}
