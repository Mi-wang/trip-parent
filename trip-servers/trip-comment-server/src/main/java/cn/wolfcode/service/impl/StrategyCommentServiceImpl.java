package cn.wolfcode.service.impl;

import cn.wolfcode.domain.StrategyComment;
import cn.wolfcode.domain.UserInfo;
import cn.wolfcode.mongo.utils.MongoPageTemplate;
import cn.wolfcode.query.StrategyCommentQuery;
import cn.wolfcode.redis.key.ArticleRedisPrefix;
import cn.wolfcode.repository.StrategyCommentRepository;
import cn.wolfcode.service.IRedisService;
import cn.wolfcode.service.IStrategyCommentService;
import cn.wolfcode.vo.ArticleStatVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author 20463
 */
@Service
public class StrategyCommentServiceImpl implements IStrategyCommentService {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private MongoPageTemplate mongoPageTemplate;
    @Autowired
    private StrategyCommentRepository strategyCommentRepository;
    @Autowired
    private IRedisService redisService;
    @Override
    public Page<StrategyComment> page(StrategyCommentQuery qo) {
        // 构建查询条件
        // db.strategy_comment.find({strategyId: qo.getStrategyId()})
        Query query = Query.query(Criteria.where("strategyId").is(qo.getStrategyId()));

        return mongoPageTemplate.page(qo, StrategyComment.class, query);
    }

    @Override
    public void save(StrategyComment comment, UserInfo userInfo) {
        // 1. 补充用户信息(当前登录用户)
        comment.setUserId(userInfo.getId());
        comment.setNickname(userInfo.getNickname());
        comment.setCity(userInfo.getCity());
        comment.setHeadImgUrl(userInfo.getHeadImgUrl());
        comment.setLevel(userInfo.getLevel());

        // 2. 创建时间
        comment.setCreateTime(new Date());
        strategyCommentRepository.save(comment);

        redisService.hincr(ArticleRedisPrefix.STRATEGIES_STAT_PREFIX,
                ArticleStatVo.REPLAY_NUM,1L,comment.getStrategyId() + "");
    }

    @Override
    public void updateById(StrategyComment strategyComment) {
        // 查询对象
        // db.users.update({_id: xxxx}, {})
        Query query =
                new Query().addCriteria(Criteria.where("_id").is(strategyComment.getId()));

        // db.users.updateOne({_id: xxxx}, {$set: {strategyTitle: xxxxx}})
        Update update = new Update();
        // 如果该参数没有值, 就不更新该字段
        update.set("thumbupnum", strategyComment.getThumbupnum());
        update.set("thumbuplist", strategyComment.getThumbuplist());

        mongoTemplate.updateFirst(query, update, StrategyComment.class);
    }

    @Override
    public void deleteById(String id) {
        strategyCommentRepository.deleteById(id);
    }

    @Override
    public void thumb(String commentId, Long userId) {
        // 1. 基于评论 id 查询评论对象
        Optional<StrategyComment> optional = strategyCommentRepository.findById(commentId);

        // 如果该对象存在, 才执行里面的函数
        optional.ifPresent(comment -> {
            // 2. 判断当前用户是否在点赞集合中
            List<Long> thumbuplist = comment.getThumbuplist();
            if (thumbuplist.contains(userId)) {
                // 3. 如果在点赞集合中, 将其从该集合中删除, 且点赞数量 -1
                thumbuplist.remove(userId); // 引用传递
                comment.setThumbupnum(comment.getThumbupnum() - 1);
            } else {
                // 4. 如果不在, 加入集合中, 点赞数量 + 1
                thumbuplist.add(userId); // 引用传递
                comment.setThumbupnum(comment.getThumbupnum() + 1);
            }
            // 5. 更新点赞信息
            this.updateById(comment);
        });

    }
}
