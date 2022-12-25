package cn.wolfcode.controller;

import cn.wolfcode.domain.StrategyTheme;
import cn.wolfcode.query.BaseQuery;
import cn.wolfcode.service.IStrategyThemeService;
import cn.wolfcode.vo.AjaxResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/25 15:32
 */
@RestController
@RequestMapping("/strategyThemes")
public class StrategyThemeController {

    @Autowired
    private IStrategyThemeService strategyThemeService;

    @GetMapping("/query")
    public AjaxResult<?> query(BaseQuery qo) {
        Page<StrategyTheme> page = strategyThemeService.queryPage(qo);
        return AjaxResult.success(page);
    }

    @GetMapping("/list")
    public AjaxResult<?> list() {
        return AjaxResult.success(strategyThemeService.list());
    }

    @GetMapping("/detail")
    public AjaxResult<?> detail(Long id) {
        return AjaxResult.success(strategyThemeService.getById(id));
    }

    @PostMapping("/save")
    public AjaxResult<?> save(StrategyTheme strategyTheme) {
        strategyThemeService.save(strategyTheme);
        return AjaxResult.success();
    }

    @PostMapping("/update")
    public AjaxResult<?> update(StrategyTheme strategyTheme) {
        strategyThemeService.updateById(strategyTheme);
        return AjaxResult.success();
    }

    @PostMapping("/delete/{id}")
    public AjaxResult<?> delete(@PathVariable Long id) {
        if (id != null) {
            strategyThemeService.removeById(id);
        }
        return AjaxResult.success();
    }

}
