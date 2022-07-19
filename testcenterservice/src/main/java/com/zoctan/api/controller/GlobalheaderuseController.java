package com.zoctan.api.controller;

import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.*;
import com.zoctan.api.service.GlobalheaderuseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author SeasonFan
 * @date 2022/07/12
 */
@RestController
@RequestMapping("/globalheaderuse")
public class GlobalheaderuseController {
    @Resource
    private GlobalheaderuseService globalheaderuseService;

    @PostMapping
    public Result add(@RequestBody Globalheaderuse globalheaderuse) {
        Condition con=new Condition(Globalheader.class);
        con.createCriteria().andCondition("globalheaderid = " + globalheaderuse.getGlobalheaderid())
        .andCondition("executeplanid = " + globalheaderuse.getExecuteplanid());
        if(globalheaderuseService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult("全局Header已经存在");
        }
        else
        {
            globalheaderuseService.save(globalheaderuse);
            return ResultGenerator.genOkResult();
        }
    }

    @PostMapping("/searchheaderbyepid")
    public Result searchheaderbyepid(@RequestBody Map<String, Object> param) {
        final List<Globalheaderuse> list = this.globalheaderuseService.searchheaderbyepid(param);
        final PageInfo<Globalheaderuse> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        globalheaderuseService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PostMapping("/addcases")
    public Result addcaseslist(@RequestBody final List<Globalheaderuse> casedataList) {
        globalheaderuseService.saveconditionscase(casedataList);
        return ResultGenerator.genOkResult();
    }

    @PostMapping("/deletacases")
    public Result deletacases(@RequestBody final List<Globalheaderuse> casedataList) {
        globalheaderuseService.deletacases(casedataList);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody Globalheaderuse globalheaderuse) {
        globalheaderuseService.update(globalheaderuse);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        Globalheaderuse globalheaderuse = globalheaderuseService.getById(id);
        return ResultGenerator.genOkResult(globalheaderuse);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Globalheaderuse> list = globalheaderuseService.listAll();
        PageInfo<Globalheaderuse> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    /**
     * 更新自己的资料
     */
    @PutMapping("/detail")
    public Result updateDeploy(@RequestBody final Globalheaderuse globalheaderuse) {
        Condition con=new Condition(Globalheader.class);
        con.createCriteria().andCondition("globalheaderid = " + globalheaderuse.getGlobalheaderid())
                .andCondition("executeplanid = " + globalheaderuse.getExecuteplanid())
                .andCondition("id <> " + globalheaderuse.getId());
        if(globalheaderuseService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult("全局Header已经存在");
        }
        else
        {
            globalheaderuseService.update(globalheaderuse);
            return ResultGenerator.genOkResult();
        }
    }

    @PostMapping("/searchexit")
    public Result search(@RequestBody final Map<String, Object> param) {
        Integer page= Integer.parseInt(param.get("page").toString());
        Integer size= Integer.parseInt(param.get("size").toString());
        PageHelper.startPage(page, size);
        final List<Globalheaderuse> list = this.globalheaderuseService.findexistcaseglobalheader(param);
        final PageInfo<Globalheaderuse> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    @PostMapping("/searchnotexit")
    public Result searchnotexit(@RequestBody final Map<String, Object> param) {
        Integer page= Integer.parseInt(param.get("page").toString());
        Integer size= Integer.parseInt(param.get("size").toString());
        long deployunitid=Long.parseLong(param.get("deployunitid").toString());
        long globalheaderid=Long.parseLong(param.get("globalheaderid").toString());
        PageHelper.startPage(page, size);
        List<Globalheaderuse> list = this.globalheaderuseService.findnotexistcase(globalheaderid,deployunitid);
        final PageInfo<Globalheaderuse> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }
}
