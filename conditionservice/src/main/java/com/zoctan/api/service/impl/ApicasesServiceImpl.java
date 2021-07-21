package com.zoctan.api.service.impl;

import com.zoctan.api.core.service.AbstractService;
import com.zoctan.api.entity.Apicases;
import com.zoctan.api.mapper.ApicasesMapper;
import com.zoctan.api.service.ApicasesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author Zoctan
* @date 2020/09/11
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class ApicasesServiceImpl extends AbstractService<Apicases> implements ApicasesService {
@Resource
private ApicasesMapper apicasesMapper;



    @Override
    public List<Apicases> findApiCaseWithName(Map<String, Object> params) {
        return this.apicasesMapper.findApiCaseWithName(params);
    }

    @Override
    public void updateApicase(Apicases params) {
        apicasesMapper.updateApicase(params);

    }

    @Override
    public Apicases GetCaseByCaseID(long caseid) {
        return apicasesMapper.GetCaseByCaseID(caseid);
    }
}
