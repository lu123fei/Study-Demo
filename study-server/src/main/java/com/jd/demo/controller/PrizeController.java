package com.jd.demo.controller;


import com.jd.demo.common.lang.Result;
import com.jd.demo.entity.Prize;
import com.jd.demo.entity.Task;
import com.jd.demo.service.PrizeService;
import com.jd.demo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 奖品表 前端控制器
 * </p>
 *
 * @author lufei
 * @since 2023-05-17
 */
@RestController
@RequestMapping("/prize")
public class PrizeController {
    @Autowired
    PrizeService prizeService;
    //根据id查询奖品

    @RequestMapping("/info/id/{id}")
    public Result infoId(@PathVariable Long id){
        return prizeService.checkInfoId(id);

    }
    //根据奖品id查询奖品

    @RequestMapping("/info/prizeId/{prizeId}")
    public Result infoPrizeId(@PathVariable Long prizeId){
        return prizeService.checkInfoPrizeId(prizeId);

    }
    //根据奖品名称查询奖品

    @RequestMapping("/info/prizeName/{prizeName}")
    public Result infoPrizeName(@PathVariable String prizeName){
        return prizeService.checkInfoPrizeName(prizeName);

    }
    //获取奖品列表
    @RequestMapping("/list")
    public Result List(){
        return prizeService.getList();

    }
    //奖品的创建
    @RequestMapping("/save")
    public Result save(@Validated @RequestBody Prize prize){
        return prizeService.savePrize(prize);

    }
    //奖品的更新
    @RequestMapping("/update")
    public Result update(@Validated @RequestBody Prize prize){
        return prizeService.updatePrize(prize);

    }
    //根据id删除奖品
    @RequestMapping("/delete/id/{id}")
    public Result deleteId(@PathVariable Long id){
        return prizeService.checkDeleteId(id);

    }
    //根据奖品id删除奖品
    @RequestMapping("/delete/prizeId/{prizeId}")
    public Result deleteByPrizeId(@PathVariable Long prizeId){
        return prizeService.checkDeletePrizeId(prizeId);

    }
    //根据奖品名称删除奖品
    @RequestMapping("/delete/prizeName/{prizeName}")
    public Result deleteByPrizeName(@PathVariable String prizeName){
        return prizeService.checkDeletePrizeName(prizeName);

    }
}
