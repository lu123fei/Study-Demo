package com.jd.demo.controller;


import com.jd.demo.common.lang.Result;
import com.jd.demo.entity.Prize;
import com.jd.demo.entity.Task;
import com.jd.demo.entity.TaskDetails;
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
 * 任务明细表 前端控制器
 * </p>
 *
 * @author lufei
 * @since 2023-05-17
 */
@RestController
@RequestMapping("/task-details")
public class TaskDetailsController {
    //根据id查询任务明细
    @Autowired
    TaskDetailsService taskDetailsService;

    @RequestMapping("info/id/{id}")
    public Result infoId(@PathVariable Long id){
        return taskDetailsService.checkInfoId(id);

    }
    //根据人员id查询任务明细
    @RequestMapping("/info/participantId/{participantId}")
    public Result infoParticipantId(@PathVariable Long participantId){
        return taskDetailsService.checkInfoParticipantId(participantId);

    }
    //根据任务id查询任务明细
    @RequestMapping("/info/taskId/{taskId}")
    public Result infoTaskId(@PathVariable Long taskId){
        return taskDetailsService.checkInfoTaskId(taskId);

    }

    //根据任务id与人员id联合查询任务明细
    @RequestMapping("/info/taskIdAndParticipantId/{taskId}/{participantId}")
    public Result infoTaskId(@PathVariable Long taskId,@PathVariable Long participantId){
        return taskDetailsService.checkInfoTaskIdAndParticipantId(taskId,participantId);

    }
    //获取任务明细列表
    @RequestMapping("/list")
    public Result List(){
        return taskDetailsService.getList();
    }
    //完成任务
    @RequestMapping("/save")
    public Result save(@Validated @RequestBody TaskDetails taskDetails){
        return taskDetailsService.saveTaskDetails(taskDetails);

    }

    //根据id删除任务明细
    @RequestMapping("delete/id/{id}")
    public Result deleteId(@PathVariable Long id){
        return taskDetailsService.checkDeleteId(id);

    }
}
