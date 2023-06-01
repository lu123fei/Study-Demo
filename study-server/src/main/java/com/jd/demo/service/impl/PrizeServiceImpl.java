package com.jd.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.jd.demo.common.lang.Result;
import com.jd.demo.entity.Prize;
import com.jd.demo.mapper.PrizeMapper;
import com.jd.demo.service.PrizeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jd.jim.cli.Cluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.jd.demo.common.lang.Constant.*;

/**
 * <p>
 * 奖品表 服务实现类
 * </p>
 *
 * @author lufei
 * @since 2023-05-17
 */
@Service
public class PrizeServiceImpl extends ServiceImpl<PrizeMapper, Prize> implements PrizeService {
    @Autowired
    PrizeMapper prizeMapper;
    @Resource
    private Cluster cluster;
    @Override
    public Prize getByPrizeId(Long prizeId) {
        return prizeMapper.selectByPrizeId(prizeId);
    }

    @Override
    public Prize getByPrizeName(String prizeName) {
        return prizeMapper.selectByPrizeName(prizeName);
    }

    @Override
    public List<Prize> getAllPrize() {
        return prizeMapper.selectAllPrize();
    }

    @Override
    public void deleteById(Long id) {
        prizeMapper.deleteById(id);
    }

    @Override
    public void deleteByPrizeId(Long prizeId) {
        prizeMapper.deleteByPrizeId(prizeId);
    }

    @Override
    public void deleteByPrizeName(String prizeName) {
        prizeMapper.deleteByPrizeName(prizeName);
    }

    @Override
    public Result checkInfoId(Long id) {
        String key = PRIZE_INFO_ID + id;
        String value = cluster.get(key);
        if (value != null) {
            return Result.success("查询奖品成功", JSON.parseObject(value));
        }else{
            Prize prize = this.getById(id);
            if(prize!=null){
                cluster.set(key, JSON.toJSONString(prize),5l, TimeUnit.MINUTES, true);
                return Result.success("查询奖品成功",prize);
            }else{
                return Result.fail("查询奖品失败，无此id的奖品");
            }
        }

    }

    @Override
    public Result checkInfoPrizeId(Long prizeId) {
        Prize prize = this.getByPrizeId(prizeId);
        if(prize!=null){
            return Result.success("查询奖品成功",prize);
        }else{
            return Result.fail("查询奖品失败，无此奖品id的奖品");
        }
    }

    @Override
    public Result checkInfoPrizeName(String prizeName) {
        Prize prize = this.getByPrizeName(prizeName);
        if(prize!=null){
            return Result.success("查询奖品成功",prize);
        }else{
            return Result.fail("查询奖品失败，无此奖品名称的奖品");
        }
    }

    @Override
    public Result getList() {
        List<Prize> prizes = this.getAllPrize();
        return Result.success("查询奖品列表成功",prizes);
    }

    @Override
    public Result savePrize(Prize prize) {
        if(prize.getId() == null || prize.getPrizeId() == null
                || prize.getPrizeName() == null ){
            return Result.fail("奖品创建失败，缺少相应关键字");

        }else{
            long id = prize.getId();
            long prizeId = prize.getPrizeId();
            String prizeName = prize.getPrizeName();
            if(this.getById(id)!=null){
                return Result.fail("奖品创建失败，已存在此id的奖品");
            }else if(this.getByPrizeId(prizeId)!=null){
                return Result.fail("奖品创建失败，已存在此奖品id的奖品");
            }else if(this.getByPrizeName(prizeName)!=null){
                return Result.fail("奖品创建失败，已存在此奖品名称的奖品");
            }else{
                this.save(prize);
                return Result.success("奖品创建成功",prize);
            }
        }
    }

    @Override
    public Result updatePrize(Prize prize) {
        if(prize.getId() == null || prize.getPrizeId() == null
                || prize.getPrizeName() == null){
            return Result.fail("奖品更新失败，缺少相应的关键字");

        }else{
            long id = prize.getId();
            long prizeId = prize.getPrizeId();
            String prizeName = prize.getPrizeName();
            if(this.getById(id)==null){
                return Result.fail("奖品更新失败，无此id的奖品");
            }else if(this.getByPrizeId(prizeId)!=null){
                return Result.fail("奖品更新失败，该奖品id已存在");
            }else if(this.getByPrizeName(prizeName)!=null){
                return Result.fail("奖品更新失败，该奖品名称已存在");
            }else{
                this.updateById(prize);
                this.deleteCache(id);
                return Result.success("奖品更新成功",prize);
            }
        }
    }

    @Override
    public Result checkDeleteId(Long id) {
        Prize prize = this.getById(id);
        if(prize!=null){
            this.deleteById(id);
            this.deleteCache(id);
            return Result.success("删除奖品成功",prize);
        }else{
            return Result.fail("删除奖品失败，无此id的奖品");
        }
    }

    @Override
    public Result checkDeletePrizeId(Long prizeId) {
        Prize prize = this.getByPrizeId(prizeId);
        if(prize!=null){
            this.deleteByPrizeId(prizeId);
            this.deleteCache(prize.getId());
            return Result.success("删除奖品成功",prize);
        }else{
            return Result.fail("删除奖品失败，无此奖品id的奖品");
        }
    }

    @Override
    public Result checkDeletePrizeName(String prizeName) {
        Prize prize = this.getByPrizeName(prizeName);
        if(prize!=null){
            this.deleteByPrizeName(prizeName);
            this.deleteCache(prize.getId());
            return Result.success("删除奖品成功",prize);
        }else{
            return Result.fail("删除奖品失败，无此奖品名称的奖品");
        }
    }

    public void deleteCache(Long id) {
        String keyInfoId = PRIZE_INFO_ID + id;
        cluster.del(keyInfoId);
    }
}
