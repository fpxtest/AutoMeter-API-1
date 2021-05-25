package com.zoctan.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.Slaver;
import com.zoctan.api.service.SlaverService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author SeasonFan
 * @date 2020/09/26
 */
@RestController
@RequestMapping("/slaver")
public class SlaverController {
    @Resource
    private SlaverService slaverService;

    @PostMapping
    public Result add(@RequestBody Slaver slaver) {
        slaverService.save(slaver);
        return ResultGenerator.genOkResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        slaverService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody Slaver slaver) {
        slaverService.update(slaver);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        Slaver slaver = slaverService.getById(id);
        return ResultGenerator.genOkResult(slaver);
    }

    @GetMapping("/getslavernum")
    public Result getslavernum() {
        Integer slavernum = slaverService.getslavernum();
        return ResultGenerator.genOkResult(slavernum);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Slaver> list = slaverService.listAll();
        PageInfo<Slaver> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    /**
     * 更新自己的资料
     */
    @PutMapping("/detail")
    public Result updateSlaver(@RequestBody final Slaver dic) {
        this.slaverService.updateSlaver(dic);
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
        final List<Slaver> list = this.slaverService.findslaverWithName(param);
        final PageInfo<Slaver> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }
}
