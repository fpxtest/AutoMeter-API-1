package com.zoctan.api.service;

import com.zoctan.api.core.service.Service;
import com.zoctan.api.entity.ExecuteplanTestcase;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2020/10/12
*/
public interface ExecuteplanTestcaseService extends Service<ExecuteplanTestcase> {

    List<ExecuteplanTestcase> findcasebytestplanid(long executeplanid,  String casetype);

    int ifexist(Condition condition);


}
