package cn.wolfcode.qo;

import cn.wolfcode.query.BaseQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wby
 * @version 1.0
 * @date 2023-01-06 006 20:43
 */
@Getter
@Setter
public class SearchQuery extends BaseQuery {

    /* 搜索类型: 全部 */
    public static final int SEARCH_TYPE_ALL = -1;
    /* 搜索类型: 目的地 */
    public static final int SEARCH_TYPE_DEST = 0;
    /* 搜索类型: 攻略 */
    public static final int SEARCH_TYPE_STRATEGY = 1;
    /* 搜索类型: 游记 */
    public static final int SEARCH_TYPE_TRAVEL = 2;
    /* 搜索类型: 用户 */
    public static final int SEARCH_TYPE_USER = 3;

    private Integer type = SEARCH_TYPE_ALL;

    @Override
    public String toString() {
        return "SearchQuery{" +
                "type=" + type +
                "keyword=" + getKeyword() +
                '}';
    }
}
