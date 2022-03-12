package com.zoctan.api.service;

import com.zoctan.api.core.service.Service;
import com.zoctan.api.entity.ApiParams;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author Zoctan
* @date 2020/05/20
*/
public interface ApiParamsService extends Service<ApiParams> {

    /**
     * 按条件查询Api内容
     *
     * @param params 参数
     * @return 用户列表
     */
    List<ApiParams> findApiParamsWithName(final Map<String, Object> params);



    List<ApiParams> getApiParamsbyname(final Map<String, Object> params);

    List<ApiParams> getApiParamsbyapiid(final Map<String, Object> params);

    /**
     * 更新Api内容
     *
     * @param params 参数
     * @return 用户列表
     */
    void updateApiParams(ApiParams params);

    void deletebyApiid(Long apiid);

    int ifexist(Condition condition);

    void SaveApiParams(ApiParams apiParams);

    List<ApiParams> getApiParamsbypropertytype(Long apiid,String propertytype);

    List<ApiParams> findApiParamsbypropertytype(Long apiid,String propertytype);

    ApiParams getBodyNoFormbyapiid(Long apiid,String propertytype,String keydefaultvalue,String keytype);

}
