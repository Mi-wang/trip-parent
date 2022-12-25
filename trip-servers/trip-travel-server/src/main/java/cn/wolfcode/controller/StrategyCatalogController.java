package cn.wolfcode.controller;

import cn.wolfcode.domain.Destination;
import cn.wolfcode.domain.StrategyCatalog;
import cn.wolfcode.query.BaseQuery;
import cn.wolfcode.service.IDestinationService;
import cn.wolfcode.service.IStrategyCatalogService;
import cn.wolfcode.vo.AjaxResult;
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
    public AjaxResult<?> query(BaseQuery qo) {
        Page<StrategyCatalog> page = strategyCatalogService.queryPage(qo);
        return AjaxResult.success(page);
    }

    @GetMapping("/groups")
    public AjaxResult<?> catalogGroup() {
        List<CatalogGroupVO> catalogGroupVOList = strategyCatalogService.groupList();
        return AjaxResult.success(catalogGroupVOList);
    }

    @GetMapping("/detail")
    public AjaxResult<?> detail(Long id) {
        return AjaxResult.success(strategyCatalogService.getById(id));
    }

    @PostMapping("/save")
    public AjaxResult<?> save(StrategyCatalog StrategyCatalog) {
        strategyCatalogService.save(StrategyCatalog);
        return AjaxResult.success();
    }

    @PostMapping("/update")
    public AjaxResult<?> update(StrategyCatalog StrategyCatalog) {
        strategyCatalogService.updateById(StrategyCatalog);
        return AjaxResult.success();
    }

    @PostMapping("/delete/{id}")
    public AjaxResult<?> delete(@PathVariable Long id) {
        if (id != null) {
            strategyCatalogService.removeById(id);
        }
        return AjaxResult.success();
    }

}