package cn.wolfcode.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wby
 * @version 1.0
 * @date 2023-01-02 002 9:46
 */
@Setter
@Getter
@Document("strategy_comment")
@ToString
public class StrategyComment implements Serializable {

    @Id
    private String id;
    private Long strategyId;  //攻略(明细)id
    private String strategyTitle; //攻略标题
    private Long userId;    //用户id
    private String nickname;  //用户名
    private String city;
    private int level;
    private String headImgUrl;     //头像
    private Date createTime;    //创建时间
    private String content;      //评论内容
    private int thumbupnum;     //点赞数
    private List<Long> thumbuplist = new ArrayList<>();
}
