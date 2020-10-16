package com.zoctan.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.dto.ApicasewithStatu;
import com.zoctan.api.entity.Apicases;
import com.zoctan.api.entity.ExecuteplanTestcase;
import com.zoctan.api.service.ApicasesService;
import com.zoctan.api.service.ExecuteplanTestcaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author SeasonFan
 * @date 2020/10/12
 */
@RestController
@RequestMapping("/executeplan/testcase")
public class ExecuteplanTestcaseController {
    @Resource
    private ExecuteplanTestcaseService executeplanTestcaseService;
    @Autowired
    private ApicasesService apicaseservice;


    @PostMapping
    public Result add(@RequestBody ExecuteplanTestcase executeplanTestcase) {
        executeplanTestcaseService.save(executeplanTestcase);
        return ResultGenerator.genOkResult();
    }

    @PostMapping("/addcases")
    public Result addcase(@RequestBody final List<ExecuteplanTestcase> executeplanTestcase) {
        executeplanTestcaseService.savetestplancase(executeplanTestcase);
        return ResultGenerator.genOkResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        executeplanTestcaseService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody ExecuteplanTestcase executeplanTestcase) {
        executeplanTestcaseService.update(executeplanTestcase);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        ExecuteplanTestcase executeplanTestcase = executeplanTestcaseService.getById(id);
        return ResultGenerator.genOkResult(executeplanTestcase);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<ExecuteplanTestcase> list = executeplanTestcaseService.listAll();
        PageInfo<ExecuteplanTestcase> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    /**
     * 根据caseid和参数类型返回参数值
     */
    @PostMapping("/getcasebydeployandapi")
    public Result casevalue(@RequestBody final Map<String, Object> param) {
        final List<ExecuteplanTestcase> plancaselist = this.executeplanTestcaseService.findcasebydeployandapi(param);
        final List<Apicases> apicaselist = this.apicaseservice.findApiCaseWithName(param);
        List<ApicasewithStatu> lastresult = new ArrayList<ApicasewithStatu>();
        for (Apicases ac : apicaselist) {
            Boolean flag = false;
            System.out.println("外循环casename...................: "+ac.getCasename());
            ApicasewithStatu et = null;
            for (int i = 0; i < plancaselist.size(); i++) {
                if (plancaselist.get(i).getTestcaseid() == ac.getId()) {
                    System.out.println("caseid...................: "+plancaselist.get(i).getTestcaseid());
                    et = new ApicasewithStatu();
                    et.setApiname(ac.getApiname());
                    et.setDeployunitname(ac.getDeployunitname());
                    et.setExpect(ac.getExpect());
                    et.setCasename(ac.getCasename());
                    et.setId(ac.getId());
                    et.setStatus(true);
                    flag = true;
                    lastresult.add(et);
                    break;
                }
            }
            System.out.println("外循环flag...................: "+flag);
            if (!flag) {
                et = new ApicasewithStatu();
                et.setApiname(ac.getApiname());
                et.setDeployunitname(ac.getDeployunitname());
                et.setExpect(ac.getExpect());
                et.setCasename(ac.getCasename());
                et.setId(ac.getId());
                et.setStatus(false);
                lastresult.add(et);
            }
        }
        final PageInfo<ApicasewithStatu> pageInfo = new PageInfo<>(lastresult);
        return ResultGenerator.genOkResult(pageInfo);
    }

    @PostMapping("/removecases")
    public Result removeplancase(@RequestBody final List<ExecuteplanTestcase> executeplanTestcase) {
        executeplanTestcaseService.removeexecuteplantestcase(executeplanTestcase);
        return ResultGenerator.genOkResult();
    }

}
