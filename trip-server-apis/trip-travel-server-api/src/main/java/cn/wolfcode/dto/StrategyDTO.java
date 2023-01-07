package cn.wolfcode.dto;

import cn.wolfcode.domain.StrategyContent;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author wby
 * @version 1.0
 * @date 2023-01-07 007 19:24
 */
@Setter
@Getter
public class StrategyDTO implements Serializable {

    private Long id;
    private Long destId;  //关联的目的地
    private String destName;
    private Long themeId; //关联主题
    private String themeName;
    private Long catalogId;  //关联的分类
    private String catalogName;
    private String title;  //标题
    private String subTitle; //副标题
    private String summary;  //内容摘要
    private String coverUrl;  //封面
    private Date createTime;  //创建时间
    private Integer isabroad;  //是否是国外
    private Integer viewnum = 0;  //点击数
    private Integer replynum = 0;  //攻略评论数
    private Integer favornum = 0; //收藏数
    private Integer sharenum = 0; //分享数
    private Integer thumbsupnum = 0; //点赞个数
    private Integer state;  //状态
    private StrategyContent content; //攻略内容


    public void map2this(LinkedHashMap<String, Object> map) {
        this.id = (Long) map.get("id");
        this.destId = (Long) map.get("destId");
        this.destName = (String) map.get("destName");
        this.themeId = (Long) map.get("themeId");
        this.themeName = (String) map.get("themeName");
        this.catalogId = (Long) map.get("catalogId");
        this.catalogName = (String) map.get("catalogName");
        this.title = (String) map.get("title");
        this.subTitle = (String) map.get("subTitle");
        this.summary = (String) map.get("summary");
        this.coverUrl = (String) map.get("coverUrl");
        this.createTime = (Date) map.get("createTime");
        this.isabroad = (Integer) map.get("isabroad");
        this.viewnum = (Integer) map.get("viewnum");
        this.replynum = (Integer) map.get("replynum");
        this.favornum = (Integer) map.get("favornum");
        this.sharenum = (Integer) map.get("sharenum");
        this.thumbsupnum = (Integer) map.get("thumbsupnum");
        this.state = (Integer) map.get("state");

        Object content = map.get("content");
        if (content instanceof Map) {
            StrategyContent ct = new StrategyContent();
            this.content = ct;
            Map<Object, Object> m = (Map<Object, Object>) content;

            ct.setId((Long) m.get("id"));
            ct.setContent((String) m.get("content"));
        } else if (content instanceof StrategyContent) {
            this.content = (StrategyContent) content;
        }
        /*try {
            BeanUtils.copyProperties(this, map);
        } catch (Exception ignored) {
        }*/
    }
}

