package com.jd.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.jd.demo.common.lang.Result;
import com.jd.demo.entity.Task;
import com.jd.demo.mapper.TaskMapper;
import com.jd.demo.service.TaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jd.jim.cli.Cluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.jd.demo.common.lang.Constant.TASK_INFO_ID;

/**
 * <p>
 * 任务表 服务实现类
 * </p>
 *
 * @author lufei
 * @since 2023-05-17
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {
    @Autowired
    TaskMapper taskMapper;
    /**
     * 缓存
     */
    @Resource
    private Cluster cluster;

    @Override
    public Task getByTaskName(String taskName) {
        return taskMapper.selectByTaskName(taskName);
    }

    @Override
    public Task getById(Long id) {
        return taskMapper.selectById(id);
    }

    @Override
    public Task getByTaskId(Long taskId) {
        return taskMapper.selectByTaskId(taskId);
    }

    @Override
    public List<Task> getAllTask() {
        return taskMapper.selectAllTask();
    }

    @Override
    public void deleteById(Long id) {
        taskMapper.deleteById(id);
    }

    @Override
    public void deleteByTaskId(Long taskId) {
        taskMapper.deleteByTaskId(taskId);
    }

    @Override
    public void deleteByTaskName(String taskName) {
        taskMapper.deleteByTaskName(taskName);
    }

    @Override
    public Result checkInfoId(Long id) {
        String key = TASK_INFO_ID + id;
        String value = cluster.get(key);
        if (value != null) {
            return Result.success("查询任务成功", JSON.parseObject(value));
        } else {
            Task task = this.getById(id);
            if (task != null) {
                cluster.set(key, JSON.toJSONString(task),5l, TimeUnit.MINUTES, true);
                return Result.success("查询任务成功", task);
            } else {
                return Result.fail("查询任务失败，无此id的任务");
            }
        }

    }

    @Override
    public Result checkInfoTaskId(Long taskId) {

        Task task = this.getByTaskId(taskId);
        if (task != null) {
            return Result.success("查询任务成功", task);
        } else {
            return Result.fail("查询任务失败，无此任务id的任务");
        }


    }

    @Override
    public Result checkInfoTaskName(String taskName) {

        Task task = this.getByTaskName(taskName);
        if (task != null) {
            return Result.success("查询任务成功", task);
        } else {
            return Result.fail("查询任务失败，无此任务名称的任务");
        }


    }

    @Override
    public Result getList() {
        List<Task> tasks = this.getAllTask();
        return Result.success("查询任务列表成功", tasks);

    }

    @Override
    public Result saveTask(Task task) {
        if (task.getId() == null || task.getTaskId() == null
                || task.getTaskName() == null || task.getTaskEnableStatus() == null) {
            return Result.fail("任务创建失败，缺少相应关键字");

        } else {
            long id = task.getId();
            long taskId = task.getTaskId();
            String taskName = task.getTaskName();
            if (this.getById(id) != null) {
                return Result.fail("任务创建失败，已存在此id的任务");
            } else if (this.getByTaskId(taskId) != null) {
                return Result.fail("任务创建失败，已存在此任务id的任务");
            } else if (this.getByTaskName(taskName) != null) {
                return Result.fail("任务创建失败，已存在此任务名称的任务");
            } else {
                this.save(task);
                return Result.success("任务创建成功", task);
            }
        }
    }

    @Override
    public Result updateTask(Task task) {
        if (task.getId() == null || task.getTaskId() == null
                || task.getTaskName() == null || task.getTaskEnableStatus() == null) {
            return Result.fail("任务更新失败，缺少相应的关键字");

        } else {
            long id = task.getId();
            long taskId = task.getTaskId();
            String taskName = task.getTaskName();
            if (this.getById(id) == null) {
                return Result.fail("任务更新失败，无此id的任务");
            } else if (this.getByTaskId(taskId) != null) {
                return Result.fail("任务更新失败，该任务id已存在");
            } else if (this.getByTaskName(taskName) != null) {
                return Result.fail("任务更新失败，该任务名称已存在");
            } else {
                this.updateById(task);
                this.deleteCache(id);
                return Result.success("任务更新成功", task);
            }
        }
    }

    @Override
    public Result checkDeleteId(Long id) {
        Task task = this.getById(id);
        if (task != null) {
            this.deleteById(id);
            this.deleteCache(id);
            return Result.success("删除任务成功", task);
        } else {
            return Result.fail("删除任务失败，无此id的任务");
        }
    }

    @Override
    public Result checkDeleteTaskId(Long taskId) {
        Task task = this.getByTaskId(taskId);
        if (task != null) {
            this.deleteByTaskId(taskId);
            this.deleteCache(task.getId());
            return Result.success("删除任务成功", task);
        } else {
            return Result.fail("删除任务失败，无此任务id的任务");
        }
    }

    @Override
    public Result checkDeleteTaskName(String taskName) {
        Task task = this.getByTaskName(taskName);
        if (task != null) {
            this.deleteByTaskName(taskName);
            this.deleteCache(task.getId());
            return Result.success("删除任务成功", task);
        } else {
            return Result.fail("删除任务失败，无此任务名称的任务");
        }
    }

    public void deleteCache(Long id) {
        String keyInfoId = TASK_INFO_ID + id;
        cluster.del(keyInfoId);
    }
}
