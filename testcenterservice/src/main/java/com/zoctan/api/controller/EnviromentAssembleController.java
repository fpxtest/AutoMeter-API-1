package com.zoctan.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.EnviromentAssemble;
import com.zoctan.api.service.EnviromentAssembleService;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author SeasonFan
 * @date 2020/11/06
 */
@RestController
@RequestMapping("/enviroment_assemble")
public class EnviromentAssembleController {
    @Resource
    private EnviromentAssembleService enviromentAssembleService;

    @PostMapping
    public Result add(@RequestBody EnviromentAssemble enviromentAssemble) {
        Condition con=new Condition(EnviromentAssemble.class);
        con.createCriteria().andCondition("assembletype = '" + enviromentAssemble.getAssembletype() + "'")
        .andCondition("connectstr = '" + enviromentAssemble.getConnectstr() + "'")
                .andCondition("assemblename = '" + enviromentAssemble.getAssemblename() + "'");
        if(enviromentAssembleService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult("已存在相同的环境组件");
        }
        else
        {
            enviromentAssembleService.save(enviromentAssemble);
            return ResultGenerator.genOkResult();
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        enviromentAssembleService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    /**
     * 更新自己的资料
     */
    @PutMapping("/detail")
    public Result updateDeploy(@RequestBody final EnviromentAssemble enviromentAssemble) {
        Condition con=new Condition(EnviromentAssemble.class);
        con.createCriteria().andCondition("assembletype = '" + enviromentAssemble.getAssembletype() + "'")
        .andCondition("connectstr = '" + enviromentAssemble.getConnectstr() + "'")
                .andCondition("assemblename = '" + enviromentAssemble.getAssemblename() + "'")
                .andCondition("id <> " + enviromentAssemble.getId());
        if(enviromentAssembleService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult("环境组件已经存在");
        }
        else {

            this.enviromentAssembleService.updateenviromentassemble(enviromentAssemble);
            return ResultGenerator.genOkResult();
        }
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        EnviromentAssemble enviromentAssemble = enviromentAssembleService.getById(id);
        return ResultGenerator.genOkResult(enviromentAssemble);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<EnviromentAssemble> list = enviromentAssembleService.listAll();
        PageInfo<EnviromentAssemble> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }


    @GetMapping("/getassemblename")
    public Result listall() {
        List<EnviromentAssemble> list = enviromentAssembleService.listAll();
        return ResultGenerator.genOkResult(list);
    }
    /**
     * 输入框查询
     */
    @PostMapping("/search")
    public Result search(@RequestBody final Map<String, Object> param) {
        Integer page= Integer.parseInt(param.get("page").toString());
        Integer size= Integer.parseInt(param.get("size").toString());
        PageHelper.startPage(page, size);
        final List<EnviromentAssemble> list = this.enviromentAssembleService.findassembleWithName(param);
        final PageInfo<EnviromentAssemble> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }
}
