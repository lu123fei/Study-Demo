package com.jd.demo.mapper;

import com.jd.demo.entity.Task;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 任务表 Mapper 接口
 * </p>
 *
 * @author lufei
 * @since 2023-05-17
 */
public interface TaskMapper extends BaseMapper<Task> {
    public Task selectById(Long id);

    Task selectByTaskId(Long taskId);

    Task selectByTaskName(String taskName);

    List<Task> selectAllTask();

    void deleteByTaskId(Long taskId);

    void deleteByTaskName(String taskName);
}
