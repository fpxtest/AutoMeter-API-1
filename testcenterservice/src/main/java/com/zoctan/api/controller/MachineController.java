package com.zoctan.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.Machine;
import com.zoctan.api.service.MachineService;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Zoctan
 * @date 2020/04/15
 */
@RestController
@RequestMapping("/machine")
public class MachineController {
    @Resource
    private MachineService machineService;

    @PostMapping
    public Result add(@RequestBody Machine machine) {
        Condition con=new Condition(Machine.class);
        con.createCriteria().andCondition("machinename = '" + machine.getMachinename().replace("'","''") + "'").orCondition("ip = '" + machine.getIp() + "'");
        if(machineService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult("机器名或者ip已经存在");
        }
        else {
            machineService.save(machine);
            return ResultGenerator.genOkResult();
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        machineService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PutMapping("/detail")
    public Result update(@RequestBody Machine machine) {
//        Condition con=new Condition(Machine.class);
//        con.createCriteria().andCondition("machinename = '" + machine.getMachinename() + "'")
//                .orCondition("ip = '" + machine.getIp() + "'")
//        .andCondition("id <> " + machine.getId());
        Machine machine1=machineService.findmachinebymachineandip(machine.getMachinename(),machine.getIp(),machine.getId());
        if(machine1!=null)
        {
            return ResultGenerator.genFailedResult("服务器名或者ip已经存在");
        }
        else
        {
            machineService.updateMachine(machine);
            return ResultGenerator.genOkResult();
        }
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        Machine machine = machineService.getById(id);
        return ResultGenerator.genOkResult(machine);
    }

    @GetMapping("/getmachinenum")
    public Result getmachinenum() {
        Integer machinenum = machineService.getmachinenum();
        return ResultGenerator.genOkResult(machinenum);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Machine> list = machineService.listAll();
        PageInfo<Machine> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    @GetMapping("/getmachine")
    public Result listbyenvname() {
        List<Machine> list = machineService.listAll();
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
        final List<Machine> list = this.machineService.findMachineWithName(param);
        final PageInfo<Machine> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }
}
