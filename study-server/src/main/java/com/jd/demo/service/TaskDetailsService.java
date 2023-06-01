package com.jd.demo.service;

import com.jd.demo.common.lang.Result;
import com.jd.demo.entity.TaskDetails;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 任务明细表 服务类
 * </p>
 *
 * @author lufei
 * @since 2023-05-17
 */
public interface TaskDetailsService extends IService<TaskDetails> {

    List<TaskDetails> getByParticipantId(Long participantId);

    List<TaskDetails> getByTaskId(Long taskId);


    List<TaskDetails> getAllTaskDetails();

    List<TaskDetails> getByTaskIdAndParticipantId(Long taskId, Long participantId);

    Result checkInfoId(Long id);

    Result checkInfoParticipantId(Long participantId);

    Result checkInfoTaskId(Long taskId);

    Result checkInfoTaskIdAndParticipantId(Long taskId, Long participantId);

    Result getList();

    Result saveTaskDetails(TaskDetails taskDetails);

    Result checkDeleteId(Long id);
}
