package com.zoctan.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.Executeplan;
import com.zoctan.api.service.ExecuteplanService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author SeasonFan
 * @date 2020/09/27
 */
@RestController
@RequestMapping("/executeplan")
public class ExecuteplanController {
    @Resource
    private ExecuteplanService executeplanService;

    @PostMapping
    public Result add(@RequestBody Executeplan executeplan) {
        executeplanService.save(executeplan);
        return ResultGenerator.genOkResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        executeplanService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody Executeplan executeplan) {
        executeplanService.update(executeplan);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        Executeplan executeplan = executeplanService.getById(id);
        return ResultGenerator.genOkResult(executeplan);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Executeplan> list = executeplanService.listAll();
        PageInfo<Executeplan> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    /**
     * 更新自己的资料
     */
    @PutMapping("/detail")
    public Result updateExecuteplan(@RequestBody final Executeplan dic) {
        this.executeplanService.updateexecuteplanname(dic);
        return ResultGenerator.genOkResult();
    }

    /**
     * 输入框查询
     */
    @PostMapping("/search")
    public Result search(@RequestBody final Map<String, Object> param) {
        PageHelper.startPage((Integer) param.get("page"), (Integer) param.get("size"));
        final List<Executeplan> list = this.executeplanService.findexplanWithName(param);
        final PageInfo<Executeplan> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }
}
