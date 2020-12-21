package com.zoctan.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.ApicasesPerformancestatistics;
import com.zoctan.api.service.ApicasesPerformancestatisticsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author SeasonFan
 * @date 2020/12/07
 */
@RestController
@RequestMapping("/apicases/performancestatistics")
public class ApicasesPerformancestatisticsController {
    @Resource
    private ApicasesPerformancestatisticsService apicasesPerformancestatisticsService;

    @PostMapping
    public Result add(@RequestBody ApicasesPerformancestatistics apicasesPerformancestatistics) {
        apicasesPerformancestatisticsService.save(apicasesPerformancestatistics);
        return ResultGenerator.genOkResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        apicasesPerformancestatisticsService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody ApicasesPerformancestatistics apicasesPerformancestatistics) {
        apicasesPerformancestatisticsService.update(apicasesPerformancestatistics);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        ApicasesPerformancestatistics apicasesPerformancestatistics = apicasesPerformancestatisticsService.getById(id);
        return ResultGenerator.genOkResult(apicasesPerformancestatistics);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<ApicasesPerformancestatistics> list = apicasesPerformancestatisticsService.listallresult();
        PageInfo<ApicasesPerformancestatistics> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    /**
     * 输入框查询
     */
    @PostMapping("/search")
    public Result search(@RequestBody final Map<String, Object> param) {
        PageHelper.startPage((Integer) param.get("page"), (Integer) param.get("size"));
        final List<ApicasesPerformancestatistics> list = this.apicasesPerformancestatisticsService.findApicasereportWithName(param);
        final PageInfo<ApicasesPerformancestatistics> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }
}
