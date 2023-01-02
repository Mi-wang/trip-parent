package cn.wolfcode.controller;


import cn.wolfcode.anno.RequestUser;
import cn.wolfcode.domain.TravelComment;
import cn.wolfcode.domain.UserInfo;
import cn.wolfcode.service.ITravelCommentService;
import cn.wolfcode.vo.AjaxResult;
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
    public AjaxResult<List<TravelComment>> query() {
        List<TravelComment> page = travelCommentService.page();
        return AjaxResult.success(page);
    }

    @PostMapping("/save")
    public AjaxResult<?> save(TravelComment travelComment, @RequestUser UserInfo userInfo) {
        travelCommentService.save(travelComment, userInfo);
        return AjaxResult.success();
    }


    @PostMapping("/delete")
    public AjaxResult<?> delete(String id) {
        travelCommentService.deleteById(id);
        return AjaxResult.success();
    }

}
