package com.jd.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 奖品表
 * </p>
 *
 * @author lufei
 * @since 2023-05-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Prize implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 奖品id
     */
    private Long prizeId;

    /**
     * 奖品名称
     */
    private String prizeName;


}
