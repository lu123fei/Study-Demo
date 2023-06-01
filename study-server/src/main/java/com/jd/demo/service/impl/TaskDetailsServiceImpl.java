package com.jd.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.jd.demo.common.lang.Result;
import com.jd.demo.entity.Task;
import com.jd.demo.entity.TaskDetails;
import com.jd.demo.mapper.TaskDetailsMapper;
import com.jd.demo.service.TaskDetailsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jd.demo.service.TaskService;
import com.jd.jim.cli.Cluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.jd.demo.common.lang.Constant.TASK_DETAILS_INFO_ID;

/**
 * <p>
 * 任务明细表 服务实现类
 * </p>
 *
 * @author lufei
 * @since 2023-05-17
 */
@Service
public class TaskDetailsServiceImpl extends ServiceImpl<TaskDetailsMapper, TaskDetails> implements TaskDetailsService {
    @Autowired
    TaskDetailsMapper taskDetailsMapper;
    @Autowired
    TaskService taskService;
    @Resource
    private Cluster cluster;
    @Override
    public List<TaskDetails> getByParticipantId(Long participantId) {
        return taskDetailsMapper.selectByParticipantId(participantId);
    }

    @Override
    public List<TaskDetails> getByTaskId(Long taskId) {
        return taskDetailsMapper.selectByTaskId(taskId);
    }

    @Override
    public List<TaskDetails> getAllTaskDetails() {
        return taskDetailsMapper.selectAllTaskDetails();
    }

    @Override
    public List<TaskDetails> getByTaskIdAndParticipantId(Long taskId, Long participantId) {
        return taskDetailsMapper.selectByTaskIdAndParticipantId(taskId,participantId);
    }

    @Override
    public Result checkInfoId(Long id) {
        String key = TASK_DETAILS_INFO_ID + id;
        String value = cluster.get(key);
        if (value != null) {
            return Result.success("查询任务明细成功", JSON.parseObject(value));
        }else{
            TaskDetails taskDetails = this.getById(id);
            if(taskDetails!=null){
                cluster.set(key, JSON.toJSONString(taskDetails),5l, TimeUnit.MINUTES, true);
                return Result.success("查询任务明细成功",taskDetails);
            }else{
                return Result.fail("查询任务明细失败，无此id的任务明细");
            }
        }
    }

    @Override
    public Result checkInfoParticipantId(Long participantId) {
        List<TaskDetails> taskDetails = this.getByParticipantId(participantId);
        if(taskDetails.size()!=0){
            return Result.success("查询任务明细成功",taskDetails);
        }else{
            return Result.fail("查询任务明细失败，无此人员id的任务明细");
        }
    }

    @Override
    public Result checkInfoTaskId(Long taskId) {
        List<TaskDetails> taskDetails = this.getByTaskId(taskId);
        if(taskDetails.size()!=0){
            return Result.success("查询任务明细成功",taskDetails);
        }else{
            return Result.fail("查询任务明细失败，无此任务id的任务明细");
        }
    }

    @Override
    public Result checkInfoTaskIdAndParticipantId(Long taskId, Long participantId) {
        List<TaskDetails> taskDetails = this.getByTaskIdAndParticipantId(taskId,participantId);
        if(taskDetails.size()!=0){
            return Result.success("查询任务明细成功",taskDetails);
        }else{
            return Result.fail("查询任务明细失败，无此任务id与人员id对应的任务明细");
        }
    }

    @Override
    public Result getList() {
        List<TaskDetails> taskDetails = this.getAllTaskDetails();
        return Result.success("获取任务明细列表成功",taskDetails);
    }

    @Override
    public Result saveTaskDetails(TaskDetails taskDetails) {
        if(taskDetails.getId()==null||taskDetails.getTaskId()==null||taskDetails.getParticipantId()==null||taskDetails.getTaskFinishTime()==null){
            return Result.fail("完成任务失败，缺少相应关键字");
        }
        long participantId = taskDetails.getParticipantId();
        long id = taskDetails.getId();
        long taskId = taskDetails.getTaskId();
        Result result = taskService.checkInfoTaskId(taskId);
        if(result.getCode()==500){
            return Result.fail("任务状态异常");
        }
        Task task = taskService.getByTaskId(taskId);
        String taskScope = task.getTaskScope();
        int taskEnableStatus = task.getTaskEnableStatus();
        if(taskEnableStatus!=1){
            return Result.fail("完成任务失败，任务状态异常");
        }else if(taskScope.contains(String.valueOf(participantId))==false){
            return Result.fail("完成任务失败，该人员不在任务范围内");
        }else if(this.getById(id)!=null){
            return Result.fail("该任务明细已记录");
        }else{
            this.save(taskDetails);
            return Result.success("完成任务成功",taskDetails);
        }
    }

    @Override
    public Result checkDeleteId(Long id) {
        TaskDetails taskDetails = this.getById(id);
        if(taskDetails!=null){
            taskDetailsMapper.deleteById(id);
            this.deleteCache(id);
            return Result.success("删除任务明细成功",taskDetails);
        }else{
            return Result.fail("删除任务明细失败，无此id的任务明细");
        }
    }
    public void deleteCache(Long id) {
        String keyInfoId = TASK_DETAILS_INFO_ID + id;
        cluster.del(keyInfoId);
    }



}
