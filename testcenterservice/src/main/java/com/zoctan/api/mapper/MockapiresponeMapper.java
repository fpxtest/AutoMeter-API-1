package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.Mockapirespone;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

public interface MockapiresponeMapper extends MyMapper<Mockapirespone> {
    List<Mockapirespone> findMockapiresponeWithName(final Map<String, Object> params);

    /**
     * 更新字典内容
     *
     * @param params 参数
     * @return 用户列表
     */
    void updateMockapirespone(Mockapirespone params);
    void enableapirespone(@Param("apiid") long apiid ,@Param("status")  String status);
    void enablerespone(@Param("id") long id ,@Param("status")  String status);

    int ifexist(Condition condition);
}