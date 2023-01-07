package cn.wolfcode.controller;


import cn.wolfcode.anno.RequestUser;
import cn.wolfcode.domain.TravelComment;
import cn.wolfcode.domain.UserInfo;
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
@RestController
@RequestMapping("/strategies/travels")
public class TravelCommentController {
    @Autowired
    private ITravelCommentService travelCommentService;

    @GetMapping("/query")
    public R<List<TravelComment>> query() {
        List<TravelComment> page = travelCommentService.page();
        return R.success(page);
    }

    @PostMapping("/save")
    public R<?> save(TravelComment travelComment, @RequestUser UserInfo userInfo) {
        travelCommentService.save(travelComment, userInfo);
        return R.success();
    }


    @PostMapping("/delete")
    public R<?> delete(String id) {
        travelCommentService.deleteById(id);
        return R.success();
    }

}
