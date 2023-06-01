package com.jd.demo.controller;


import com.jd.demo.common.lang.Result;
import com.jd.demo.entity.Task;
import com.jd.demo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 任务表 前端控制器
 * </p>
 *
 * @author lufei
 * @since 2023-05-17
 */
@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    TaskService taskService;
    //根据id查询任务

    @RequestMapping("/info/id/{id}")
    public Result infoId(@PathVariable Long id){
        return taskService.checkInfoId(id);
    }
    //根据任务id查询任务
    @RequestMapping("/info/taskId/{taskId}")
    public Result infoTaskId(@PathVariable Long taskId){
        return taskService.checkInfoTaskId(taskId);

    }
    //根据任务名称查询任务
    @RequestMapping("/info/taskName/{taskName}")
    public Result infoTaskName(@PathVariable String taskName){
        return taskService.checkInfoTaskName(taskName);

    }
    //获取任务列表
    @RequestMapping("/list")
    public Result List(){
        return taskService.getList();
    }
    //任务的创建
    @RequestMapping("/save")
    public Result save(@Validated @RequestBody Task task){
        return taskService.saveTask(task);

    }
    //任务的更新
    @RequestMapping("/update")
    public Result update(@Validated @RequestBody Task task){
        return taskService.updateTask(task);

    }
    //根据id删除任务
    @RequestMapping("/delete/id/{id}")
    public Result deleteId(@PathVariable Long id){
        return taskService.checkDeleteId(id);

    }
    //根据任务id删除任务
    @RequestMapping("/delete/taskId/{taskId}")
    public Result deleteTaskId(@PathVariable Long taskId){
        return taskService.checkDeleteTaskId(taskId);

    }
    //根据任务名称删除任务
    @RequestMapping("/delete/taskName/{taskName}")
    public Result deleteTaskName(@PathVariable String taskName){
        return taskService.checkDeleteTaskName(taskName);

    }
}
