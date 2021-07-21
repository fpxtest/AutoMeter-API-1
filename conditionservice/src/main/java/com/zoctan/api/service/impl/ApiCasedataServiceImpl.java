package com.zoctan.api.service.impl;

import com.zoctan.api.core.service.AbstractService;
import com.zoctan.api.entity.ApiCasedata;
import com.zoctan.api.mapper.ApiCasedataMapper;
import com.zoctan.api.service.ApiCasedataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Zoctan
 * @date 2020/09/21
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ApiCasedataServiceImpl extends AbstractService<ApiCasedata> implements ApiCasedataService {
    @Resource
    private ApiCasedataMapper apiCasedataMapper;

    @Override
    public List<ApiCasedata> GetCaseDatasByCaseID(Long caseid) {
        return apiCasedataMapper.GetCaseDatasByCaseID(caseid);
    }
}
