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

    private String orderBy;
    private Long refid = -1L;
    private Integer type = -1;
    private Long destId;
    private Long themeId;

    public Long getRefid() {
        return refid == null ? -1L : refid;
    }

    public Integer getType() {
        return type == null ? -1 : type;
    }
}
