package com.zoctan.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.Dictionary;
import com.zoctan.api.service.DictionaryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Zoctan
 * @date 2020/04/16
 */
@RestController
@RequestMapping("/dictionary")
public class DictionaryController {
    @Resource
    private DictionaryService dictionaryService;

    @PostMapping
    public Result add(@RequestBody Dictionary dictionary) {
        dictionaryService.save(dictionary);
        return ResultGenerator.genOkResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        dictionaryService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody Dictionary dictionary) {
        dictionaryService.update(dictionary);
        return ResultGenerator.genOkResult();
    }

//    /** 更新字典的资料 */
//    @PutMapping("/{id}")
//    public Result updateOthers(
//            @PathVariable final Long dicid, @RequestBody final Dictionary dic, final Principal principal) {
//        final Dictionary dbDic = this.dictionaryService.getById(dicid);
//        if (dbDic == null) {
//            return ResultGenerator.genFailedResult("字典不存在");
//        }
//        this.dictionaryService.update(dic);
//        return ResultGenerator.genOkResult();
//    }

    /** 更新自己的资料 */
    @PutMapping("/detail")
    public Result updateDic(@RequestBody final Dictionary dic) {
        this.dictionaryService.updateDic(dic);
        return ResultGenerator.genOkResult();
    }


    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        Dictionary dictionary = dictionaryService.getById(id);
        return ResultGenerator.genOkResult(dictionary);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Dictionary> list = dictionaryService.listAll();
        PageInfo<Dictionary> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    //根据字典编码获取字典名和值
    @GetMapping("/apisearch")
    public Result apisearch(@RequestParam(defaultValue = "0") Integer page,
                         @RequestParam(defaultValue = "0") Integer size,
                         @RequestParam String diccode) {
        PageHelper.startPage(page, size);
        final List<Dictionary> list = this.dictionaryService.findDicNameValueWithCode(diccode);
        PageInfo<Dictionary> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }


    @PostMapping("/search")
    public Result search(@RequestBody final Map<String, Object> param) {
        PageHelper.startPage((Integer) param.get("page"), (Integer) param.get("size"));
        final List<Dictionary> list = this.dictionaryService.findDicWithName(param);
        final PageInfo<Dictionary> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

}
