package com.zoctan.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.Tester;
import com.zoctan.api.service.TesterService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Zoctan
 * @date 2020/04/22
 */
@RestController
@RequestMapping("/tester")
public class TesterController {
    @Resource
    private TesterService testerService;

    @PostMapping
    public Result add(@RequestBody Tester tester) {
        testerService.save(tester);
        return ResultGenerator.genOkResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        testerService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody Tester tester) {
        testerService.update(tester);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        Tester tester = testerService.getById(id);
        return ResultGenerator.genOkResult(tester);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Tester> list = testerService.listAll();
        PageInfo<Tester> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    /**
     * 更新自己的资料
     */
    @PutMapping("/detail")
    public Result updateDeploy(@RequestBody final Tester dic) {
        this.testerService.updateTester(dic);
        return ResultGenerator.genOkResult();
    }

    /**
     * 输入框查询
     */
    @PostMapping("/search")
    public Result search(@RequestBody final Map<String, Object> param) {
        PageHelper.startPage((Integer) param.get("page"), (Integer) param.get("size"));
        final List<Tester> list = this.testerService.findTesterWithName(param);
        final PageInfo<Tester> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }
}
