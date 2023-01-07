package cn.wolfcode.controller;

import cn.wolfcode.domain.Destination;
import cn.wolfcode.domain.Region;
import cn.wolfcode.query.BaseQuery;
import cn.wolfcode.service.IDestinationService;
import cn.wolfcode.service.IRegionService;
import cn.wolfcode.vo.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/22 19:52
 */
@RestController
@RequestMapping("/regions")
public class RegionController {

    @Autowired
    private IRegionService regionService;

    @Autowired
    private IDestinationService destinationService;

    @GetMapping("/query")
    public R<?> query(BaseQuery qo) {
        Page<Region> page = regionService.queryPage(qo);
        return R.ok(page);
    }

    @GetMapping("/{id}/destination")
    public R<?> destinations(@PathVariable Long id) {
        Region region = regionService.getById(id);
        // 获取到区域对象管理的所有目的地 id
        List<Long> destIds = region.parseRefIds();

        return R.ok(destinationService.listByIds(destIds));
    }

    @GetMapping("/detail")
    public R<?> detail(Long id) {
        return R.ok(regionService.getById(id));
    }

    @PostMapping("/save")
    public R<?> save(Region region) {
        regionService.save(region);
        return R.ok();
    }

    @PostMapping("/update")
    public R<?> update(Region region) {
        regionService.updateById(region);
        return R.ok();
    }

    @PostMapping("/delete/{id}")
    public R<?> delete(@PathVariable Long id) {
        if (id != null) {
            regionService.removeById(id);
        }
        return R.ok();
    }

    @GetMapping("/destination")
    public R<?> dests(Long rid) {
        List<Destination> list = destinationService.queryByRegionId(rid);
        return R.ok(list);
    }

    @GetMapping("/hot")
    public R<?> hotList() {
        List<Region> list = regionService.queryHotList();
        return R.ok(list);
    }
}

