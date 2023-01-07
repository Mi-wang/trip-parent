package cn.wolfcode.dto;

import cn.wolfcode.domain.Destination;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wby
 * @version 1.0
 * @date 2023-01-07 007 19:24
 */
@Getter
@Setter
public class DestinationDTO implements Serializable {

    private Long id;
    private String name;        // 名称
    private String english;  // 英文名

    private Long parentId; // 上级目的地
    private String parentName;  // 上级目的名

    private String info;    // 简介
    private String coverUrl; // 封面地址

    private List<Destination> children = new ArrayList<>();
}
