package com.jd.demo.service;

import com.jd.demo.common.lang.Result;
import com.jd.demo.entity.PrizeDistributionDetails;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 奖品分发明细表 服务类
 * </p>
 *
 * @author lufei
 * @since 2023-05-17
 */
public interface PrizeDistributionDetailsService extends IService<PrizeDistributionDetails> {

    List<PrizeDistributionDetails> getByParticipantId(Long participantId);

    List<PrizeDistributionDetails> getByTaskId(Long taskId);

    List<PrizeDistributionDetails> getByPrizeId(Long prizeId);

    List<PrizeDistributionDetails> getAllPrizeDistributionDetails();

    List<PrizeDistributionDetails> getByUnionQuery(Long participantId, Long taskId, Long prizeId);

    Result checkInfoId(Long id);

    Result checkInfoParticipantId(Long participantId);

    Result checkInfoTaskId(Long taskId);

    Result checkInfoPrizeId(Long prizeId);

    Result getList();

    Result checkInfoUnionQuery(Long participantId, Long taskId, Long prizeId);

    Result savePrizeDistributionDetails(PrizeDistributionDetails prizeDistributionDetails);

    Result checkDeleteId(Long id);
}
