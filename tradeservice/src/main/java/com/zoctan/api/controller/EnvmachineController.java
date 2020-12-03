package com.zoctan.api.controller;

import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.Envmachine;
import com.zoctan.api.service.EnvmachineService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* @author Zoctan
* @date 2020/04/20
*/
@RestController
@RequestMapping("/envmachine")
public class EnvmachineController {
@Resource
private EnvmachineService envmachineService;

@PostMapping
public Result add(@RequestBody Envmachine envmachine) {
envmachineService.save(envmachine);
return ResultGenerator.genOkResult();
}

@DeleteMapping("/{id}")
public Result delete(@PathVariable Long id) {
envmachineService.deleteById(id);
return ResultGenerator.genOkResult();
}

@PatchMapping
public Result update(@RequestBody Envmachine envmachine) {
envmachineService.update(envmachine);
return ResultGenerator.genOkResult();
}

@GetMapping("/{id}")
public Result detail(@PathVariable Long id) {
Envmachine envmachine = envmachineService.getById(id);
return ResultGenerator.genOkResult(envmachine);
}

@GetMapping
public Result list(@RequestParam(defaultValue = "0") Integer page,
@RequestParam(defaultValue = "0") Integer size) {
PageHelper.startPage(page, size);
List<Envmachine> list = envmachineService.listAll();
PageInfo<Envmachine> pageInfo = PageInfo.of(list);
return ResultGenerator.genOkResult(pageInfo);
}
}
