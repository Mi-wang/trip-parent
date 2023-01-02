package cn.wolfcode.service;

import cn.wolfcode.domain.Strategy;
import cn.wolfcode.domain.StrategyContent;
import cn.wolfcode.query.BaseQuery;
import cn.wolfcode.query.StrategyQuery;
import cn.wolfcode.redis.key.ArticleRedisPrefix;
import cn.wolfcode.vo.AjaxResult;
import cn.wolfcode.vo.ArticleStatVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/25 15:42
 */
public interface IStrategyService extends IService<Strategy> {

    Page<Strategy> queryPage(StrategyQuery qo);

    StrategyContent getContent(Long id);

    List<Strategy> viewnnumTop3(Long destId);

    ArticleStatVo veiwnumIncr(Long sid);

    Boolean isFavor(Long strategyId, Long userId);

    AjaxResult<ArticleStatVo> favornumIncr(Long strategyId, Long userId);
}
