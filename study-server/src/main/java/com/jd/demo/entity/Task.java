package com.jd.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 任务表
 * </p>
 *
 * @author lufei
 * @since 2023-05-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 任务id
     */
    private Long taskId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务开始时间
     */
    private LocalDate taskStartTime;

    /**
     * 任务结束时间
     */
    private LocalDate taskEndTime;

    /**
     * 任务启用状态 -1删除 0停用 1启用 2屏蔽
     */
    private Integer taskEnableStatus;

    /**
     * 任务范围即参与任务的人员名字
     */
    private String taskScope;

    /**
     * 任务奖品id
     */
    private Integer taskPrizeId;

    /**
     * 任务参与人数
     */
    private Integer taskParticipantsNumber;

}
