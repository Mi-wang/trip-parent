package cn.wolfcode.service.impl;

import cn.wolfcode.domain.TravelComment;
import cn.wolfcode.domain.UserInfo;
import cn.wolfcode.query.TravelCommentQuery;
import cn.wolfcode.repository.TravelCommentRepository;
import cn.wolfcode.service.ITravelCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TravelCommentServiceImpl implements ITravelCommentService {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private TravelCommentRepository travelCommentRepository;

    @Override
    public List<TravelComment> page(TravelCommentQuery qo) {
        // 构建查询条件
        Query query = Query.query(Criteria.where("travelId").is(qo.getTravelId()));

        return mongoTemplate.find(query, TravelComment.class);
    }

    @Override
    public void save(TravelComment comment, UserInfo userInfo) {
        // 1. 补充用户信息(当前登录用户)
        comment.setUserId(userInfo.getId());
        comment.setNickname(userInfo.getNickname());
        comment.setCity(userInfo.getCity());
        comment.setHeadImgUrl(userInfo.getHeadImgUrl());
        comment.setLevel(userInfo.getLevel());

        // 被回复的评论
        TravelComment refComment = comment.getRefComment();
        if (refComment != null && refComment.getId() != null) {
            // 基于 id 查询出被回复的对象, 并重新覆盖当前对象
            TravelComment fullRefComment = mongoTemplate.findById(refComment.getId(), TravelComment.class);
            comment.setRefComment(fullRefComment);
        }

        // 2. 创建时间
        comment.setCreateTime(new Date());
        travelCommentRepository.save(comment);
    }

    @Override
    public void deleteById(String id) {
        travelCommentRepository.deleteById(id);
    }
}