package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.SVMModelTest;

public interface SVMModelMapper extends MyMapper<SVMModelTest> {
    void callSVMPython(SVMModelTest svmModelTest);

}