package com.zoctan.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.EnviromentAssemble;
import com.zoctan.api.entity.Macdepunit;
import com.zoctan.api.service.MacdepunitService;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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

        Condition assemcon=new Condition(Macdepunit.class);
        assemcon.createCriteria().andCondition("envid = " + macdepunit.getEnvid()).andCondition("assembleid = " + macdepunit.getAssembleid() );
        if(macdepunitService.ifexist(assemcon)>0)
        {
            return ResultGenerator.genFailedResult("此环境已经存在此微服务或者组件");
        }
        else
        {
            Condition depcon=new Condition(Macdepunit.class);
            depcon.createCriteria().andCondition("envid = " + macdepunit.getEnvid()).andCondition("depunitid = " + macdepunit.getDepunitid() );
            if(macdepunitService.ifexist(depcon)>0)
            {
                return ResultGenerator.genFailedResult("此环境已经存在此微服务或者组件");
            }
            else
            {
                macdepunitService.save(macdepunit);
                return ResultGenerator.genOkResult();
            }
        }
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
    public Result updateDeploy(@RequestBody final Macdepunit macdepunit) {

        Condition assemcon=new Condition(Macdepunit.class);
        assemcon.createCriteria().andCondition("envid = " + macdepunit.getEnvid()).andCondition("assembleid = " + macdepunit.getAssembleid() )
                .andCondition("id <> " + macdepunit.getId());
        if(macdepunitService.ifexist(assemcon)>0)
        {
            return ResultGenerator.genFailedResult("此环境已经存在此微服务或者组件");
        }
        else
        {
            Condition con=new Condition(Macdepunit.class);
            con.createCriteria().andCondition("envid = " + macdepunit.getEnvid()).andCondition("depunitid = " + macdepunit.getDepunitid() )
                    .andCondition("id <> " + macdepunit.getId());
            if(macdepunitService.ifexist(con)>0)
            {
                return ResultGenerator.genFailedResult("此环境已经存在此微服务或者组件");
            }
            else {
                this.macdepunitService.updateMacAndDep(macdepunit);
                return ResultGenerator.genOkResult();
            }
        }
    }

    /**
     * 输入框查询
     */
    @PostMapping("/search")
    public Result search(@RequestBody final Map<String, Object> param) {
        Integer page= Integer.parseInt(param.get("page").toString());
        Integer size= Integer.parseInt(param.get("size").toString());
        PageHelper.startPage(page, size);
        final List<Macdepunit> list = this.macdepunitService.findMacAndDepWithid(param);
        final PageInfo<Macdepunit> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }
}
