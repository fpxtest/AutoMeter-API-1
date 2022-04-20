package com.zoctan.api.service.impl;

import com.zoctan.api.mapper.RouteperformancereportMapper;
import com.zoctan.api.entity.Routeperformancereport;
import com.zoctan.api.service.RouteperformancereportService;
import com.zoctan.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author SeasonFan
* @date 2022/04/13
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class RouteperformancereportServiceImpl extends AbstractService<Routeperformancereport> implements RouteperformancereportService {
@Resource
private RouteperformancereportMapper routeperformancereportMapper;

}
