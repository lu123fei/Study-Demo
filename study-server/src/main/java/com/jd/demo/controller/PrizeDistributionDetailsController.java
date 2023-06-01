package com.jd.demo.controller;


import com.jd.demo.common.lang.Result;
import com.jd.demo.entity.PrizeDistributionDetails;
import com.jd.demo.entity.Task;
import com.jd.demo.entity.TaskDetails;
import com.jd.demo.service.PrizeDistributionDetailsService;
import com.jd.demo.service.TaskDetailsService;
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
 * 奖品分发明细表 前端控制器
 * </p>
 *
 * @author lufei
 * @since 2023-05-17
 */
@RestController
@RequestMapping("/prize-distribution-details")
public class PrizeDistributionDetailsController {
    //根据id查询奖品分发明细
    @Autowired
    PrizeDistributionDetailsService prizeDistributionDetailsService;


    @RequestMapping("info/id/{id}")
    public Result infoId(@PathVariable Long id) {
        return prizeDistributionDetailsService.checkInfoId(id);

    }

    //根据人员id查询奖品分发明细
    @RequestMapping("info/participantId/{participantId}")
    public Result infoParticipantId(@PathVariable Long participantId) {
        return prizeDistributionDetailsService.checkInfoParticipantId(participantId);

    }

    //根据任务id查询奖品分发明细
    @RequestMapping("info/taskId/{taskId}")
    public Result infoTaskId(@PathVariable Long taskId) {
        return prizeDistributionDetailsService.checkInfoTaskId(taskId);

    }

    //根据奖品id查询奖品分发明细
    @RequestMapping("info/prizeId/{prizeId}")
    public Result infoPrizeId(@PathVariable Long prizeId) {
        return prizeDistributionDetailsService.checkInfoPrizeId(prizeId);

    }

    //获取奖品分发明细列表
    @RequestMapping("/list")
    public Result list() {
        return prizeDistributionDetailsService.getList();


    }

    //根据人员id、任务id、奖品id联合查询奖品分发明细
    @RequestMapping("/info/unionQuery/{participantId}/{taskId}/{prizeId}")
    public Result unionQuery(@PathVariable Long participantId, @PathVariable Long taskId, @PathVariable Long prizeId) {
        return prizeDistributionDetailsService.checkInfoUnionQuery(participantId,taskId,prizeId);

    }

    //奖品分发
    @RequestMapping("/save")
    public Result save(@Validated @RequestBody PrizeDistributionDetails prizeDistributionDetails) {
        return prizeDistributionDetailsService.savePrizeDistributionDetails(prizeDistributionDetails);

    }

    //根据id删除指定奖品分发明细
    @RequestMapping("delete/id/{id}")
    public Result deleteId(@PathVariable Long id) {
        return prizeDistributionDetailsService.checkDeleteId(id);

    }
}
