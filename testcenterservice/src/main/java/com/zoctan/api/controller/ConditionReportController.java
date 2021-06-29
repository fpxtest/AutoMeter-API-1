package com.zoctan.api.controller;

import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.ConditionReport;
import com.zoctan.api.service.ConditionReportService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* @author SeasonFan
* @date 2021/05/30
*/
@RestController
@RequestMapping("/condition/report")
public class ConditionReportController {
@Resource
private ConditionReportService conditionReportService;

@PostMapping
public Result add(@RequestBody ConditionReport conditionReport) {
conditionReportService.save(conditionReport);
return ResultGenerator.genOkResult();
}

@DeleteMapping("/{id}")
public Result delete(@PathVariable Long id) {
conditionReportService.deleteById(id);
return ResultGenerator.genOkResult();
}

@PatchMapping
public Result update(@RequestBody ConditionReport conditionReport) {
conditionReportService.update(conditionReport);
return ResultGenerator.genOkResult();
}

@GetMapping("/{id}")
public Result detail(@PathVariable Long id) {
ConditionReport conditionReport = conditionReportService.getById(id);
return ResultGenerator.genOkResult(conditionReport);
}

@GetMapping
public Result list(@RequestParam(defaultValue = "0") Integer page,
@RequestParam(defaultValue = "0") Integer size) {
PageHelper.startPage(page, size);
List<ConditionReport> list = conditionReportService.listAll();
PageInfo<ConditionReport> pageInfo = PageInfo.of(list);
return ResultGenerator.genOkResult(pageInfo);
}
}
