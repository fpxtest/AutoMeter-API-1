package com.zoctan.api.service;

import com.zoctan.api.core.service.Service;
import com.zoctan.api.entity.ExecuteplanTestcase;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2020/10/12
*/
public interface ExecuteplanTestcaseService extends Service<ExecuteplanTestcase> {


    List<ExecuteplanTestcase> findexplanWithName(final Map<String, Object> params);

    List<ExecuteplanTestcase> getplancasesbyplanid(final long executeplanid);

    void savetestplancase(final List<ExecuteplanTestcase> testcase);

    List<ExecuteplanTestcase> finddeployunitbyplanid(final long executeplanid);

    List<ExecuteplanTestcase> findcaseorderexist(final long executeplanid,long caseorder);

    ExecuteplanTestcase findexecplancasebyid(final long id);

    List<ExecuteplanTestcase> getplancasesbyplanidandorder(final long executeplanid);

    List<ExecuteplanTestcase> findcasebydeployandapi(final Map<String, Object> params);

    void removeexecuteplantestcase(final List<ExecuteplanTestcase> testcase);

    void removetestcase(final long testcaseid);

    void removeplancase(final long executeplanid);
    void updatePlanCaseorder(final long id,long caseorder);

    Integer findcasenumbyplanid(long executeplanid);

    List<ExecuteplanTestcase> getstaticsplancases(long projectid);

    int ifexist(Condition condition);


}
