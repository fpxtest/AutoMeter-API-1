package com.zoctan.api.service.impl;

import com.zoctan.api.entity.Enviroment;
import com.zoctan.api.mapper.ApicasesAssertMapper;
import com.zoctan.api.entity.ApicasesAssert;
import com.zoctan.api.service.ApicasesAssertService;
import com.zoctan.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2021/10/29
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class ApicasesAssertServiceImpl extends AbstractService<ApicasesAssert> implements ApicasesAssertService {
@Resource
private ApicasesAssertMapper apicasesAssertMapper;

    @Override
    public List<ApicasesAssert> findAssertWithName(Map<String, Object> params) {
        return this.apicasesAssertMapper.findAssertWithName(params);
    }

    @Override
    public List<ApicasesAssert> findAssertbycaseid(Map<String, Object> params) {
        return apicasesAssertMapper.findAssertbycaseid(params);
    }

    @Override
    public void updateAssert(ApicasesAssert params) {
        apicasesAssertMapper.updateAssert(params);

    }

    @Override
    public int ifexist(Condition con) {
        return countByCondition(con);
    }



}
