package com.zoctan.api.service;

import com.zoctan.api.entity.Mockapi;
import com.zoctan.api.entity.Mockapirespone;
import com.zoctan.api.core.service.Service;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2022/08/03
*/
public interface MockapiresponeService extends Service<Mockapirespone> {
    List<Mockapirespone> findMockapiresponeWithName(final Map<String, Object> params);

    /**
     * 更新字典内容
     *
     * @param params 参数
     * @return 用户列表
     */
    void updateMockapirespone(Mockapirespone params);
    void enableapirespone(long apiid,String status );
    void enablerespone(long id,String status);

    int ifexist(Condition condition);
}
