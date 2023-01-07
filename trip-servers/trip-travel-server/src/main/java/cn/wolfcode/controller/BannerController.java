package cn.wolfcode.controller;

import cn.wolfcode.domain.Banner;
import cn.wolfcode.query.BaseQuery;
import cn.wolfcode.service.IBannerService;
import cn.wolfcode.vo.R;
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
    public R<?> query(BaseQuery qo) {
        Page<Banner> page = bannerService.queryPage(qo);
        return R.ok(page);
    }

    @GetMapping("/detail")
    public R<?> detail(Long id) {
        return R.ok(bannerService.getById(id));
    }

    @PostMapping("/save")
    public R<?> save(Banner banner) {
        bannerService.save(banner);
        return R.ok();
    }

    @GetMapping("/strategy")
    public R<?> strategyList() {
        List<Banner> banners = bannerService.listByType(Banner.TYPE_STRATEGY, 5);
        return R.ok(banners);
    }

    @GetMapping("/travel")
    public R<?> travelList() {
        List<Banner> banners = bannerService.listByType(Banner.TYPE_TRAVEL, 5);
        return R.ok(banners);
    }

    @PostMapping("/update")
    public R<?> update(Banner banner) {
        bannerService.updateById(banner);
        return R.ok();
    }

    @PostMapping("/delete/{id}")
    public R<?> delete(@PathVariable Long id) {
        if (id != null) {
            bannerService.removeById(id);
        }
        return R.ok();
    }
}

