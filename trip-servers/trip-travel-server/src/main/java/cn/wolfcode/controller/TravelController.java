package cn.wolfcode.controller;

import cn.wolfcode.domain.Travel;
import cn.wolfcode.query.TravelQuery;
import cn.wolfcode.service.ITravelService;
import cn.wolfcode.vo.R;
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


    @GetMapping("/list")
    public R<?> list() {
        return R.success(travelService.list());
    }

    @GetMapping("/query")
    public R<?> query(TravelQuery qo) {
        Page<Travel> page = travelService.queryPage(qo);
        return R.success(page);
    }

    @GetMapping("/content")
    public R<?> content(Long id) {
        return R.success(travelService.getContent(id));
    }

    @GetMapping("/viewnnumTop3")
    public R<?> viewnnumTop3() {
        return R.success(travelService.viewnnumTop3());
    }

    @PostMapping("/audit")
    public R<?> audit(Long id, Integer state) {
        travelService.audit(id, state);
        return R.success();
    }


    @GetMapping("/detail")
    public R<?> detail(Long id) {
        return R.success(travelService.getById(id));
    }

    @PostMapping("/save")
    public R<?> save(Travel travel) {
        travelService.save(travel);
        return R.success();
    }

    @PostMapping("/update")
    public R<?> update(Travel travel) {
        travelService.updateById(travel);
        return R.success();
    }

    @PostMapping("/delete/{id}")
    public R<?> delete(@PathVariable Long id) {
        if (id != null) {
            travelService.removeById(id);
        }
        return R.success();
    }

}

