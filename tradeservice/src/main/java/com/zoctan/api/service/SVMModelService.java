package com.zoctan.api.service;

import com.zoctan.api.core.service.Service;
import com.zoctan.api.entity.SVMModelTest;

/**
* @author Zoctan
* @date 2020/04/14
*/
public interface SVMModelService extends Service<SVMModelTest> {

    void callSVMPython(SVMModelTest svmModelTest);

}
