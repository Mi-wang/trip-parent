package cn.wolfcode.vo;

import cn.wolfcode.domain.StrategyCatalog;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/25 16:29
 */
@Getter
@Setter
public class CatalogGroupVO {

    private String destName;
    private List<StrategyCatalog> catalogList;
}
