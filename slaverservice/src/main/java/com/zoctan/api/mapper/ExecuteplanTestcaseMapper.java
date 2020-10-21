package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.ExecuteplanTestcase;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ExecuteplanTestcaseMapper extends MyMapper<ExecuteplanTestcase> {

    /**
     *
     *
     * @param testcase 用例参数数据
     */
    void savetestplancase(@Param("casedataList") final List<ExecuteplanTestcase> testcase);

    List<ExecuteplanTestcase> findcasebydeployandapi(final Map<String, Object> params);

    Integer findcasebyplanidandcaseid(@Param("executeplanid") long executeplanid, @Param("testcaseid") long testcaseid);


    void removeexecuteplantestcase(@Param("executeplanid") long executeplanid, @Param("testcaseid") long testcaseid);

}