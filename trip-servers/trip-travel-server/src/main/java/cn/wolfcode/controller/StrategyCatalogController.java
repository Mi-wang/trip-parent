package cn.wolfcode.controller;

import cn.wolfcode.domain.StrategyCatalog;
import cn.wolfcode.query.BaseQuery;
import cn.wolfcode.service.IStrategyCatalogService;
import cn.wolfcode.vo.R;
import cn.wolfcode.vo.CatalogGroupVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/25 15:32
 */
@RestController
@RequestMapping("/strategyCatalogs")
public class StrategyCatalogController {

    @Autowired
    private IStrategyCatalogService strategyCatalogService;

    @GetMapping("/query")
    public R<?> query(BaseQuery qo) {
        Page<StrategyCatalog> page = strategyCatalogService.queryPage(qo);
        return R.ok(page);
    }

    @GetMapping("/groups")
    public R<?> catalogGroup() {
        List<CatalogGroupVO> catalogGroupVOList = strategyCatalogService.groupList();
        return R.ok(catalogGroupVOList);
    }

    @GetMapping("/detail")
    public R<?> detail(Long id) {
        return R.ok(strategyCatalogService.getById(id));
    }

    @PostMapping("/save")
    public R<?> save(StrategyCatalog StrategyCatalog) {
        strategyCatalogService.save(StrategyCatalog);
        return R.ok();
    }

    @PostMapping("/update")
    public R<?> update(StrategyCatalog StrategyCatalog) {
        strategyCatalogService.updateById(StrategyCatalog);
        return R.ok();
    }

    @PostMapping("/delete/{id}")
    public R<?> delete(@PathVariable Long id) {
        if (id != null) {
            strategyCatalogService.removeById(id);
        }
        return R.ok();
    }

}