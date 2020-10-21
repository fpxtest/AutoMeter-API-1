package com.zoctan.api.controller;

import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.dto.TestplanCase;
import com.zoctan.api.entity.ExecuteplanTestcase;
import com.zoctan.api.service.TestPlanCaseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Zoctan
 * @date 2020/04/17
 */
@RestController
@RequestMapping("/exectestplancase")
public class TestPlanCaseController {
    @Resource
    private TestPlanCaseService tpcservice;

    @PostMapping("/exec")
    public Result exec(@RequestBody List<TestplanCase> plancase) {
        // 调用testcenter需要模拟下admin登录，调用Request URL: http://localhost:8080/account/token  {name: "admin", password: "admin123"}
        // 在请求头里面加上Authorization = token

        for (TestplanCase tc:plancase
             ) {
            tpcservice.executeplancase(tc.getTestplanid(),tc.getCaseid(),tc.getDeployname(),tc.getJmeterpath(),tc.getJmxpath());
        }

        return ResultGenerator.genOkResult();
    }

    @GetMapping("/test")
    public Result gettest(@RequestBody ExecuteplanTestcase plancase) {
        //tpcservice.executeplancase(plancase.getExecuteplanid(),plancase.getTestcaseid());
        return ResultGenerator.genOkResult();
    }

}
