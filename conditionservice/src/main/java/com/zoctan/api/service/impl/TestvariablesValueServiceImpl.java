package com.zoctan.api.service.impl;

import com.zoctan.api.core.service.AbstractService;
import com.zoctan.api.entity.TestvariablesValue;
import com.zoctan.api.mapper.TestvariablesValueMapper;
import com.zoctan.api.service.TestvariablesValueService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2021/07/17
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class TestvariablesValueServiceImpl extends AbstractService<TestvariablesValue> implements TestvariablesValueService {
@Resource
private TestvariablesValueMapper testvariablesValueMapper;

    @Override
    public List<TestvariablesValue> findtestvariablesvalueWithName(Map<String, Object> params) {
        return testvariablesValueMapper.findtestvariablesvalueWithName(params);
    }

    @Override
    public void updatetestvariablesvalue(TestvariablesValue params) {
        testvariablesValueMapper.updatetestvariablesvalue(params);
    }

    @Override
    public TestvariablesValue findtestvariablesvalue(Long planid, Long caseid, String batchname, String variablesname) {
        return testvariablesValueMapper.findtestvariablesvalue(planid, caseid, batchname, variablesname);
    }

    @Override
    public int ifexist(Condition con) {
        return countByCondition(con);
    }

}
