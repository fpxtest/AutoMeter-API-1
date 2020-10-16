package com.zoctan.api.controller;

import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.Test;
import com.zoctan.api.service.TestService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* @author Zoctan
* @date 2020/04/14
*/
@RestController
@RequestMapping("/test")
public class TestController {
@Resource
private TestService testService;

@PostMapping
public Result add(@RequestBody Test test) {
testService.save(test);
return ResultGenerator.genOkResult();
}

@DeleteMapping("/{id}")
public Result delete(@PathVariable Long id) {
testService.deleteById(id);
return ResultGenerator.genOkResult();
}

@PatchMapping
public Result update(@RequestBody Test test) {
testService.update(test);
return ResultGenerator.genOkResult();
}

@GetMapping("/{id}")
public Result detail(@PathVariable Long id) {
Test test = testService.getById(id);
return ResultGenerator.genOkResult(test);
}

@GetMapping
public Result list(@RequestParam(defaultValue = "0") Integer page,
@RequestParam(defaultValue = "0") Integer size) {
PageHelper.startPage(page, size);
List<Test> list = testService.listAll();
PageInfo<Test> pageInfo = PageInfo.of(list);
return ResultGenerator.genOkResult(pageInfo);
}
}
