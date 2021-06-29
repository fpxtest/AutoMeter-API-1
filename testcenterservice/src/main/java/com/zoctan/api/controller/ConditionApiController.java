package com.zoctan.api.controller;

import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.ConditionApi;
import com.zoctan.api.service.ConditionApiService;
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
@RequestMapping("/condition/api")
public class ConditionApiController {
@Resource
private ConditionApiService conditionApiService;

@PostMapping
public Result add(@RequestBody ConditionApi conditionApi) {
conditionApiService.save(conditionApi);
return ResultGenerator.genOkResult();
}

@DeleteMapping("/{id}")
public Result delete(@PathVariable Long id) {
conditionApiService.deleteById(id);
return ResultGenerator.genOkResult();
}

@PatchMapping
public Result update(@RequestBody ConditionApi conditionApi) {
conditionApiService.update(conditionApi);
return ResultGenerator.genOkResult();
}

@GetMapping("/{id}")
public Result detail(@PathVariable Long id) {
ConditionApi conditionApi = conditionApiService.getById(id);
return ResultGenerator.genOkResult(conditionApi);
}

@GetMapping
public Result list(@RequestParam(defaultValue = "0") Integer page,
@RequestParam(defaultValue = "0") Integer size) {
PageHelper.startPage(page, size);
List<ConditionApi> list = conditionApiService.listAll();
PageInfo<ConditionApi> pageInfo = PageInfo.of(list);
return ResultGenerator.genOkResult(pageInfo);
}
}
