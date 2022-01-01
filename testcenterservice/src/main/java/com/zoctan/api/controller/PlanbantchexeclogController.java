package com.zoctan.api.controller;

import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.Planbantchexeclog;
import com.zoctan.api.service.PlanbantchexeclogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* @author SeasonFan
* @date 2021/12/26
*/
@RestController
@RequestMapping("/planbantchexeclog")
public class PlanbantchexeclogController {
@Resource
private PlanbantchexeclogService planbantchexeclogService;

@PostMapping
public Result add(@RequestBody Planbantchexeclog planbantchexeclog) {
planbantchexeclogService.save(planbantchexeclog);
return ResultGenerator.genOkResult();
}

@DeleteMapping("/{id}")
public Result delete(@PathVariable Long id) {
planbantchexeclogService.deleteById(id);
return ResultGenerator.genOkResult();
}

@PatchMapping
public Result update(@RequestBody Planbantchexeclog planbantchexeclog) {
planbantchexeclogService.update(planbantchexeclog);
return ResultGenerator.genOkResult();
}

@GetMapping("/{id}")
public Result detail(@PathVariable Long id) {
Planbantchexeclog planbantchexeclog = planbantchexeclogService.getById(id);
return ResultGenerator.genOkResult(planbantchexeclog);
}

@GetMapping
public Result list(@RequestParam(defaultValue = "0") Integer page,
@RequestParam(defaultValue = "0") Integer size) {
PageHelper.startPage(page, size);
List<Planbantchexeclog> list = planbantchexeclogService.listAll();
PageInfo<Planbantchexeclog> pageInfo = PageInfo.of(list);
return ResultGenerator.genOkResult(pageInfo);
}
}
