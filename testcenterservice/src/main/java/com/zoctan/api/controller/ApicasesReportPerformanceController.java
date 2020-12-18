package com.zoctan.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.ApicasesReportPerformance;
import com.zoctan.api.service.ApicasesReportPerformanceService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author SeasonFan
 * @date 2020/12/14
 */
@RestController
@RequestMapping("/apicases/report/performance")
public class ApicasesReportPerformanceController {
    @Resource
    private ApicasesReportPerformanceService apicasesReportPerformanceService;

    @PostMapping
    public Result add(@RequestBody ApicasesReportPerformance apicasesReportPerformance) {
        apicasesReportPerformanceService.save(apicasesReportPerformance);
        return ResultGenerator.genOkResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        apicasesReportPerformanceService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody ApicasesReportPerformance apicasesReportPerformance) {
        apicasesReportPerformanceService.update(apicasesReportPerformance);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        ApicasesReportPerformance apicasesReportPerformance = apicasesReportPerformanceService.getById(id);
        return ResultGenerator.genOkResult(apicasesReportPerformance);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<ApicasesReportPerformance> list = apicasesReportPerformanceService.listallresult();
        PageInfo<ApicasesReportPerformance> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    /**
     * 输入框查询
     */
    @PostMapping("/search")
    public Result search(@RequestBody final Map<String, Object> param) {
        PageHelper.startPage((Integer) param.get("page"), (Integer) param.get("size"));
        final List<ApicasesReportPerformance> list = this.apicasesReportPerformanceService.findApicasereportWithName(param);
        final PageInfo<ApicasesReportPerformance> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }
}
