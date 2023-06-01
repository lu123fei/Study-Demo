package com.jd.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.jd.demo.common.lang.Result;
import com.jd.demo.entity.PrizeDistributionDetails;
import com.jd.demo.entity.Task;
import com.jd.demo.entity.TaskDetails;
import com.jd.demo.mapper.PrizeDistributionDetailsMapper;
import com.jd.demo.service.PrizeDistributionDetailsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jd.demo.service.TaskDetailsService;
import com.jd.demo.service.TaskService;
import com.jd.jim.cli.Cluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.jd.demo.common.lang.Constant.*;

/**
 * <p>
 * 奖品分发明细表 服务实现类
 * </p>
 *
 * @author lufei
 * @since 2023-05-17
 */
@Service
public class PrizeDistributionDetailsServiceImpl extends ServiceImpl<PrizeDistributionDetailsMapper, PrizeDistributionDetails> implements PrizeDistributionDetailsService {
    @Autowired
    PrizeDistributionDetailsMapper prizeDistributionDetailsMapper;
    @Autowired
    TaskService taskService;
    @Autowired
    TaskDetailsService taskDetailsService;
    @Resource
    private Cluster cluster;
    @Override
    public List<PrizeDistributionDetails> getByParticipantId(Long participantId) {
        return prizeDistributionDetailsMapper.selectByParticipantId(participantId);
    }

    @Override
    public List<PrizeDistributionDetails> getByTaskId(Long taskId) {
        return prizeDistributionDetailsMapper.selectByTaskId(taskId);
    }

    @Override
    public List<PrizeDistributionDetails> getByPrizeId(Long prizeId) {
        return prizeDistributionDetailsMapper.selectByPrizeId(prizeId);
    }

    @Override
    public List<PrizeDistributionDetails> getAllPrizeDistributionDetails() {
        return prizeDistributionDetailsMapper.selectAllPrizeDistributionDetails();
    }

    @Override
    public List<PrizeDistributionDetails> getByUnionQuery(Long participantId, Long taskId, Long prizeId) {
        return prizeDistributionDetailsMapper.selectByUnionQuery(participantId,taskId,prizeId);
    }

    @Override
    public Result checkInfoId(Long id) {
        String key = PRIZE_DISTRIBUTION_DETAILS_INFO_ID + id;
        String value = cluster.get(key);
        if (value != null) {
            return Result.success("查询奖品分发明细成功", JSON.parseObject(value));
        } else{
            PrizeDistributionDetails prizeDistributionDetails = this.getById(id);
            if (prizeDistributionDetails != null) {
                cluster.set(key, JSON.toJSONString(prizeDistributionDetails),5l, TimeUnit.MINUTES, true);
                return Result.success("查询奖品分发明细成功", prizeDistributionDetails);
            } else {
                return Result.fail("查询奖品分发明细失败，无此id的奖品分发明细");
            }
        }

    }

    @Override
    public Result checkInfoParticipantId(Long participantId) {
        List<PrizeDistributionDetails> prizeDistributionDetails = this.getByParticipantId(participantId);
        if (prizeDistributionDetails.size() != 0) {
            return Result.success("查询奖品分发明细成功", prizeDistributionDetails);
        } else {
            return Result.fail("查询奖品分发明细失败，无此人员id的奖品分发明细");
        }
    }

    @Override
    public Result checkInfoTaskId(Long taskId) {
        List<PrizeDistributionDetails> prizeDistributionDetails = this.getByTaskId(taskId);
        if (prizeDistributionDetails.size() != 0) {
            return Result.success("查询奖品分发明细成功", prizeDistributionDetails);
        } else {
            return Result.fail("查询奖品分发明细失败，无此任务id的奖品分发明细");
        }
    }

    @Override
    public Result checkInfoPrizeId(Long prizeId) {
        List<PrizeDistributionDetails> prizeDistributionDetails = this.getByPrizeId(prizeId);
        if (prizeDistributionDetails.size() != 0) {
            return Result.success("查询奖品分发明细成功", prizeDistributionDetails);
        } else {
            return Result.fail("查询奖品分发明细失败，无此奖品id的奖品分发明细");
        }
    }

    @Override
    public Result getList() {
        List<PrizeDistributionDetails> prizeDistributionDetails = this.getAllPrizeDistributionDetails();
        return Result.success("查询奖品分发明细成功", prizeDistributionDetails);
    }

    @Override
    public Result checkInfoUnionQuery(Long participantId, Long taskId, Long prizeId) {
        List<PrizeDistributionDetails> prizeDistributionDetails = this.getByUnionQuery(participantId, taskId, prizeId);
        if (prizeDistributionDetails.size() != 0) {
            return Result.success("查询奖品分发明细成功", prizeDistributionDetails);
        } else {
            return Result.fail("查询奖品分发明细失败，无此人员id、任务id、奖品id对应的奖品分发明细");
        }
    }

    @Override
    public Result savePrizeDistributionDetails(PrizeDistributionDetails prizeDistributionDetails) {
        if (prizeDistributionDetails.getId() == null || prizeDistributionDetails.getPrizeId() == null
                || prizeDistributionDetails.getTaskId() == null || prizeDistributionDetails.getParticipantId() == null || prizeDistributionDetails.getPrizeDistributionTime() == null) {
            return Result.fail("奖品分发明细创建失败，缺少相应关键字");

        } else {
            long id = prizeDistributionDetails.getId();
            long prizeId = prizeDistributionDetails.getPrizeId();
            long participantId = prizeDistributionDetails.getParticipantId();
            long taskId = prizeDistributionDetails.getTaskId();
            Result result = taskService.checkInfoTaskId(taskId);
            if(result.getCode()==500){
                return Result.fail("任务状态异常");
            }
            Task task = taskService.getByTaskId(taskId);
            if(taskDetailsService.getByTaskIdAndParticipantId(taskId,participantId)==null){
                return Result.fail("奖品分发明细创建失败，该人员并未完成此任务");
            }else if(prizeId!=task.getTaskPrizeId()){
                return Result.fail("奖品分发明细创建失败，任务与奖品不对应");
            }else if(this.getById(id)!=null){
                return Result.fail("奖品分发明细创建失败，该奖品分发明细已创建");
            }else{
                this.save(prizeDistributionDetails);
                return Result.success("奖品分发明细创建成功",prizeDistributionDetails);
            }
        }
    }

    @Override
    public Result checkDeleteId(Long id) {
        PrizeDistributionDetails prizeDistributionDetails = this.getById(id);
        if(prizeDistributionDetails!=null){
            prizeDistributionDetailsMapper.deleteById(id);
            this.deleteCache(id);
            return Result.success("删除奖品分发明细成功",prizeDistributionDetails);
        }else{
            return Result.fail("删除奖品分发失败，无此id的奖品分发明细");
        }
    }
    public void deleteCache(Long id) {
        String keyInfoId = PRIZE_DISTRIBUTION_DETAILS_INFO_ID + id;
        cluster.del(keyInfoId);
    }
}
