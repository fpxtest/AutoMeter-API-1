package com.zoctan.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.ConditionDb;
import com.zoctan.api.service.ConditionDbService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author SeasonFan
 * @date 2021/05/30
 */
@RestController
@RequestMapping("/condition/db")
public class ConditionDbController {
    @Resource
    private ConditionDbService conditionDbService;

    @PostMapping
    public Result add(@RequestBody ConditionDb conditionDb) {
        conditionDbService.save(conditionDb);
        return ResultGenerator.genOkResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        conditionDbService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody ConditionDb conditionDb) {
        conditionDbService.update(conditionDb);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        ConditionDb conditionDb = conditionDbService.getById(id);
        return ResultGenerator.genOkResult(conditionDb);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<ConditionDb> list = conditionDbService.listAll();
        PageInfo<ConditionDb> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    /**
     * 输入框查询
     */
    @PostMapping("/search")
    public Result search(@RequestBody final Map<String, Object> param) {
        Integer page= Integer.parseInt(param.get("page").toString());
        Integer size= Integer.parseInt(param.get("size").toString());
        PageHelper.startPage(page, size);
        final List<ConditionDb> list = this.conditionDbService.finddbconditionWithName(param);
        final PageInfo<ConditionDb> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }
}
