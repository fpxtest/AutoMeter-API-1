package com.zoctan.api.controller;

import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.Testvariables;
import com.zoctan.api.entity.TestvariablesValue;
import com.zoctan.api.service.TestvariablesValueService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author SeasonFan
 * @date 2021/07/17
 */
@RestController
@RequestMapping("/testvariables/value")
public class TestvariablesValueController {
    @Resource
    private TestvariablesValueService testvariablesValueService;

    @PostMapping
    public Result add(@RequestBody TestvariablesValue testvariablesValue) {
        testvariablesValueService.save(testvariablesValue);
        return ResultGenerator.genOkResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        testvariablesValueService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody TestvariablesValue testvariablesValue) {
        testvariablesValueService.update(testvariablesValue);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        TestvariablesValue testvariablesValue = testvariablesValueService.getById(id);
        return ResultGenerator.genOkResult(testvariablesValue);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<TestvariablesValue> list = testvariablesValueService.listAll();
        PageInfo<TestvariablesValue> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    /**
     * 更新自己的资料
     */
    @PutMapping("/detail")
    public Result updateDeploy(@RequestBody final TestvariablesValue dic) {
        this.testvariablesValueService.updatetestvariablesvalue(dic);
        return ResultGenerator.genOkResult();
    }

    /**
     * 输入框查询
     */
    @PostMapping("/search")
    public Result search(@RequestBody final Map<String, Object> param) {
        Integer page= Integer.parseInt(param.get("page").toString());
        Integer size= Integer.parseInt(param.get("size").toString());
        PageHelper.startPage(page, size);
        final List<TestvariablesValue> list = this.testvariablesValueService.findtestvariablesvalueWithName(param);
        final PageInfo<TestvariablesValue> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }
}
