package com.zoctan.api.controller;

import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.ApicasesVariables;
import com.zoctan.api.entity.DbconditionVariables;
import com.zoctan.api.entity.Dbvariables;
import com.zoctan.api.service.DbconditionVariablesService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author SeasonFan
 * @date 2022/03/25
 */
@RestController
@RequestMapping("/dbcondition/variables")
public class DbconditionVariablesController {
    @Resource
    private DbconditionVariablesService dbconditionVariablesService;

    @PostMapping
    public Result add(@RequestBody DbconditionVariables dbconditionVariables) {

        Condition con=new Condition(DbconditionVariables.class);
        con.createCriteria().andCondition("variablesname = '" + dbconditionVariables.getVariablesname().replace("'","''") + "'");
        if(dbconditionVariablesService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult("该变量已经绑定了数据库条件");
        }
        else {
            dbconditionVariablesService.save(dbconditionVariables);
            return ResultGenerator.genOkResult();
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        dbconditionVariablesService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody DbconditionVariables dbconditionVariables) {
        dbconditionVariablesService.update(dbconditionVariables);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        DbconditionVariables dbconditionVariables = dbconditionVariablesService.getById(id);
        return ResultGenerator.genOkResult(dbconditionVariables);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<DbconditionVariables> list = dbconditionVariablesService.listAll();
        PageInfo<DbconditionVariables> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    @PutMapping("/detail")
    public Result updateDeploy(@RequestBody final DbconditionVariables dic) {
        this.dbconditionVariablesService.updatedbconditionvariables(dic);
        return ResultGenerator.genOkResult();
    }

    @PostMapping("/getbyvariablesid")
    public Result getbyvariablesid(@RequestBody final Map<String, Object> param) {
        final List<DbconditionVariables> apicasesVariablesList  = this.dbconditionVariablesService.getbyvariablesid(param);
        return ResultGenerator.genOkResult(apicasesVariablesList);
    }
}
