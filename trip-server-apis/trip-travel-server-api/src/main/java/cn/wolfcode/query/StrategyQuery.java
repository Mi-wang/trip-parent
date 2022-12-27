package cn.wolfcode.query;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 20463
 * @author wby
 * @version 1.0
 * @date 2022/12/26 18:57
 */
@Getter
@Setter
public class StrategyQuery extends BaseQuery {

    private Long destId;
    private Long themeId;
}
