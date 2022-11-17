package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.ExecuteplanTestcase;
import com.zoctan.api.entity.ProjectAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProjectAccountMapper extends MyMapper<ProjectAccount> {
    List<ProjectAccount> findaccountbyprojectid(final Map<String, Object> params);

    void saveprojectaccount(@Param("projectaccountList")final List<ProjectAccount> projectaccount);
    Integer findaccountbyprojectidandaccountid(@Param("projectid") long projectid, @Param("accountid") long accountid);

}