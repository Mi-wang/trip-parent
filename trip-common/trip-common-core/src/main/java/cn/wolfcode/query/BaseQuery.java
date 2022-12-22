package cn.wolfcode.query;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/22 10:48
 */
@Setter
@Getter
public class BaseQuery {

    private Integer currentPage = 1;
    private Integer pageSize = 10;

    private String keyword;

    public Integer getStart() {
        return (currentPage - 1) * pageSize;
    }
}
