package com.zoctan.api.service;

import com.zoctan.api.entity.Deployunit;
import com.zoctan.api.core.service.Service;

/**
* @author Zoctan
* @date 2020/04/17
*/
public interface DeployunitService extends Service<Deployunit> {
    Deployunit findDeployNameValueWithCode(String params,long projectid);

}
