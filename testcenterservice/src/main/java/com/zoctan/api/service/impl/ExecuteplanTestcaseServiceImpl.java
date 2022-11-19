package com.zoctan.api.service.impl;

import com.zoctan.api.core.service.AbstractService;
import com.zoctan.api.entity.Executeplan;
import com.zoctan.api.entity.ExecuteplanTestcase;
import com.zoctan.api.mapper.ExecuteplanMapper;
import com.zoctan.api.mapper.ExecuteplanTestcaseMapper;
import com.zoctan.api.service.ExecuteplanService;
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
    @Resource
    private ExecuteplanService executeplanService;

    @Override
    public List<ExecuteplanTestcase> findexplanWithName(Map<String, Object> params) {
        return executeplanTestcaseMapper.findexplanWithName(params);
    }

    @Override
    public List<ExecuteplanTestcase> getplancasesbyplanid(long executeplanid) {
        return executeplanTestcaseMapper.getplancasesbyplanid(executeplanid);
    }

    @Override
    public void savetestplancase(List<ExecuteplanTestcase> testcase) {
        List<ExecuteplanTestcase> caselist = new ArrayList<ExecuteplanTestcase>();
        long execplanid=0;
        for (ExecuteplanTestcase tc : testcase) {
            execplanid = tc.getExecuteplanid();
            Integer tmptestcase = executeplanTestcaseMapper.findcasebyplanidandcaseid(tc.getExecuteplanid(), tc.getTestcaseid());
            if (tmptestcase == null || tmptestcase.intValue() == 0) {
                caselist.add(tc);
            }
        }
        if (caselist.size() > 0) {
            executeplanTestcaseMapper.savetestplancase(caselist);
            Executeplan executeplan= executeplanService.getById(execplanid);
            if(executeplan!=null)
            {
                long casecount=executeplan.getCasecounts();
                executeplan.setCasecounts(casecount+caselist.size());
                executeplanService.update(executeplan);
            }
        }
    }

    @Override
    public List<ExecuteplanTestcase> finddeployunitbyplanid(long executeplanid) {
        return executeplanTestcaseMapper.finddeployunitbyplanid(executeplanid);
    }

    @Override
    public List<ExecuteplanTestcase> findcaseorderexist(long executeplanid, long caseorder) {
        return executeplanTestcaseMapper.findcaseorderexist(executeplanid, caseorder);
    }

    @Override
    public ExecuteplanTestcase findexecplancasebyid(long id) {
        return executeplanTestcaseMapper.findexecplancasebyid(id);
    }

    @Override
    public List<ExecuteplanTestcase> getplancasesbyplanidandorder(long executeplanid) {
        return executeplanTestcaseMapper.getplancasesbyplanidandorder(executeplanid);
    }


    @Override
    public List<ExecuteplanTestcase> findcasebydeployandapi(Map<String, Object> params) {
        return executeplanTestcaseMapper.findcasebydeployandapi(params);
    }

    @Override
    public void removeexecuteplantestcase(List<ExecuteplanTestcase> testcase) {
        long execplanid=0;
        for (ExecuteplanTestcase tc : testcase) {
            execplanid = tc.getExecuteplanid();
            executeplanTestcaseMapper.removeexecuteplantestcase(tc.getExecuteplanid(), tc.getTestcaseid());
        }
        Executeplan executeplan= executeplanService.getById(execplanid);
        if(executeplan!=null)
        {
            long casecount=executeplan.getCasecounts();
            executeplan.setCasecounts(casecount-testcase.size());
            executeplanService.update(executeplan);
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
    public void updatePlanCaseorder(long id, long caseorder) {
        executeplanTestcaseMapper.updatePlanCaseorder(id, caseorder);
    }

    @Override
    public Integer findcasenumbyplanid(long executeplanid) {
        return executeplanTestcaseMapper.findcasenumbyplanid(executeplanid);
    }
    @Override
    public int ifexist(Condition con) {
        return countByCondition(con);
    }


    @Override
    public List<ExecuteplanTestcase> getstaticsplancases(long projectid) {
        return executeplanTestcaseMapper.getstaticsplancases(projectid);
    }
}
