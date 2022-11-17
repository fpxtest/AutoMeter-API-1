package com.zoctan.api.service.impl;

import com.zoctan.api.entity.ExecuteplanTestcase;
import com.zoctan.api.mapper.ProjectAccountMapper;
import com.zoctan.api.entity.ProjectAccount;
import com.zoctan.api.service.ProjectAccountService;
import com.zoctan.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2022/11/16
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class ProjectAccountServiceImpl extends AbstractService<ProjectAccount> implements ProjectAccountService {
@Resource
private ProjectAccountMapper projectAccountMapper;

    @Override
    public List<ProjectAccount> findaccountbyprojectid(Map<String, Object> params) {
        return projectAccountMapper.findaccountbyprojectid(params);
    }

    @Override
    public void saveprojectaccount(List<ProjectAccount> projectaccount) {
        List<ProjectAccount> caselist = new ArrayList<ProjectAccount>();
        for (ProjectAccount tc : projectaccount) {
            Integer tmptestcase = projectAccountMapper.findaccountbyprojectidandaccountid(tc.getProjectid(), tc.getAccountid());
            if (tmptestcase == null || tmptestcase.intValue() == 0) {
                caselist.add(tc);
            }
        }
        if (caselist.size() > 0) {
            projectAccountMapper.saveprojectaccount(caselist);
        }
    }
}
