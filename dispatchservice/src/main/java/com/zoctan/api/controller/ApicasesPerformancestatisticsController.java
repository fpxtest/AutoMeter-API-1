package com.zoctan.api.controller;

import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.ApicasesPerformancestatistics;
import com.zoctan.api.service.ApicasesPerformancestatisticsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* @author Season
* @date 2020/12/17
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
List<ApicasesPerformancestatistics> list = apicasesPerformancestatisticsService.listAll();
PageInfo<ApicasesPerformancestatistics> pageInfo = PageInfo.of(list);
return ResultGenerator.genOkResult(pageInfo);
}
}
