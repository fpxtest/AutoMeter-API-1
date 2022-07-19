package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.ApiParams;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

public interface ApiParamsMapper extends MyMapper<ApiParams> {
    /**
     * 按条件查询Api内容
     *
     * @param params 参数
     * @return 用户列表
     */
    List<ApiParams> findApiParamsWithName(final Map<String, Object> params);

    List<ApiParams> getApiParamsbyapiid(final Map<String, Object> params);

    /**
     * 按条件查询Api内容
     *
     * @param params 参数
     * @return 用户列表
     */
    List<ApiParams> getApiParamsbyname(final Map<String, Object> params);

    void deletebyApiid(@Param("apiid") Long apiid);

    /**
     * 更新Api内容
     *
     * @param params 参数
     * @return 用户列表
     */
    void updateApiParams(ApiParams params);

    int ifexist(Condition condition);

    void SaveApiParams(ApiParams apiParams);

    List<ApiParams> getApiParamsbypropertytype(@Param("apiid")Long apiid, @Param("propertytype")String propertytype);

    List<ApiParams> findApiParamsbypropertytype(@Param("apiid")Long apiid, @Param("propertytype")String propertytype);

    ApiParams getBodyNoFormbyapiid(@Param("apiid")Long apiid, @Param("propertytype")String propertytype, @Param("keydefaultvalue")String keydefaultvalue, @Param("keytype")String keytype);

}