package com.zoctan.api.service.impl;

import com.zoctan.api.mapper.ApiMapper;
import com.zoctan.api.entity.Api;
import com.zoctan.api.service.ApiService;
import com.zoctan.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author Zoctan
* @date 2020/04/24
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class ApiServiceImpl extends AbstractService<Api> implements ApiService {
@Resource
private ApiMapper apiMapper;

    @Override
    public List<Api> findApiWithName(Map<String, Object> params) {
        return this.apiMapper.findApiWithName(params);
    }

    @Override
    public void updateApi(Api params) {
        apiMapper.updateApi(params);

    }
}
