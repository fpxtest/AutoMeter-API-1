package com.zoctan.api.controller;

import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.ExecuteplanParams;
import com.zoctan.api.service.ExecuteplanParamsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* @author SeasonFan
* @date 2021/12/19
*/
@RestController
@RequestMapping("/executeplan/params")
public class ExecuteplanParamsController {
@Resource
private ExecuteplanParamsService executeplanParamsService;

@PostMapping
public Result add(@RequestBody ExecuteplanParams executeplanParams) {
executeplanParamsService.save(executeplanParams);
return ResultGenerator.genOkResult();
}

@DeleteMapping("/{id}")
public Result delete(@PathVariable Long id) {
executeplanParamsService.deleteById(id);
return ResultGenerator.genOkResult();
}

@PatchMapping
public Result update(@RequestBody ExecuteplanParams executeplanParams) {
executeplanParamsService.update(executeplanParams);
return ResultGenerator.genOkResult();
}

@GetMapping("/{id}")
public Result detail(@PathVariable Long id) {
ExecuteplanParams executeplanParams = executeplanParamsService.getById(id);
return ResultGenerator.genOkResult(executeplanParams);
}

@GetMapping
public Result list(@RequestParam(defaultValue = "0") Integer page,
@RequestParam(defaultValue = "0") Integer size) {
PageHelper.startPage(page, size);
List<ExecuteplanParams> list = executeplanParamsService.listAll();
PageInfo<ExecuteplanParams> pageInfo = PageInfo.of(list);
return ResultGenerator.genOkResult(pageInfo);
}
}
