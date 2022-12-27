package cn.wolfcode.query;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wby
 * @version 1.0
 * @date 2022-12-27 027 15:50
 */
@Getter
@Setter
public class TravelQuery {
    private Long destId;
    private String orderBy = "viewnum";
}
