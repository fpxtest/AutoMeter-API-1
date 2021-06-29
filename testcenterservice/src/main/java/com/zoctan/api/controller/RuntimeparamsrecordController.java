package com.zoctan.api.controller;

import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.Runtimeparamsrecord;
import com.zoctan.api.service.RuntimeparamsrecordService;
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
@RequestMapping("/runtimeparamsrecord")
public class RuntimeparamsrecordController {
@Resource
private RuntimeparamsrecordService runtimeparamsrecordService;

@PostMapping
public Result add(@RequestBody Runtimeparamsrecord runtimeparamsrecord) {
runtimeparamsrecordService.save(runtimeparamsrecord);
return ResultGenerator.genOkResult();
}

@DeleteMapping("/{id}")
public Result delete(@PathVariable Long id) {
runtimeparamsrecordService.deleteById(id);
return ResultGenerator.genOkResult();
}

@PatchMapping
public Result update(@RequestBody Runtimeparamsrecord runtimeparamsrecord) {
runtimeparamsrecordService.update(runtimeparamsrecord);
return ResultGenerator.genOkResult();
}

@GetMapping("/{id}")
public Result detail(@PathVariable Long id) {
Runtimeparamsrecord runtimeparamsrecord = runtimeparamsrecordService.getById(id);
return ResultGenerator.genOkResult(runtimeparamsrecord);
}

@GetMapping
public Result list(@RequestParam(defaultValue = "0") Integer page,
@RequestParam(defaultValue = "0") Integer size) {
PageHelper.startPage(page, size);
List<Runtimeparamsrecord> list = runtimeparamsrecordService.listAll();
PageInfo<Runtimeparamsrecord> pageInfo = PageInfo.of(list);
return ResultGenerator.genOkResult(pageInfo);
}
}
