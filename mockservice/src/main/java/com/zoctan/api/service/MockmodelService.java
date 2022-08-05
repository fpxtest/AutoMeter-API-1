package com.zoctan.api.service;

import com.zoctan.api.core.service.Service;
import com.zoctan.api.entity.Mockmodel;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2022/08/03
*/
public interface MockmodelService extends Service<Mockmodel> {

    List<Mockmodel> findMockmodelWithName(final Map<String, Object> params);

    /**
     * 更新字典内容
     *
     * @param params 参数
     * @return 用户列表
     */
    void updateMockmodel(Mockmodel params);


    int ifexist(Condition condition);

}
