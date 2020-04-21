package com.zoctan.api.controller;

import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.Deployunit;
import com.zoctan.api.service.DeployunitService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* @author Zoctan
* @date 2020/04/17
*/
@RestController
@RequestMapping("/deployunit")
public class DeployunitController {
@Resource
private DeployunitService deployunitService;

@PostMapping
public Result add(@RequestBody Deployunit deployunit) {
deployunitService.save(deployunit);
return ResultGenerator.genOkResult();
}

@DeleteMapping("/{id}")
public Result delete(@PathVariable Long id) {
deployunitService.deleteById(id);
return ResultGenerator.genOkResult();
}

@PatchMapping
public Result update(@RequestBody Deployunit deployunit) {
deployunitService.update(deployunit);
return ResultGenerator.genOkResult();
}

@GetMapping("/{id}")
public Result detail(@PathVariable Long id) {
Deployunit deployunit = deployunitService.getById(id);
return ResultGenerator.genOkResult(deployunit);
}

@GetMapping
public Result list(@RequestParam(defaultValue = "0") Integer page,
@RequestParam(defaultValue = "0") Integer size) {
PageHelper.startPage(page, size);
List<Deployunit> list = deployunitService.listAll();
PageInfo<Deployunit> pageInfo = PageInfo.of(list);
return ResultGenerator.genOkResult(pageInfo);
}
}
