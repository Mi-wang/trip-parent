package cn.wolfcode.controller;

import cn.wolfcode.dto.DestinationDTO;
import cn.wolfcode.dto.StrategyDTO;
import cn.wolfcode.dto.TravelDTO;
import cn.wolfcode.dto.UserInfoDTO;
import cn.wolfcode.feign.TravelServerFeignApi;
import cn.wolfcode.feign.UserServerFeignApi;
import cn.wolfcode.qo.SearchQuery;
import cn.wolfcode.vo.R;
import cn.wolfcode.vo.SearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author wby
 * @version 1.0
 * @date 2023-01-06 006 20:42
 */
@RestController
@RequestMapping("/q")
public class SearchController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private TravelServerFeignApi travelServerFeignApi;
    @Autowired
    private UserServerFeignApi userServerFeignApi;

    @GetMapping
    public R<?> search(SearchQuery qo) throws UnsupportedEncodingException {
        // URL 解码
        if (StringUtils.hasLength(qo.getKeyword())) {
            qo.setKeyword(URLDecoder.decode(qo.getKeyword(), "UTF-8"));
        }
        if (qo.getType() == null) {
            return R.err("没有这个城市");
        }
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

    private R<?> searchForDest(SearchQuery qo) {
        log.info("[搜索服务] 目的地信息搜索: {}", qo);

        // 1. 基于目的地名称查询目的地, 判断是否有目的地
        R<DestinationDTO> r = travelServerFeignApi.getDestByName(qo.getKeyword());

        // 从响应结果中获取数据
        DestinationDTO dest = r.data(DestinationDTO.class);
        if (dest == null) {
            // 2. 如果没有, 直接返回空的 result 对象和 qo 对象即可
            return R.ok(R.map().put("result", new SearchResult()).put("qo", qo));
        }

        SearchResult result = new SearchResult();

        // 3. 如果有, 基于目的地 id/名字 分别查询攻略/游记/用户 => Feign 远程调用
        // 查询攻略
        R<List<LinkedHashMap<String, Object>>> strategyRet = travelServerFeignApi.findStrategyByDestId(dest.getId());
        // 将 List<Map> 转换为 List<StrategyDTO>
        List<StrategyDTO> strategies = strategyRet.listData(strategyRet.data(), StrategyDTO.class);
        result.setTotal((long) strategies.size());
        if (strategies.size() > 5) {
            strategies = strategies.subList(0, 5);
        }

        // 查询游记
        R<List<LinkedHashMap<String, Object>>> travel = travelServerFeignApi.findTravelByDestId(dest.getId());
        List<TravelDTO> travelDTOS = strategyRet.listData(travel.data(), TravelDTO.class);
        result.setTotal(result.getTotal() + travelDTOS.size());
        if (travelDTOS.size() > 5) {
            travelDTOS = travelDTOS.subList(0, 5);
        }

        // 查询用户
        R<List<LinkedHashMap<String, Object>>> users = userServerFeignApi.findByDestName(dest.getName());
        List<UserInfoDTO> userInfoDTOS = strategyRet.listData(users.data(), UserInfoDTO.class);
        result.setTotal(result.getTotal() + userInfoDTOS.size());
        if (userInfoDTOS.size() > 5) {
            userInfoDTOS = userInfoDTOS.subList(0, 5);
        }

        // 封装结果
        result.setStrategies(strategies);
        result.setTravels(travelDTOS);
        result.setUsers(userInfoDTOS);

        // {"code": 200, "msg": "xxx", "data": {"dest": null, "result": null, "qo": null}}
        return R.ok(R.map()
                .put("dest", dest)
                .put("result", result)
                .put("qo", qo)
        );
    }

    private R<?> searchForStrategy(SearchQuery qo) {
        log.info("[搜索服务] 攻略信息搜索: {}", qo);
        return R.ok();
    }

    private R<?> searchForTravel(SearchQuery qo) {
        log.info("[搜索服务] 游记信息搜索: {}", qo);
        return R.ok();
    }

    private R<?> searchForUser(SearchQuery qo) {
        log.info("[搜索服务] 用户信息搜索: {}", qo);
        return R.ok();
    }

    private R<?> searchForAll(SearchQuery qo) {
        log.info("[搜索服务] 全文搜索: {}", qo);
        return R.ok();
    }
}
