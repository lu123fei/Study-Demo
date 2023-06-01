package com.jd.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 任务明细表
 * </p>
 *
 * @author lufei
 * @since 2023-05-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TaskDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 人员id
     */
    private Long participantId;

    /**
     * 任务id
     */
    private Long taskId;

    /**
     * 任务完成时间
     */
    private LocalDate taskFinishTime;

    public TaskDetails(Long id,Long participantId,Long taskId,LocalDate taskFinishTime){
        this.id = id;
        this.participantId = participantId;
        this.taskId = taskId;
        this.taskFinishTime = taskFinishTime;
    }

}
