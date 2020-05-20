package com.zoctan.api.controller;

import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.ApiParams;
import com.zoctan.api.service.ApiParamsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* @author Zoctan
* @date 2020/05/20
*/
@RestController
@RequestMapping("/api/params")
public class ApiParamsController {
@Resource
private ApiParamsService apiParamsService;

@PostMapping
public Result add(@RequestBody ApiParams apiParams) {
apiParamsService.save(apiParams);
return ResultGenerator.genOkResult();
}

@DeleteMapping("/{id}")
public Result delete(@PathVariable Long id) {
apiParamsService.deleteById(id);
return ResultGenerator.genOkResult();
}

@PatchMapping
public Result update(@RequestBody ApiParams apiParams) {
apiParamsService.update(apiParams);
return ResultGenerator.genOkResult();
}

@GetMapping("/{id}")
public Result detail(@PathVariable Long id) {
ApiParams apiParams = apiParamsService.getById(id);
return ResultGenerator.genOkResult(apiParams);
}

@GetMapping
public Result list(@RequestParam(defaultValue = "0") Integer page,
@RequestParam(defaultValue = "0") Integer size) {
PageHelper.startPage(page, size);
List<ApiParams> list = apiParamsService.listAll();
PageInfo<ApiParams> pageInfo = PageInfo.of(list);
return ResultGenerator.genOkResult(pageInfo);
}
}
