package cn.wolfcode.controller;

import cn.wolfcode.anno.RequestUser;
import cn.wolfcode.domain.StrategyComment;
import cn.wolfcode.domain.UserInfo;
import cn.wolfcode.query.StrategyCommentQuery;
import cn.wolfcode.service.IStrategyCommentService;
import cn.wolfcode.vo.AjaxResult;
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
    public AjaxResult<Page<StrategyComment>> query(StrategyCommentQuery query) {
        Page<StrategyComment> page = strategyCommentService.page(query);
        return AjaxResult.success(page);
    }

    @PostMapping("/save")
    public AjaxResult<?> save(StrategyComment comment, @RequestUser UserInfo userInfo) {
        strategyCommentService.save(comment, userInfo);
        return AjaxResult.success();
    }

    @PostMapping("/thumb")
    public AjaxResult<?> thumb(String cid, @RequestUser UserInfo userInfo) {
        // 进行点赞操作, 判断当前用户是否点过赞, 如果已经点过取消点赞
        strategyCommentService.thumb(cid, userInfo.getId());
        return AjaxResult.success();
    }

    @PostMapping("/test")
    public AjaxResult<?> test(@RequestUser UserInfo userInfo) {
        System.out.println("userInfo = " + userInfo);
        return AjaxResult.success(userInfo);
    }

    @PostMapping("/update")
    public AjaxResult<?> update(StrategyComment comment) {
        strategyCommentService.updateById(comment);
        return AjaxResult.success();
    }

    @PostMapping("/delete")
    public AjaxResult<?> delete(String id) {
        strategyCommentService.deleteById(id);
        return AjaxResult.success();
    }
}
