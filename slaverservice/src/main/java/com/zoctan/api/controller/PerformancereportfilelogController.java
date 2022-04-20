package com.zoctan.api.controller;

import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.Performancereportfilelog;
import com.zoctan.api.service.PerformancereportfilelogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Season
 * @date 2022/04/15
 */
@RestController
@RequestMapping("/performancereportfilelog")
public class PerformancereportfilelogController {
    @Resource
    private PerformancereportfilelogService performancereportfilelogService;

    @PostMapping
    public Result add(@RequestBody Performancereportfilelog performancereportfilelog) {
        performancereportfilelogService.save(performancereportfilelog);
        return ResultGenerator.genOkResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        performancereportfilelogService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody Performancereportfilelog performancereportfilelog) {
        performancereportfilelogService.update(performancereportfilelog);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        Performancereportfilelog performancereportfilelog = performancereportfilelogService.getById(id);
        return ResultGenerator.genOkResult(performancereportfilelog);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Performancereportfilelog> list = performancereportfilelogService.listAll();
        PageInfo<Performancereportfilelog> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }
}
