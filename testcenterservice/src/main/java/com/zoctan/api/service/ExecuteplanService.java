package com.zoctan.api.service;

import com.zoctan.api.core.service.Service;
import com.zoctan.api.dto.Testplanandbatch;
import com.zoctan.api.entity.Executeplan;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author SeasonFan
* @date 2020/09/27
*/
public interface ExecuteplanService extends Service<Executeplan> {

    /**
     * 按条件查询字典内容
     *
     * @param params 参数
     * @return 用户列表
     */
    List<Executeplan> findexplanWithName(final Map<String, Object> params);

    /**
     * 更新字典内容
     *
     * @param params 参数
     * @return 用户列表
     */
    void updateexecuteplanname(Executeplan params);

    void executeplancase(final List<Testplanandbatch> executeplan,String Exectype);

    void updatetestplanstatus(Long id, String status);

    Executeplan findexplanWithid(Long id);

    int ifexist(Condition condition);

    List<Executeplan> getallexplan();
    List<Executeplan> getallexplanbytype(String usetype);

    Integer getexecuteplannum();

    List<String> getstaticsplan();


}
