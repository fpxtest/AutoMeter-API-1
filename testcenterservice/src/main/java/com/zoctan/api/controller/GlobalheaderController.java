package com.zoctan.api.controller;

import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.Enviroment;
import com.zoctan.api.entity.Globalheader;
import com.zoctan.api.entity.Machine;
import com.zoctan.api.service.GlobalheaderParamsService;
import com.zoctan.api.service.GlobalheaderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author SeasonFan
 * @date 2022/07/09
 */
@RestController
@RequestMapping("/globalheader")
public class GlobalheaderController {
    @Resource
    private GlobalheaderService globalheaderService;

    @Resource
    private GlobalheaderParamsService globalheaderParamsService;

    @PostMapping
    public Result add(@RequestBody Globalheader globalheader) {
        Condition con=new Condition(Globalheader.class);
        con.createCriteria().andCondition("projectid = "+globalheader.getProjectid())
                .andCondition("globalheadername = '" + globalheader.getGlobalheadername().replace("'","''") + "'");
        if(globalheaderService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult("全局Header名已经存在");
        }
        else {
            globalheaderService.save(globalheader);
            return ResultGenerator.genOkResult();
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        globalheaderService.deleteById(id);
        globalheaderParamsService.deleteBy("globalheaderid",id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody Globalheader globalheader) {
        globalheaderService.update(globalheader);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        Globalheader globalheader = globalheaderService.getById(id);
        return ResultGenerator.genOkResult(globalheader);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Globalheader> list = globalheaderService.listAll();
        PageInfo<Globalheader> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    @GetMapping("/getvariableslist")
    public Result getvariableslist(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size,@RequestParam long projectid) {
        Condition con=new Condition(Globalheader.class);
        con.createCriteria().andCondition("projectid = "+projectid);
        List<Globalheader> list = globalheaderService.listByCondition(con);
        return ResultGenerator.genOkResult(list);
    }

    /**
     * 更新自己的资料
     */
    @PutMapping("/detail")
    public Result updateDeploy(@RequestBody final Globalheader dic) {
        Condition con=new Condition(Globalheader.class);
        con.createCriteria().andCondition("projectid = "+dic.getProjectid())
                .andCondition("globalheadername = '" + dic.getGlobalheadername().replace("'","''") + "'").andCondition("id <> " + dic.getId());
        if(globalheaderService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult("全局Header名已经存在");
        }
        else {

            this.globalheaderService.updateGlobalheader(dic);
            return ResultGenerator.genOkResult();
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
        final List<Globalheader> list = this.globalheaderService.findGlobalheaderWithName(param);
        final PageInfo<Globalheader> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }
}
