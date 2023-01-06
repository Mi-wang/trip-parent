package cn.wolfcode.controller;

import cn.wolfcode.domain.Banner;
import cn.wolfcode.query.BaseQuery;
import cn.wolfcode.service.IBannerService;
import cn.wolfcode.vo.AjaxResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wby
 * @version 1.0
 * @date 2023-01-06 006 18:47
 */
@RestController
@RequestMapping("/banners")
public class BannerController {

    @Autowired
    private IBannerService bannerService;

    @GetMapping("/query")
    public AjaxResult<?> query(BaseQuery qo) {
        Page<Banner> page = bannerService.queryPage(qo);
        return AjaxResult.success(page);
    }

    @GetMapping("/detail")
    public AjaxResult<?> detail(Long id) {
        return AjaxResult.success(bannerService.getById(id));
    }

    @PostMapping("/save")
    public AjaxResult<?> save(Banner banner) {
        bannerService.save(banner);
        return AjaxResult.success();
    }

    @GetMapping("/strategy")
    public AjaxResult<?> strategyList() {
        List<Banner> banners = bannerService.listByType(Banner.TYPE_STRATEGY, 5);
        return AjaxResult.success(banners);
    }

    @GetMapping("/travel")
    public AjaxResult<?> travelList() {
        List<Banner> banners = bannerService.listByType(Banner.TYPE_TRAVEL, 5);
        return AjaxResult.success(banners);
    }

    @PostMapping("/update")
    public AjaxResult<?> update(Banner banner) {
        bannerService.updateById(banner);
        return AjaxResult.success();
    }

    @PostMapping("/delete/{id}")
    public AjaxResult<?> delete(@PathVariable Long id) {
        if (id != null) {
            bannerService.removeById(id);
        }
        return AjaxResult.success();
    }
}

