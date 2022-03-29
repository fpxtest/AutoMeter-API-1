package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.Api;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

public interface ApiMapper extends MyMapper<Api> {
    /**
     * 按发布单元名或者协议名获取发布单元内容
     *
     * @param params 参数
     * @return 发布单元列表
     */
    List<Api> findApiWithName(final Map<String, Object> params);

    /**
     * 更新发布单元内容
     *
     * @param params 参数
     * @return 发布单元列表
     */
    void updateApi(Api params);

    List<Api> listAllbydeploy(@Param("deployunitid") long deployunitid);

    Api getresponetypebydeployandapiname(@Param("deployunitname")String deployunitname,@Param("apiname")String apiname);
    Api getapibydvap(@Param("deployunitid") Long deployunitid,@Param("visittype")String visittype,@Param("path")String path);

    int ifexist(Condition condition);

    Integer getapinum();

    List<Api> getstaticsdeployapi();

    List<Api> getapibydeployunitid(Long deployunitid);
}