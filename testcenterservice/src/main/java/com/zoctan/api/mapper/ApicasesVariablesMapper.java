package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.ApicasesVariables;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

public interface ApicasesVariablesMapper extends MyMapper<ApicasesVariables> {
    List<ApicasesVariables> findApicasesVariablesWithName(final Map<String, Object> params);

    /**
     * 更新字典内容
     *
     * @param params 参数
     * @return 用户列表
     */
    void updateApicasesVariables(ApicasesVariables params);

    List<ApicasesVariables> getbyvariablesid(final Map<String, Object> params);


    int ifexist(Condition condition);
}