package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.Tester;

import java.util.List;
import java.util.Map;

public interface TesterMapper extends MyMapper<Tester> {
    /**
     * 按条件查询测试人员名和值
     *
     * @param params 参数
     * @return 用户列表
     */
    List<Tester> findtesterName(String params);

    /**
     * 按条件查询测试人员内容
     *
     * @param params 参数
     * @return 用户列表
     */
    List<Tester> findTesterWithName(final Map<String, Object> params);

    /**
     * 更新测试人员内容
     *
     * @param params 参数
     * @return 用户列表
     */
    void updateTester(Tester params);
}