package com.zoctan.api.service.impl;

import com.zoctan.api.core.service.AbstractService;
import com.zoctan.api.entity.ExecuteplanTestcase;
import com.zoctan.api.mapper.ExecuteplanTestcaseMapper;
import com.zoctan.api.service.ExecuteplanTestcaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<ExecuteplanTestcase> findexplanWithName(Map<String, Object> params) {
        return executeplanTestcaseMapper.findexplanWithName(params);
    }

    @Override
    public void savetestplancase(List<ExecuteplanTestcase> testcase) {
        List<ExecuteplanTestcase> caselist = new ArrayList<ExecuteplanTestcase>();
        for (ExecuteplanTestcase tc : testcase) {
            Integer tmptestcase = executeplanTestcaseMapper.findcasebyplanidandcaseid(tc.getExecuteplanid(), tc.getTestcaseid());
            if (tmptestcase == null || tmptestcase.intValue() == 0) {
                caselist.add(tc);
            }
        }
        if (caselist.size() > 0) {
            executeplanTestcaseMapper.savetestplancase(caselist);
        }
    }

    @Override
    public List<ExecuteplanTestcase> finddeployunitbyplanid(long executeplanid) {
        return executeplanTestcaseMapper.finddeployunitbyplanid(executeplanid);
    }


    @Override
    public List<ExecuteplanTestcase> findcasebydeployandapi(Map<String, Object> params) {
        return executeplanTestcaseMapper.findcasebydeployandapi(params);
    }

    @Override
    public void removeexecuteplantestcase(List<ExecuteplanTestcase> testcase) {
        for (ExecuteplanTestcase tc : testcase) {
            executeplanTestcaseMapper.removeexecuteplantestcase(tc.getExecuteplanid(), tc.getTestcaseid());
        }
    }

    @Override
    public void removetestcase(long testcaseid) {
        executeplanTestcaseMapper.removetestcase(testcaseid);
    }

    @Override
    public void removeplancase(long executeplanid) {
        executeplanTestcaseMapper.removeplancase(executeplanid);
    }

    @Override
    public Integer findcasenumbyplanid(long executeplanid) {
        return executeplanTestcaseMapper.findcasenumbyplanid(executeplanid);
    }

    @Override
    public List<ExecuteplanTestcase> getstaticsplancases() {
        return executeplanTestcaseMapper.getstaticsplancases();
    }
}
