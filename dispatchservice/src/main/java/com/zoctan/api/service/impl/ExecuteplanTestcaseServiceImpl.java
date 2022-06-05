package com.zoctan.api.service.impl;

import com.zoctan.api.core.service.AbstractService;
import com.zoctan.api.entity.ExecuteplanTestcase;
import com.zoctan.api.mapper.ExecuteplanTestcaseMapper;
import com.zoctan.api.service.ExecuteplanTestcaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2020/10/12
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class ExecuteplanTestcaseServiceImpl extends AbstractService<ExecuteplanTestcase> implements ExecuteplanTestcaseService {
@Resource
private ExecuteplanTestcaseMapper executeplanTestcaseMapper;


    @Override
    public List<ExecuteplanTestcase> findcasebytestplanid(long executeplanid, String casetype) {
        return executeplanTestcaseMapper.findcasebytestplanid(executeplanid, casetype);
    }

    @Override
    public int ifexist(Condition con) {
        return countByCondition(con);
    }


    }
