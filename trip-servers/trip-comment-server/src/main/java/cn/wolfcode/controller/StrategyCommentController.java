package cn.wolfcode.controller;

import cn.wolfcode.anno.RequestUser;
import cn.wolfcode.domain.StrategyComment;
import cn.wolfcode.domain.UserInfo;
import cn.wolfcode.query.StrategyCommentQuery;
import cn.wolfcode.service.IStrategyCommentService;
import cn.wolfcode.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 20463
 */
@RequestMapping("/strategies/comments")
@RestController
public class StrategyCommentController {

    @Autowired
    private IStrategyCommentService strategyCommentService;

    @GetMapping("/query")
    public R<Page<StrategyComment>> query(StrategyCommentQuery query) {
        Page<StrategyComment> page = strategyCommentService.page(query);
        return R.ok(page);
    }

    @PostMapping("/save")
    public R<?> save(StrategyComment comment, @RequestUser UserInfo userInfo) {
        strategyCommentService.save(comment, userInfo);
        return R.ok();
    }

    @PostMapping("/thumb")
    public R<?> thumb(String cid, @RequestUser UserInfo userInfo) {
        // 进行点赞操作, 判断当前用户是否点过赞, 如果已经点过取消点赞
        strategyCommentService.thumb(cid, userInfo.getId());
        return R.ok();
    }

    @PostMapping("/test")
    public R<?> test(@RequestUser UserInfo userInfo) {
        System.out.println("userInfo = " + userInfo);
        return R.ok(userInfo);
    }

    @PostMapping("/update")
    public R<?> update(StrategyComment comment) {
        strategyCommentService.updateById(comment);
        return R.ok();
    }

    @PostMapping("/delete")
    public R<?> delete(String id) {
        strategyCommentService.deleteById(id);
        return R.ok();
    }
}
