package com.zoctan.api.service.impl;

import com.zoctan.api.core.service.AbstractService;
import com.zoctan.api.entity.ApiParams;
import com.zoctan.api.mapper.ApiParamsMapper;
import com.zoctan.api.service.ApiParamsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author Zoctan
* @date 2020/05/20
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class ApiParamsServiceImpl extends AbstractService<ApiParams> implements ApiParamsService {
@Resource
private ApiParamsMapper apiParamsMapper;

    @Override
    public List<ApiParams> findApiParamsWithName(Map<String, Object> params) {
        return this.apiParamsMapper.findApiParamsWithName(params);
    }

    @Override
    public List<ApiParams> getApiParamsbyname(Map<String, Object> params) {
        return this.apiParamsMapper.getApiParamsbyname(params);
    }

    @Override
    public int ifexist(Condition con) {
        return countByCondition(con);
    }

    @Override
    public void updateApiParams(ApiParams params) {
        apiParamsMapper.updateApiParams(params);
    }
}
