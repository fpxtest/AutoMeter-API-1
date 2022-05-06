package com.zoctan.api.service.impl;

import com.zoctan.api.mapper.ProcessTestcaseMapper;
import com.zoctan.api.entity.ProcessTestcase;
import com.zoctan.api.service.ProcessTestcaseService;
import com.zoctan.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author SeasonFan
* @date 2022/04/27
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class ProcessTestcaseServiceImpl extends AbstractService<ProcessTestcase> implements ProcessTestcaseService {
@Resource
private ProcessTestcaseMapper processTestcaseMapper;

}
