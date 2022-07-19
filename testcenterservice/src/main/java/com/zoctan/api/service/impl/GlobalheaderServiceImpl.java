package com.zoctan.api.service.impl;

import com.zoctan.api.mapper.GlobalheaderMapper;
import com.zoctan.api.entity.Globalheader;
import com.zoctan.api.service.GlobalheaderService;
import com.zoctan.api.core.service.AbstractService;
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
public class GlobalheaderServiceImpl extends AbstractService<Globalheader> implements GlobalheaderService {
@Resource
private GlobalheaderMapper globalheaderMapper;

    @Override
    public int ifexist(Condition con) {
        return countByCondition(con);
    }


    @Override
    public List<Globalheader> findGlobalheaderWithName(Map<String, Object> params) {
        return globalheaderMapper.findGlobalheaderWithName(params);
    }

    @Override
    public void updateGlobalheader(Globalheader params) {
        globalheaderMapper.updateGlobalheader(params);
    }
}
