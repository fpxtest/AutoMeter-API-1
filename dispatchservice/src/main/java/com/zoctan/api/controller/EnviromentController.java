package com.zoctan.api.controller;

import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.Enviroment;
import com.zoctan.api.service.EnviromentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* @author Zoctan
* @date 2020/04/18
*/
@RestController
@RequestMapping("/enviroment")
public class EnviromentController {
@Resource
private EnviromentService enviromentService;

@PostMapping
public Result add(@RequestBody Enviroment enviroment) {
enviromentService.save(enviroment);
return ResultGenerator.genOkResult();
}

@DeleteMapping("/{id}")
public Result delete(@PathVariable Long id) {
enviromentService.deleteById(id);
return ResultGenerator.genOkResult();
}

@PatchMapping
public Result update(@RequestBody Enviroment enviroment) {
enviromentService.update(enviroment);
return ResultGenerator.genOkResult();
}

@GetMapping("/{id}")
public Result detail(@PathVariable Long id) {
Enviroment enviroment = enviromentService.getById(id);
return ResultGenerator.genOkResult(enviroment);
}

@GetMapping
public Result list(@RequestParam(defaultValue = "0") Integer page,
@RequestParam(defaultValue = "0") Integer size) {
PageHelper.startPage(page, size);
List<Enviroment> list = enviromentService.listAll();
PageInfo<Enviroment> pageInfo = PageInfo.of(list);
return ResultGenerator.genOkResult(pageInfo);
}
}
