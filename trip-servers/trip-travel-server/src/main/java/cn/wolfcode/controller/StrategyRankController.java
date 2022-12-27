package cn.wolfcode.controller;

import cn.wolfcode.domain.StrategyRank;
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
@RequestMapping("/strategies/ranks")
public class StrategyRankController extends BaseController {

    @Autowired
    private IStrategyRankService strategyRankService;

    @GetMapping
    public AjaxResult<?> index(int type) {
        List<StrategyRank> list =  strategyRankService.queryByType(type);
        return AjaxResult.success(list);
    }
}
