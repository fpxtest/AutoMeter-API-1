package com.zoctan.api.service;

import com.zoctan.api.core.service.Service;
import com.zoctan.api.entity.TestvariablesValue;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2021/07/17
*/
public interface TestvariablesValueService extends Service<TestvariablesValue> {
    List<TestvariablesValue> findtestvariablesvalueWithName(final Map<String, Object> params);

    /**
     * 更新字典内容
     *
     * @param params 参数
     * @return 用户列表
     */
    void updatetestvariablesvalue(TestvariablesValue params);

    TestvariablesValue findtestvariablesvalue(Long planid,Long caseid,String batchname,String variablesname);

    int ifexist(Condition condition);
}
