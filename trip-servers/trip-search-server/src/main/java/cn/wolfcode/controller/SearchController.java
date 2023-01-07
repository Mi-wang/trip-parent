package cn.wolfcode.controller;

import cn.wolfcode.qo.SearchQuery;
import cn.wolfcode.vo.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wby
 * @version 1.0
 * @date 2023-01-06 006 20:42
 */
@RestController
@RequestMapping("/q")
public class SearchController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(SearchController.class);

    @GetMapping
    public R<?> search(SearchQuery qo) {
        // 类型分发操作
        switch (qo.getType()) {
            case SearchQuery.SEARCH_TYPE_DEST:
                return this.searchForDest(qo);
            case SearchQuery.SEARCH_TYPE_STRATEGY:
                return this.searchForStrategy(qo);
            case SearchQuery.SEARCH_TYPE_TRAVEL:
                return this.searchForTravel(qo);
            case SearchQuery.SEARCH_TYPE_USER:
                return this.searchForUser(qo);
            default:
                return this.searchForAll(qo);
        }
    }

    private R<?> searchForUser(SearchQuery qo) {
        log.info("[搜索服务] 用户信息搜索: {}", qo);
        return R.ok();
    }

    private R<?> searchForTravel(SearchQuery qo) {
        log.info("[搜索服务] 游记信息搜索: {}", qo);
        return R.ok();
    }

    private R<?> searchForStrategy(SearchQuery qo) {
        log.info("[搜索服务] 攻略信息搜索: {}", qo);
        return R.ok();
    }

    private R<?> searchForDest(SearchQuery qo) {
        log.info("[搜索服务] 目的地信息搜索: {}", qo);
        return R.ok();
    }

    private R<?> searchForAll(SearchQuery qo) {
        log.info("[搜索服务] 全文搜索: {}", qo);
        return R.ok();
    }
}

