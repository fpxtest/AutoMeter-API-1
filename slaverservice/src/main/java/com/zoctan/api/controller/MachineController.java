package com.zoctan.api.controller;

import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.Machine;
import com.zoctan.api.service.MachineService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* @author Zoctan
* @date 2020/04/15
*/
@RestController
@RequestMapping("/machine")
public class MachineController {
@Resource
private MachineService machineService;

@PostMapping
public Result add(@RequestBody Machine machine) {
machineService.save(machine);
return ResultGenerator.genOkResult();
}

@DeleteMapping("/{id}")
public Result delete(@PathVariable Long id) {
machineService.deleteById(id);
return ResultGenerator.genOkResult();
}

@PatchMapping
public Result update(@RequestBody Machine machine) {
machineService.update(machine);
return ResultGenerator.genOkResult();
}

@GetMapping("/{id}")
public Result detail(@PathVariable Long id) {
Machine machine = machineService.getById(id);
return ResultGenerator.genOkResult(machine);
}

@GetMapping
public Result list(@RequestParam(defaultValue = "0") Integer page,
@RequestParam(defaultValue = "0") Integer size) {
PageHelper.startPage(page, size);
List<Machine> list = machineService.listAll();
PageInfo<Machine> pageInfo = PageInfo.of(list);
return ResultGenerator.genOkResult(pageInfo);
}
}
