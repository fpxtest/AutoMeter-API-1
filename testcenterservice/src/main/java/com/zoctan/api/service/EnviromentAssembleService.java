package com.zoctan.api.service;

import com.zoctan.api.core.service.Service;
import com.zoctan.api.entity.EnviromentAssemble;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2020/11/06
*/
public interface EnviromentAssembleService extends Service<EnviromentAssemble> {

    List<EnviromentAssemble> findassembleWithName(final Map<String, Object> params);

    /**
     * 更新字典内容
     *
     * @param params 参数
     * @return 用户列表
     */
    void updateenviromentassemble(EnviromentAssemble params);


    int ifexist(Condition condition);

}
