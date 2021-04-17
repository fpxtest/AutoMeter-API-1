package com.zoctan.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.dto.TestPlanCaseReportStatics;
import com.zoctan.api.entity.ApicasesReportstatics;
import com.zoctan.api.service.ApicasesReportstaticsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SeasonFan
 * @date 2021/04/01
 */
@RestController
@RequestMapping("/apicases/reportstatics")
public class ApicasesReportstaticsController {
    @Resource
    private ApicasesReportstaticsService apicasesReportstaticsService;

    @PostMapping
    public Result add(@RequestBody ApicasesReportstatics apicasesReportstatics) {
        apicasesReportstaticsService.save(apicasesReportstatics);
        return ResultGenerator.genOkResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        apicasesReportstaticsService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody ApicasesReportstatics apicasesReportstatics) {
        apicasesReportstaticsService.update(apicasesReportstatics);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        ApicasesReportstatics apicasesReportstatics = apicasesReportstaticsService.getById(id);
        return ResultGenerator.genOkResult(apicasesReportstatics);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<ApicasesReportstatics> list = apicasesReportstaticsService.listAll();
        PageInfo<ApicasesReportstatics> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    @GetMapping("/getplanstatics")
    public Result getplanstatics() {

        List<TestPlanCaseReportStatics> testPlanCaseReportStaticsList=new ArrayList<>();

        for(int i=1;i<10;i++)
        {
            TestPlanCaseReportStatics testPlanCaseReportStatics=new TestPlanCaseReportStatics();
            testPlanCaseReportStatics.setTestplanName("accountservice"+i);
            List<Integer> statics=new ArrayList<>();
            statics.add(10*i);
            statics.add(20*i);
            statics.add(30*i);
            statics.add(40*i);
            statics.add(50*i);
            statics.add(60*i);
            statics.add(70*i);
            testPlanCaseReportStatics.setPlanCaseStatics(statics);
            testPlanCaseReportStaticsList.add(testPlanCaseReportStatics);
        }

        //ApicasesReportstatics apicasesReportstatics = apicasesReportstaticsService.getById(id);
        return ResultGenerator.genOkResult(testPlanCaseReportStaticsList);
    }
}
