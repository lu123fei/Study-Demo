package com.jd.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 奖品分发明细表
 * </p>
 *
 * @author lufei
 * @since 2023-05-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PrizeDistributionDetails implements Serializable {

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
     * 奖品id
     */
    private Long prizeId;

    /**
     * 奖品发送时间
     */
    private LocalDate prizeDistributionTime;


}
