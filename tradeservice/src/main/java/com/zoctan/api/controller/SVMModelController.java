package com.zoctan.api.controller;

import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.SVMModelTest;
import com.zoctan.api.service.SVMModelService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Zoctan
 * @date 2020/04/17
 */
@RestController
@RequestMapping("/svmmodel")
public class SVMModelController {
    @Resource
    private SVMModelService svmModelService;

    @PostMapping("/testoneaccount")
    public Result testoneaccount(@RequestBody SVMModelTest svmModelTest) {
        svmModelService.callSVMPython(svmModelTest);
        return ResultGenerator.genOkResult();
    }

    @PostMapping("/testmutilaccount")
    public Result testmutilaccount(@RequestBody SVMModelTest svmModelTest) {
        svmModelService.callSVMPython(svmModelTest);
        return ResultGenerator.genOkResult();
    }
}
