package com.zoctan.api.service.impl;

import com.zoctan.api.core.service.AbstractService;
import com.zoctan.api.entity.Dictionary;
import com.zoctan.api.entity.SVMModelTest;
import com.zoctan.api.mapper.DictionaryMapper;
import com.zoctan.api.service.SVMModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

/**
 * @author Zoctan
 * @date 2020/04/14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SVMModelServiceImpl extends AbstractService<SVMModelTest> implements SVMModelService {
    @Autowired(required = false)
    private DictionaryMapper dictionaryMapper;

    @Override
    public void callSVMPython(SVMModelTest svmModelTest) {
        Process proc = null;
        try {
            List<Dictionary> pythonpath = dictionaryMapper.findDicNameValueWithCode("pythonpath");
            String path=pythonpath.get(0).getDicitmevalue();
            String pythonfile=path+"/"+svmModelTest.getPythonfile();
            String[] args1 = new String[]{"python", pythonfile, svmModelTest.getStockids(),svmModelTest.getModelparams(),svmModelTest.getAccountchange(),svmModelTest.getStartTime().toString(),svmModelTest.getEndTime().toString()};
            try {
                proc = Runtime.getRuntime().exec(args1);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (Exception ex) {
        }
    }
}
