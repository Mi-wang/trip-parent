package cn.wolfcode.service;


import cn.wolfcode.domain.TravelComment;
import cn.wolfcode.domain.UserInfo;
import cn.wolfcode.query.TravelCommentQuery;

import java.util.List;

/**
 * @author 20463
 */
public interface ITravelCommentService {

    List<TravelComment> page(TravelCommentQuery query);

    void save(TravelComment TravelComment, UserInfo userInfo);

    void deleteById(String id);
}
