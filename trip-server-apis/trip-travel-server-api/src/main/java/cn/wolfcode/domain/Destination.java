package cn.wolfcode.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/22 10:30
 * 目的地(行政地区：国家/省份/城市)
 */
@Setter
@Getter
@TableName("destination")
public class Destination extends BaseDomain {

    //名称
    private String name;
    //英文名
    private String english;
    //上级目的地
    private Long parentId;
    //上级目的名
    private String parentName;
    //简介
    private String info;
    private String coverUrl;

    @TableField(exist = false)
    private List<Destination> children = new ArrayList<>();
}