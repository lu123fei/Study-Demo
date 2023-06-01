package com.jd.demo.mapper;

import com.jd.demo.entity.Prize;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 奖品表 Mapper 接口
 * </p>
 *
 * @author lufei
 * @since 2023-05-17
 */
public interface PrizeMapper extends BaseMapper<Prize> {

    Prize selectByPrizeId(Long prizeId);

    Prize selectByPrizeName(String prizeName);

    List<Prize> selectAllPrize();

    void deleteByPrizeId(Long prizeId);

    void deleteByPrizeName(String prizeName);
}
