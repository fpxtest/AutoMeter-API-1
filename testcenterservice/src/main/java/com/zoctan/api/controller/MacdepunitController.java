package com.zoctan.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.Macdepunit;
import com.zoctan.api.service.MacdepunitService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Zoctan
 * @date 2020/05/21
 */
@RestController
@RequestMapping("/macdepunit")
public class MacdepunitController {
    @Resource
    private MacdepunitService macdepunitService;

    @PostMapping
    public Result add(@RequestBody Macdepunit macdepunit) {
        macdepunitService.save(macdepunit);
        return ResultGenerator.genOkResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        macdepunitService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody Macdepunit macdepunit) {
        macdepunitService.update(macdepunit);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        Macdepunit macdepunit = macdepunitService.getById(id);
        return ResultGenerator.genOkResult(macdepunit);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Macdepunit> list = macdepunitService.listAll();
        PageInfo<Macdepunit> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    /**
     * 更新自己的资料
     */
    @PutMapping("/detail")
    public Result updateDeploy(@RequestBody final Macdepunit dic) {
        this.macdepunitService.updateMacAndDep(dic);
        return ResultGenerator.genOkResult();
    }

    /**
     * 输入框查询
     */
    @PostMapping("/search")
    public Result search(@RequestBody final Map<String, Object> param) {
        PageHelper.startPage((Integer) param.get("page"), (Integer) param.get("size"));
        final List<Macdepunit> list = this.macdepunitService.findMacAndDepWithName(param);
        final PageInfo<Macdepunit> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }
}
