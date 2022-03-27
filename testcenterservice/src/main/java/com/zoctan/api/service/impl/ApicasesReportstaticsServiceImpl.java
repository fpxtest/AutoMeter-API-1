package com.zoctan.api.service.impl;

import com.zoctan.api.mapper.ApicasesReportstaticsMapper;
import com.zoctan.api.entity.ApicasesReportstatics;
import com.zoctan.api.service.ApicasesReportstaticsService;
import com.zoctan.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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
    public List<ApicasesReportstatics> getapicasesreportstaticsbypandb(long planid, String batchname) {
        return apicasesReportstaticsMapper.getapicasesreportstaticsbypandb(planid, batchname);
    }
}
