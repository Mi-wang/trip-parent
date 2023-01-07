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

    @GetMapping("/query")
    public R<?> query(TravelQuery qo) {
        Page<Travel> page = travelService.queryPage(qo);
        return R.ok(page);
    }

    @GetMapping("/list")
    public R<?> list() {
        return R.ok(travelService.list());
    }

    @GetMapping("/findByDestId")
    public R<?> findByDestId(Long destId) {
        return R.ok(travelService.findByDestId(destId));
    }

    @GetMapping("/detail")
    public R<?> detail(Long id) {
        return R.ok(travelService.getById(id));
    }

    @GetMapping("/content")
    public R<?> content(Long id) {
        return R.ok(travelService.getContent(id));
    }

    @GetMapping("/viewnnumTop3")
    public R<?> viewnnumTop3() {
        return R.ok(travelService.viewnnumTop3());
    }

    @PostMapping("/audit")
    public R<?> audit(Long id, Integer state) {
        travelService.audit(id, state);
        return R.ok();
    }

    @PostMapping("/save")
    public R<?> save(Travel travel) {
        travelService.save(travel);
        return R.ok();
    }

    @PostMapping("/update")
    public R<?> update(Travel travel) {
        travelService.updateById(travel);
        return R.ok();
    }

    @PostMapping("/delete/{id}")
    public R<?> delete(@PathVariable Long id) {
        if (id != null) {
            travelService.removeById(id);
        }
        return R.ok();
    }

}