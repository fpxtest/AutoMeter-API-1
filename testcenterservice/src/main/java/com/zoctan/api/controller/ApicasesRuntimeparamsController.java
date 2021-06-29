package com.zoctan.api.controller;

import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.ApicasesRuntimeparams;
import com.zoctan.api.service.ApicasesRuntimeparamsService;
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
@RequestMapping("/apicases/runtimeparams")
public class ApicasesRuntimeparamsController {
@Resource
private ApicasesRuntimeparamsService apicasesRuntimeparamsService;

@PostMapping
public Result add(@RequestBody ApicasesRuntimeparams apicasesRuntimeparams) {
apicasesRuntimeparamsService.save(apicasesRuntimeparams);
return ResultGenerator.genOkResult();
}

@DeleteMapping("/{id}")
public Result delete(@PathVariable Long id) {
apicasesRuntimeparamsService.deleteById(id);
return ResultGenerator.genOkResult();
}

@PatchMapping
public Result update(@RequestBody ApicasesRuntimeparams apicasesRuntimeparams) {
apicasesRuntimeparamsService.update(apicasesRuntimeparams);
return ResultGenerator.genOkResult();
}

@GetMapping("/{id}")
public Result detail(@PathVariable Long id) {
ApicasesRuntimeparams apicasesRuntimeparams = apicasesRuntimeparamsService.getById(id);
return ResultGenerator.genOkResult(apicasesRuntimeparams);
}

@GetMapping
public Result list(@RequestParam(defaultValue = "0") Integer page,
@RequestParam(defaultValue = "0") Integer size) {
PageHelper.startPage(page, size);
List<ApicasesRuntimeparams> list = apicasesRuntimeparamsService.listAll();
PageInfo<ApicasesRuntimeparams> pageInfo = PageInfo.of(list);
return ResultGenerator.genOkResult(pageInfo);
}
}
