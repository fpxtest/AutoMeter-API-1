package com.zoctan.api.controller;

import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.Performancereportsource;
import com.zoctan.api.service.PerformancereportsourceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* @author Season
* @date 2020/12/16
*/
@RestController
@RequestMapping("/performancereportsource")
public class PerformancereportsourceController {
@Resource
private PerformancereportsourceService performancereportsourceService;

@PostMapping
public Result add(@RequestBody Performancereportsource performancereportsource) {
performancereportsourceService.save(performancereportsource);
return ResultGenerator.genOkResult();
}

@DeleteMapping("/{id}")
public Result delete(@PathVariable Long id) {
performancereportsourceService.deleteById(id);
return ResultGenerator.genOkResult();
}

@PatchMapping
public Result update(@RequestBody Performancereportsource performancereportsource) {
performancereportsourceService.update(performancereportsource);
return ResultGenerator.genOkResult();
}

@GetMapping("/{id}")
public Result detail(@PathVariable Long id) {
Performancereportsource performancereportsource = performancereportsourceService.getById(id);
return ResultGenerator.genOkResult(performancereportsource);
}

@GetMapping
public Result list(@RequestParam(defaultValue = "0") Integer page,
@RequestParam(defaultValue = "0") Integer size) {
PageHelper.startPage(page, size);
List<Performancereportsource> list = performancereportsourceService.listAll();
PageInfo<Performancereportsource> pageInfo = PageInfo.of(list);
return ResultGenerator.genOkResult(pageInfo);
}
}
