package cn.wolfcode.controller;

import cn.wolfcode.domain.Destination;
import cn.wolfcode.domain.Region;
import cn.wolfcode.query.BaseQuery;
import cn.wolfcode.service.IDestinationService;
import cn.wolfcode.service.IRegionService;
import cn.wolfcode.vo.AjaxResult;
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
    public AjaxResult<?> query(BaseQuery qo) {
        Page<Region> page = regionService.queryPage(qo);
        return AjaxResult.success(page);
    }

    @GetMapping("/{id}/destination")
    public AjaxResult<?> destinations(@PathVariable Long id) {
        Region region = regionService.getById(id);
        // 获取到区域对象管理的所有目的地 id
        List<Long> destIds = region.parseRefIds();

        return AjaxResult.success(destinationService.listByIds(destIds));
    }

    @GetMapping("/detail")
    public AjaxResult<?> detail(Long id) {
        return AjaxResult.success(regionService.getById(id));
    }

    @PostMapping("/save")
    public AjaxResult<?> save(Region region) {
        regionService.save(region);
        return AjaxResult.success();
    }

    @PostMapping("/update")
    public AjaxResult<?> update(Region region) {
        regionService.updateById(region);
        return AjaxResult.success();
    }

    @PostMapping("/delete/{id}")
    public AjaxResult<?> delete(@PathVariable Long id) {
        if (id != null) {
            regionService.removeById(id);
        }
        return AjaxResult.success();
    }

    @GetMapping("/destination")
    public AjaxResult<?> dests(Long rid) {
        List<Destination> list = destinationService.queryByRegionId(rid);
        return AjaxResult.success(list);
    }

    @GetMapping("/hot")
    public AjaxResult<?> hotList() {
        List<Region> list = regionService.queryHotList();
        return AjaxResult.success(list);
    }
}

