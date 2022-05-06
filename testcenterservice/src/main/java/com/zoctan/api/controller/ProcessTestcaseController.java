package com.zoctan.api.controller;

import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.ProcessTestcase;
import com.zoctan.api.service.ProcessTestcaseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* @author SeasonFan
* @date 2022/04/27
*/
@RestController
@RequestMapping("/process/testcase")
public class ProcessTestcaseController {
@Resource
private ProcessTestcaseService processTestcaseService;

@PostMapping
public Result add(@RequestBody ProcessTestcase processTestcase) {
processTestcaseService.save(processTestcase);
return ResultGenerator.genOkResult();
}

@DeleteMapping("/{id}")
public Result delete(@PathVariable Long id) {
processTestcaseService.deleteById(id);
return ResultGenerator.genOkResult();
}

@PatchMapping
public Result update(@RequestBody ProcessTestcase processTestcase) {
processTestcaseService.update(processTestcase);
return ResultGenerator.genOkResult();
}

@GetMapping("/{id}")
public Result detail(@PathVariable Long id) {
ProcessTestcase processTestcase = processTestcaseService.getById(id);
return ResultGenerator.genOkResult(processTestcase);
}

@GetMapping
public Result list(@RequestParam(defaultValue = "0") Integer page,
@RequestParam(defaultValue = "0") Integer size) {
PageHelper.startPage(page, size);
List<ProcessTestcase> list = processTestcaseService.listAll();
PageInfo<ProcessTestcase> pageInfo = PageInfo.of(list);
return ResultGenerator.genOkResult(pageInfo);
}
}
