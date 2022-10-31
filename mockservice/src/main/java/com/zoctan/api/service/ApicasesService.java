package com.zoctan.api.service;

import com.zoctan.api.core.service.Service;
import com.zoctan.api.entity.Apicases;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* @author Zoctan
* @date 2020/09/11
*/
public interface ApicasesService extends Service<Apicases> {
    /**
     * 按微服务名或者协议名获取微服务内容
     *
     * @param params 参数
     * @return 微服务列表
     */
    List<Apicases> findApiCaseWithName(final Map<String, Object> params);

    /**
     * 更新微服务内容
     *
     * @param params 参数
     * @return 微服务列表
     */
    void updateApicase(Apicases params);

    Apicases GetCaseByCaseID(@Param("id")long caseid);

}
