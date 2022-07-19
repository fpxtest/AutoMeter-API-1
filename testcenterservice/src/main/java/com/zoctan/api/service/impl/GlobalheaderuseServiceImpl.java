package com.zoctan.api.service.impl;

import com.zoctan.api.mapper.GlobalheaderuseMapper;
import com.zoctan.api.entity.Globalheaderuse;
import com.zoctan.api.service.GlobalheaderuseService;
import com.zoctan.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2022/07/12
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class GlobalheaderuseServiceImpl extends AbstractService<Globalheaderuse> implements GlobalheaderuseService {
@Resource
private GlobalheaderuseMapper globalheaderuseMapper;

    @Override
    public List<Globalheaderuse> findexistcaseglobalheader(Map<String, Object> params) {
        return globalheaderuseMapper.findexistcaseglobalheader(params);
    }

    @Override
    public void saveconditionscase(List<Globalheaderuse> casedataList) {
        globalheaderuseMapper.saveconditionscase(casedataList);
    }

    @Override
    public void deletacases(List<Globalheaderuse> casedataList) {
        globalheaderuseMapper.deletacases(casedataList);
    }

    @Override
    public List<Globalheaderuse> findnotexistcase(long globalheaderid,long deployunitid) {
        return globalheaderuseMapper.findnotexistcase(globalheaderid,deployunitid);
    }

    @Override
    public List<Globalheaderuse> searchheaderbyepid(Map<String, Object> params) {
        return globalheaderuseMapper.searchheaderbyepid(params);
    }

    @Override
    public int ifexist(Condition con) {
        return countByCondition(con);
    }

}
