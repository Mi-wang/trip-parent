package cn.wolfcode.service;


import cn.wolfcode.domain.TravelComment;
import cn.wolfcode.domain.UserInfo;

import java.util.List;

/**
 * @author 20463
 */
public interface ITravelCommentService {

    List<TravelComment> page();

    void save(TravelComment travelComment, UserInfo userInfo);

    void deleteById(String id);



}
