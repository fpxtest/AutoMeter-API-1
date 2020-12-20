package com.zoctan.api.controller;

import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.Dispatch;
import com.zoctan.api.service.DispatchService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* @author SeasonFan
* @date 2020/11/21
*/
@RestController
@RequestMapping("/dispatch")
public class DispatchController {
@Resource
private DispatchService dispatchService;

@PostMapping
public Result add(@RequestBody Dispatch dispatch) {
dispatchService.save(dispatch);
return ResultGenerator.genOkResult();
}

@DeleteMapping("/{id}")
public Result delete(@PathVariable Long id) {
dispatchService.deleteById(id);
return ResultGenerator.genOkResult();
}

@PatchMapping
public Result update(@RequestBody Dispatch dispatch) {
dispatchService.update(dispatch);
return ResultGenerator.genOkResult();
}

@GetMapping("/{id}")
public Result detail(@PathVariable Long id) {
Dispatch dispatch = dispatchService.getById(id);
return ResultGenerator.genOkResult(dispatch);
}

@GetMapping
public Result list(@RequestParam(defaultValue = "0") Integer page,
@RequestParam(defaultValue = "0") Integer size) {
PageHelper.startPage(page, size);
List<Dispatch> list = dispatchService.listAll();
PageInfo<Dispatch> pageInfo = PageInfo.of(list);
return ResultGenerator.genOkResult(pageInfo);
}
}