package cn.wolfcode.service.impl;

import cn.wolfcode.domain.TravelComment;
import cn.wolfcode.domain.UserInfo;
import cn.wolfcode.repository.TravelCommentRepository;
import cn.wolfcode.service.ITravelCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
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
    public List<TravelComment> page() {
        return mongoTemplate.find(new Query(),TravelComment.class);
    }

    @Override
    public void save(TravelComment travelComment, UserInfo userInfo) {
        travelComment.setUserId(userInfo.getId());
        travelComment.setNickname(userInfo.getNickname());
        travelComment.setCity(userInfo.getCity());
        travelComment.setHeadImgUrl(userInfo.getHeadImgUrl());
        travelComment.setLevel(userInfo.getLevel());

        //创建时间
        travelComment.setCreateTime(new Date());
        travelCommentRepository.save(travelComment);
    }

    @Override
    public void deleteById(String id) {
        travelCommentRepository.deleteById(id);
    }
}
