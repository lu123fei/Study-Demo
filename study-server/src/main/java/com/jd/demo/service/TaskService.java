package com.jd.demo.service;

import com.jd.demo.common.lang.Result;
import com.jd.demo.entity.Task;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 任务表 服务类
 * </p>
 *
 * @author lufei
 * @since 2023-05-17
 */
public interface TaskService extends IService<Task> {

    Task getByTaskName( String name);
    Task getById( Long id);

    Task getByTaskId(Long taskId);

    List<Task> getAllTask();

    void deleteById(Long id);

    void deleteByTaskId(Long taskId);

    void deleteByTaskName(String taskName);
    Result checkInfoId(Long id);

    Result checkInfoTaskId(Long taskId);

    Result checkInfoTaskName(String taskName);

    Result getList();

    Result saveTask(Task task);

    Result updateTask(Task task);

    Result checkDeleteId(Long id);

    Result checkDeleteTaskId(Long taskId);

    Result checkDeleteTaskName(String taskName);
}
