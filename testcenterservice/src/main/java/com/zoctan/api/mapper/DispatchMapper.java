package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.Dispatch;

import java.util.List;
import java.util.Map;

public interface DispatchMapper extends MyMapper<Dispatch> {
    List<Dispatch> findDispatchWithName(final Map<String, Object> params);

}