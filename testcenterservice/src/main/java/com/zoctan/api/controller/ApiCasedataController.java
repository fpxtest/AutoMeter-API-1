package com.zoctan.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.dto.Casedata;
import com.zoctan.api.entity.ApiCasedata;
import com.zoctan.api.service.ApiCasedataService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Zoctan
 * @date 2020/09/21
 */
@RestController
@RequestMapping("/api/casedata")
public class ApiCasedataController {
    @Resource
    private ApiCasedataService apiCasedataService;

    @PostMapping
    public Result add(@RequestBody Casedata apiCasedata) {
        apiCasedataService.save(apiCasedata);
        return ResultGenerator.genOkResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        apiCasedataService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody ApiCasedata apiCasedata) {
        apiCasedataService.update(apiCasedata);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        ApiCasedata apiCasedata = apiCasedataService.getById(id);
        return ResultGenerator.genOkResult(apiCasedata);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<ApiCasedata> list = apiCasedataService.listAll();
        PageInfo<ApiCasedata> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    /**
     * 根据caseid和参数类型返回参数值
     */
    @PostMapping("/casevalue")
    public Result casevalue(@RequestBody final Map<String, Object> param) {
        final List<ApiCasedata> list = this.apiCasedataService.getparamvaluebycaseidandtype(param);
        final PageInfo<ApiCasedata> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);    }

//    /**
//     * 根据caseid和参数类型返回参数值
//     */
//    @DeleteMapping("/delecasevalue")
//    public Result deletecasevalue(@RequestBody final Map<String, Object> param) {
//        this.apiCasedataService.deleteparamvaluebycaseidandtype(param);
//        return ResultGenerator.genOkResult();
//    }
}
