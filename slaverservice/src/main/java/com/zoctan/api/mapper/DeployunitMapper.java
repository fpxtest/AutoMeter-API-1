package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.Deployunit;
import org.apache.ibatis.annotations.Param;

public interface DeployunitMapper extends MyMapper<Deployunit> {

    Deployunit findDeployNameValueWithCode(@Param("deployunitname") String params,@Param("projectid")long projectid);

}