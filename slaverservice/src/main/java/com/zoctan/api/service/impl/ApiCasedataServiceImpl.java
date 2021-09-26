package com.zoctan.api.service.impl;

import com.zoctan.api.core.service.AbstractService;
import com.zoctan.api.dto.Casedata;
import com.zoctan.api.entity.ApiCasedata;
import com.zoctan.api.mapper.ApiCasedataMapper;
import com.zoctan.api.service.ApiCasedataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
    public void save(final Casedata cd)
    {
        this.apiCasedataMapper.deleteparamvaluebycaseidandtype(cd.getCaseid(),cd.getPropertytype());
        this.apiCasedataMapper.saveCasedata(cd.getCasedataMap());
    }

    @Override
    public List<ApiCasedata> getparamvaluebycaseidandtype(final Map<String, Object> params) {
        return this.apiCasedataMapper.getparamvaluebycaseidandtype(params);
    }

    @Override
    public List<ApiCasedata> getcasedatabycaseid(Long caseid) {
        return this.apiCasedataMapper.getcasedatabycaseid(caseid);
    }

    @Override
    public void deletcasedatabyid(Long caseid) {
        this.apiCasedataMapper.deletcasedatabyid(caseid);
    }
}
