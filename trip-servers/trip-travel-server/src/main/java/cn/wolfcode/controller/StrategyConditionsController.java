package cn.wolfcode.controller;

import cn.wolfcode.domain.StrategyCondition;
import cn.wolfcode.domain.StrategyRank;
import cn.wolfcode.service.IStrategyConditionService;
import cn.wolfcode.service.IStrategyRankService;
import cn.wolfcode.vo.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wby
 * @version 1.0
 * @date 2022-12-27 9:22
 */
@RestController
@RequestMapping("/strategies/conditions")
public class StrategyConditionsController extends BaseController {

    @Autowired
    private IStrategyConditionService strategyConditionService;

    @GetMapping
    public AjaxResult<?> index(int type) {
        List<StrategyCondition> list =  strategyConditionService.queryByType(type);
        return AjaxResult.success(list);
    }
}
