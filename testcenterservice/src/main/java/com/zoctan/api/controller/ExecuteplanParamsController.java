package com.zoctan.api.controller;

import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.ApiParams;
import com.zoctan.api.entity.ExecuteplanParams;
import com.zoctan.api.service.ExecuteplanParamsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author SeasonFan
 * @date 2021/12/19
 */
@RestController
@RequestMapping("/executeplan/params")
public class ExecuteplanParamsController {
    @Resource
    private ExecuteplanParamsService executeplanParamsService;

    @PostMapping
    public Result add(@RequestBody ExecuteplanParams executeplanParams) {
        if(executeplanParams.getParamstype().equals("Body"))
        {
            Condition conbody=new Condition(ExecuteplanParams.class);
            conbody.createCriteria().andCondition("paramstype = '" + executeplanParams.getParamstype() + "'")
                    .andCondition("executeplanid = '" + executeplanParams.getExecuteplanid() + "'");
            if(executeplanParamsService.ifexist(conbody)>0)
            {
                return ResultGenerator.genFailedResult("测试集合下已经存在Body参数");
            }
            else
            {
                executeplanParamsService.save(executeplanParams);
                return ResultGenerator.genOkResult();
            }
        }
        else
        {
            Condition con=new Condition(ExecuteplanParams.class);
            con.createCriteria().andCondition("paramstype = '" + executeplanParams.getParamstype() + "'")
                    .andCondition("keyname = '" + executeplanParams.getKeyname().replace("'","''") + "'")
                    .andCondition("executeplanid = '" + executeplanParams.getExecuteplanid() + "'");
            if(executeplanParamsService.ifexist(con)>0)
            {
                return ResultGenerator.genFailedResult("测试集合下已经存在此类型的参数");
            }
            executeplanParamsService.save(executeplanParams);
            return ResultGenerator.genOkResult();
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        executeplanParamsService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody ExecuteplanParams executeplanParams) {
        executeplanParamsService.update(executeplanParams);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        ExecuteplanParams executeplanParams = executeplanParamsService.getById(id);
        return ResultGenerator.genOkResult(executeplanParams);
    }

    /**
     * 更新自己的资料
     */
    @PutMapping("/detail")
    public Result updateDeploy(@RequestBody final ExecuteplanParams executeplanParams) {
        if(executeplanParams.getParamstype().equals("Body"))
        {
            Condition conbody=new Condition(ExecuteplanParams.class);
            conbody.createCriteria().andCondition("paramstype = '" + executeplanParams.getParamstype() + "'")
                    .andCondition("executeplanid = '" + executeplanParams.getExecuteplanid() + "'")
                    .andCondition("id <> " + executeplanParams.getId());

            if(executeplanParamsService.ifexist(conbody)>0)
            {
                return ResultGenerator.genFailedResult("测试集合下已经存在Body参数");
            }
            else
            {
                this.executeplanParamsService.updateParams(executeplanParams);
                return ResultGenerator.genOkResult();
            }
        }
        else
        {
            Condition con=new Condition(ExecuteplanParams.class);
            con.createCriteria().andCondition("paramstype = '" + executeplanParams.getParamstype() + "'")
                    .andCondition("keyname = '" + executeplanParams.getKeyname().replace("'","''") + "'")
                    .andCondition("id <> " + executeplanParams.getId())
                    .andCondition("executeplanid = " + executeplanParams.getExecuteplanid());
            if(executeplanParamsService.ifexist(con)>0)
            {
                return ResultGenerator.genFailedResult("测试集合下已经存在此类型的参数");
            }
            else
            {
                this.executeplanParamsService.updateParams(executeplanParams);
                return ResultGenerator.genOkResult();
            }
        }
    }

    /**
     * 输入APIID获取参数列表
     */
    @PostMapping("/searchparamsbyepid")
    public Result searchparamsbyepid(@RequestBody Map<String, Object> param) {
        final List<ExecuteplanParams> list = this.executeplanParamsService.getParamsbyepid(param);
        final PageInfo<ExecuteplanParams> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<ExecuteplanParams> list = executeplanParamsService.listAll();
        PageInfo<ExecuteplanParams> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }
}
