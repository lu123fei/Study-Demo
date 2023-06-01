package com.jd.demo.service;

import com.jd.demo.common.lang.Result;
import com.jd.demo.entity.Prize;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 奖品表 服务类
 * </p>
 *
 * @author lufei
 * @since 2023-05-17
 */
public interface PrizeService extends IService<Prize> {

    Prize getByPrizeId(Long prizeId);

    Prize getByPrizeName(String prizeName);

    List<Prize> getAllPrize();

    void deleteById(Long id);

    void deleteByPrizeId(Long prizeId);

    void deleteByPrizeName(String prizeName);

    Result checkInfoId(Long id);

    Result checkInfoPrizeId(Long prizeId);

    Result checkInfoPrizeName(String prizeName);

    Result getList();

    Result savePrize(Prize prize);

    Result updatePrize(Prize prize);

    Result checkDeleteId(Long id);

    Result checkDeletePrizeId(Long prizeId);

    Result checkDeletePrizeName(String prizeName);
}
