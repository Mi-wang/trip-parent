package cn.wolfcode.service;

import cn.wolfcode.domain.StrategyComment;
import cn.wolfcode.domain.TravelComment;
import cn.wolfcode.domain.UserInfo;
import cn.wolfcode.query.StrategyCommentQuery;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author 20463
 */
public interface IStrategyCommentService {

    Page<StrategyComment> page(StrategyCommentQuery query);

    void save(StrategyComment strategyComment, UserInfo userInfo);

    void updateById(StrategyComment strategyComment);

    void deleteById(String id);

    void thumb(String commentId, Long userId);

}
