package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.Mockapi;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

public interface MockapiMapper extends MyMapper<Mockapi> {
    List<Mockapi> findMockapiWithName(final Map<String, Object> params);

    /**
     * 更新字典内容
     *
     * @param params 参数
     * @return 用户列表
     */
    void updateMockapi(Mockapi params);


    int ifexist(Condition condition);
}