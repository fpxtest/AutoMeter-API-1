package com.zoctan.api.service.impl;

import com.zoctan.api.core.service.AbstractService;
import com.zoctan.api.entity.Dictionary;
import com.zoctan.api.mapper.DictionaryMapper;
import com.zoctan.api.service.DictionaryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author Zoctan
* @date 2020/04/16
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class DictionaryServiceImpl extends AbstractService<Dictionary> implements DictionaryService {
@Resource
private DictionaryMapper dictionaryMapper;

    @Override
    public List<Dictionary> findDicNameValueWithCode(String params) {
        return this.dictionaryMapper.findDicNameValueWithCode(params);
    }

    @Override
    public List<Dictionary> findDicWithName(Map<String, Object> params) {
        return this.dictionaryMapper.findDicWithName(params);
    }

    @Override
    public void updateDic(Dictionary params)
   {
       dictionaryMapper.updateDic(params);
   }


}
