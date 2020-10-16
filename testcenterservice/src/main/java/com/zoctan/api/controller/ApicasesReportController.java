package com.zoctan.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.ApicasesReport;
import com.zoctan.api.service.ApicasesReportService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author SeasonFan
 * @date 2020/10/16
 */
@RestController
@RequestMapping("/apicases/report")
public class ApicasesReportController {
    @Resource
    private ApicasesReportService apicasesReportService;

    @PostMapping
    public Result add(@RequestBody ApicasesReport apicasesReport) {
        apicasesReportService.save(apicasesReport);
        return ResultGenerator.genOkResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        apicasesReportService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody ApicasesReport apicasesReport) {
        apicasesReportService.update(apicasesReport);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        ApicasesReport apicasesReport = apicasesReportService.getById(id);
        return ResultGenerator.genOkResult(apicasesReport);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<ApicasesReport> list = apicasesReportService.listAll();
        PageInfo<ApicasesReport> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    /**
     * 输入框查询
     */
    @PostMapping("/search")
    public Result search(@RequestBody final Map<String, Object> param) {
        PageHelper.startPage((Integer) param.get("page"), (Integer) param.get("size"));
        final List<ApicasesReport> list = this.apicasesReportService.findApiCasereportWithName(param);
        final PageInfo<ApicasesReport> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }
}
