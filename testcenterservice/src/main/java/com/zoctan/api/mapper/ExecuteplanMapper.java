package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.Executeplan;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

public interface ExecuteplanMapper extends MyMapper<Executeplan> {
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

    void updatetestplanstatus(@Param("id")Long id, @Param("status")String status);

    Executeplan findexplanWithid(@Param("id")Long id);

    int ifexist(Condition condition);

    List<Executeplan> getallexplan(@Param("projectid")long projectid);

    List<Executeplan> getallexplanbytype(@Param("usetype")String usetype,@Param("projectid")long projectid);

    Integer getexecuteplannum(@Param("projectid")long projectid);

    List<String> getstaticsplan(@Param("projectid")long projectid);

    void createNewTable(@Param("tableName") String tableName);

}