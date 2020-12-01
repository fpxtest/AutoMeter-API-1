package com.zoctan.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.Executeplanbatch;
import com.zoctan.api.service.ExecuteplanbatchService;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author SeasonFan
 * @date 2020/10/22
 */
@RestController
@RequestMapping("/executeplanbatch")
public class ExecuteplanbatchController {
    @Resource
    private ExecuteplanbatchService executeplanbatchService;

    @PostMapping
    public Result add(@RequestBody Executeplanbatch executeplanbatch) {
        Condition con=new Condition(Executeplanbatch.class);
        con.createCriteria().andCondition("batchname = '" + executeplanbatch.getBatchname() + "'")
                .andCondition("executeplanid = '" + executeplanbatch.getExecuteplanid() + "'");
        if(executeplanbatchService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult("该执行计划下已经存在此批次");
        }
        else {
            executeplanbatchService.save(executeplanbatch);
            return ResultGenerator.genOkResult();
        }

    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        executeplanbatchService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody Executeplanbatch executeplanbatch) {
        executeplanbatchService.update(executeplanbatch);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        Executeplanbatch executeplanbatch = executeplanbatchService.getById(id);
        return ResultGenerator.genOkResult(executeplanbatch);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Executeplanbatch> list = executeplanbatchService.listAll();
        PageInfo<Executeplanbatch> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    @PostMapping("/getbatchbyplan")
    public Result getbatchbyplan(@RequestParam Long executeplanid) {
        List<Executeplanbatch> list = executeplanbatchService.getbatchbyplan(executeplanid);
        return ResultGenerator.genOkResult(list);
    }


}
