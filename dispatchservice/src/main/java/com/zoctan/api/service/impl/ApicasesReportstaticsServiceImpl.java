package com.zoctan.api.service.impl;

import com.zoctan.api.core.service.AbstractService;
import com.zoctan.api.entity.ApicasesReportstatics;
import com.zoctan.api.mapper.ApicasesReportstaticsMapper;
import com.zoctan.api.service.ApicasesReportstaticsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;

/**
* @author SeasonFan
* @date 2021/04/01
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class ApicasesReportstaticsServiceImpl extends AbstractService<ApicasesReportstatics> implements ApicasesReportstaticsService {
@Resource
private ApicasesReportstaticsMapper apicasesReportstaticsMapper;

    @Override
    public int ifexist(Condition con) {
        return countByCondition(con);
    }

}
