package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.Executeplan;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

public interface ExecuteplanMapper extends MyMapper<Executeplan> {

    void updatetestplanstatus(@Param("id")Long id, @Param("status")String status);

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


    Executeplan findexplanWithid(@Param("id")Long id);

    int ifexist(Condition condition);

    List<Executeplan> getallexplan();

    List<Executeplan> getallexplanbytype(@Param("usetype")String usetype);

    Integer getexecuteplannum();
}