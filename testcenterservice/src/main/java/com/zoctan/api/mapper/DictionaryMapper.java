package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.Dictionary;

import java.util.List;
import java.util.Map;

public interface DictionaryMapper extends MyMapper<Dictionary> {

    /**
     * 按条件获取字典项名
     *
     * @param params 参数
     * @return 用户列表
     */
    List<Dictionary> findDicNameValueWithCode(String params);


    /**
     * 按字典名或者字典项名获取字典内容
     *
     * @param params 参数
     * @return 用户列表
     */
    List<Dictionary> findDicWithName(final Map<String, Object> params);

    /**
     * 更新字典内容
     *
     * @param params 参数
     * @return 用户列表
     */
    void updateDic(Dictionary params);

}