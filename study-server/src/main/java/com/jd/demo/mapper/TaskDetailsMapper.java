package com.jd.demo.mapper;

import com.jd.demo.entity.TaskDetails;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 任务明细表 Mapper 接口
 * </p>
 *
 * @author lufei
 * @since 2023-05-17
 */
public interface TaskDetailsMapper extends BaseMapper<TaskDetails> {

    List<TaskDetails> selectByParticipantId(Long participantId);

    List<TaskDetails> selectByTaskId(Long taskId);


    List<TaskDetails> selectAllTaskDetails();

    List<TaskDetails> selectByTaskIdAndParticipantId(Long taskId, Long participantId);
}
