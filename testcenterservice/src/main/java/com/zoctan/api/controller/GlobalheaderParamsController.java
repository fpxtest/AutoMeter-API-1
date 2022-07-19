package com.zoctan.api.controller;

import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.Globalheader;
import com.zoctan.api.entity.GlobalheaderParams;
import com.zoctan.api.service.GlobalheaderParamsService;
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
@RequestMapping("/globalheader/params")
public class GlobalheaderParamsController {
    @Resource
    private GlobalheaderParamsService globalheaderParamsService;

    @PostMapping
    public Result add(@RequestBody GlobalheaderParams globalheaderParams) {
        Condition con=new Condition(GlobalheaderParams.class);
        con.createCriteria().andCondition("keyname = '" + globalheaderParams.getKeyname().replace("'","''") + "'").andCondition("globalheaderid = " + globalheaderParams.getGlobalheaderid());
        if(globalheaderParamsService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult("此Header已存在相同的参数");
        }
        else {
            globalheaderParamsService.save(globalheaderParams);
            return ResultGenerator.genOkResult();
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        globalheaderParamsService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody GlobalheaderParams globalheaderParams) {
        globalheaderParamsService.update(globalheaderParams);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        GlobalheaderParams globalheaderParams = globalheaderParamsService.getById(id);
        return ResultGenerator.genOkResult(globalheaderParams);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<GlobalheaderParams> list = globalheaderParamsService.listAll();
        PageInfo<GlobalheaderParams> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    @PutMapping("/detail")
    public Result updateDeploy(@RequestBody final GlobalheaderParams globalheaderParams) {
        Condition con=new Condition(GlobalheaderParams.class);
        con.createCriteria().andCondition("keyname = '" + globalheaderParams.getKeyname().replace("'","''") + "'").andCondition("globalheaderid = " + globalheaderParams.getGlobalheaderid()).andCondition("id <> " + globalheaderParams.getId());
        if(globalheaderParamsService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult("此Header已存在相同的参数");
        }
        else {

            this.globalheaderParamsService.updateGlobalheaderParams(globalheaderParams);
            return ResultGenerator.genOkResult();
        }
    }

    @PostMapping("/search")
    public Result search(@RequestBody final Map<String, Object> param) {
        Integer page= Integer.parseInt(param.get("page").toString());
        Integer size= Integer.parseInt(param.get("size").toString());
        PageHelper.startPage(page, size);
        final List<GlobalheaderParams> list = this.globalheaderParamsService.findGlobalheaderParamsWithName(param);
        final PageInfo<GlobalheaderParams> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }
}
