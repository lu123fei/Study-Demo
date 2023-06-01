package com.jd.demo.mapper;

import com.jd.demo.entity.PrizeDistributionDetails;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 奖品分发明细表 Mapper 接口
 * </p>
 *
 * @author lufei
 * @since 2023-05-17
 */
public interface PrizeDistributionDetailsMapper extends BaseMapper<PrizeDistributionDetails> {

    List<PrizeDistributionDetails> selectByParticipantId(Long participantId);

    List<PrizeDistributionDetails> selectByTaskId(Long taskId);

    List<PrizeDistributionDetails> selectByPrizeId(Long prizeId);

    List<PrizeDistributionDetails> selectAllPrizeDistributionDetails();

    List<PrizeDistributionDetails> selectByUnionQuery(Long participantId, Long taskId, Long prizeId);
}
