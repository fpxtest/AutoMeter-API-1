package com.zoctan.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.dto.StaticsDataForLine;
import com.zoctan.api.entity.StaticsDeployunitandcases;
import com.zoctan.api.entity.StaticsPlanandcases;
import com.zoctan.api.service.StaticsPlanandcasesService;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;

/**
 * @author SeasonFan
 * @date 2021/04/14
 */
@RestController
@RequestMapping("/statics/planandcases")
public class StaticsPlanandcasesController {
    @Resource
    private StaticsPlanandcasesService staticsPlanandcasesService;

    @PostMapping
    public Result add(@RequestBody StaticsPlanandcases staticsPlanandcases) {
        staticsPlanandcasesService.save(staticsPlanandcases);
        return ResultGenerator.genOkResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        staticsPlanandcasesService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody StaticsPlanandcases staticsPlanandcases) {
        staticsPlanandcasesService.update(staticsPlanandcases);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        StaticsPlanandcases staticsPlanandcases = staticsPlanandcasesService.getById(id);
        return ResultGenerator.genOkResult(staticsPlanandcases);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<StaticsPlanandcases> list = staticsPlanandcasesService.listAll();
        PageInfo<StaticsPlanandcases> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    @GetMapping("/getlastdays")
    public Result getlastdays() {
        List<String> lastdaylist=new ArrayList<>();
        for(int i=15;i>0;i--)
        {
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE,-i);
            int MONTH = calendar.get(Calendar.MONTH)+1;
            int Day = calendar.get(Calendar.DAY_OF_MONTH);
            lastdaylist.add(MONTH+"-"+Day);
        }
        return ResultGenerator.genOkResult(lastdaylist);
    }

    @GetMapping("/getplanstatics")
    public Result getplanstatics(@RequestParam long projectid) {
        Condition con=new Condition(StaticsPlanandcases.class);
        con.createCriteria().andCondition("projectid = "+projectid);
        List<StaticsPlanandcases> list = staticsPlanandcasesService.listByCondition(con);//.listAll();
        List<StaticsDataForLine> staticsDataForLineList=new ArrayList<>();
        HashMap<String,List<Double>> tmp=new HashMap<>();
        for (StaticsPlanandcases staticsPlanandcases: list) {
            if(!tmp.containsKey(staticsPlanandcases.getTestplanname()))
            {
                List<Double> planstaticsdatelist=new ArrayList<>();
                planstaticsdatelist.add(staticsPlanandcases.getPassrate());
                tmp.put(staticsPlanandcases.getTestplanname(),planstaticsdatelist);
            }
            else
            {
                tmp.get(staticsPlanandcases.getTestplanname()).add(staticsPlanandcases.getPassrate());
            }
        }
        for (String PlanName:tmp.keySet()) {
            StaticsDataForLine staticsDataForLine=new StaticsDataForLine();
            staticsDataForLine.setExecPlanName(PlanName);
            staticsDataForLine.setPassPecent(tmp.get(PlanName));
            staticsDataForLineList.add(staticsDataForLine);
        }
        return ResultGenerator.genOkResult(staticsDataForLineList);
    }

    public static void main(String[] args) throws ParseException {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-1);
        int Year=calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH)+1;
        int Day = calendar.get(Calendar.DATE);
        String StaticsDay=Year+"-"+MONTH+"-"+Day;
            System.out.println("MONTH Day is "+StaticsDay);

//        for(int i=15;i>0;i--)
//        {
//            //Date date = new Date();
//            Calendar calendar = Calendar.getInstance();
//            //calendar.setTime(date);
//            calendar.add(Calendar.DATE,-i);
//            int MONTH = calendar.get(Calendar.MONTH)+1;
//            int Day = calendar.get(Calendar.DAY_OF_MONTH);
//            System.out.println("MONTH Day is "+MONTH+"-"+Day);
//        }
    }
}
