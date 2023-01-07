package cn.wolfcode.controller;


import cn.wolfcode.anno.RequestUser;
import cn.wolfcode.domain.TravelComment;
import cn.wolfcode.domain.UserInfo;
import cn.wolfcode.query.TravelCommentQuery;
import cn.wolfcode.service.ITravelCommentService;
import cn.wolfcode.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 20463
 */
@RequestMapping("/travels/comments")
@RestController
public class TravelCommentController {

    @Autowired
    private ITravelCommentService travelCommentService;

    @GetMapping("/query")
    public R<List<TravelComment>> query(TravelCommentQuery query) {
        List<TravelComment> page = travelCommentService.page(query);
        return R.ok(page);
    }

    @PostMapping("/save")
    public R<?> save(TravelComment comment, @RequestUser UserInfo userInfo) {
        travelCommentService.save(comment, userInfo);
        return R.ok();
    }

    @PostMapping("/delete")
    public R<?> delete(String id) {
        travelCommentService.deleteById(id);
        return R.ok();
    }
}
