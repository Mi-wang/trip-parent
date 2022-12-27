package cn.wolfcode.controller;

import cn.wolfcode.domain.Travel;
import cn.wolfcode.query.BaseQuery;
import cn.wolfcode.query.TravelQuery;
import cn.wolfcode.service.ITravelService;
import cn.wolfcode.vo.AjaxResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/27 11:42
 */
@RestController
@RequestMapping("/travels")
public class TravelController {

    @Autowired
    private ITravelService travelService;


    @GetMapping("/query")
    public AjaxResult<?> query(TravelQuery qo) {
        Page<Travel> page = travelService.queryPage(qo);
        return AjaxResult.success(page);
    }
    @GetMapping("/content")
    public AjaxResult<?> content(Long id) {
        return AjaxResult.success(travelService.getContent(id));
    }

    @GetMapping("/viewnnumTop3")
    public AjaxResult<?> viewnnumTop3() {
        return AjaxResult.success(travelService.viewnnumTop3());
    }

    @PostMapping("/audit")
    public AjaxResult<?> audit(Long id, Integer state) {
        travelService.audit(id, state);
        return AjaxResult.success();
    }


    @GetMapping("/detail")
    public AjaxResult<?> detail(Long id) {
        return AjaxResult.success(travelService.getById(id));
    }

    @PostMapping("/save")
    public AjaxResult<?> save(Travel travel) {
        travelService.save(travel);
        return AjaxResult.success();
    }

    @PostMapping("/update")
    public AjaxResult<?> update(Travel travel) {
        travelService.updateById(travel);
        return AjaxResult.success();
    }

    @PostMapping("/delete/{id}")
    public AjaxResult<?> delete(@PathVariable Long id) {
        if (id != null) {
            travelService.removeById(id);
        }
        return AjaxResult.success();
    }

}

