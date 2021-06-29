package com.zoctan.api.service.impl;

import com.zoctan.api.mapper.ConditionReportMapper;
import com.zoctan.api.entity.ConditionReport;
import com.zoctan.api.service.ConditionReportService;
import com.zoctan.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author SeasonFan
* @date 2021/05/30
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class ConditionReportServiceImpl extends AbstractService<ConditionReport> implements ConditionReportService {
@Resource
private ConditionReportMapper conditionReportMapper;

}
