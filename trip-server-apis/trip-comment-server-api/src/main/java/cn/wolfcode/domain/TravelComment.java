package cn.wolfcode.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;
import java.util.Date;


/**
 * @author wby
 * @version 1.0
 * @date 2023-01-02 002 9:48
 */
@Setter
@Getter
@Document("travel_comment")
public class TravelComment implements Serializable {

    public static final int TRAVLE_COMMENT_TYPE_COMMENT = 0; //普通评论
    public static final int TRAVLE_COMMENT_TYPE = 1; //评论的评论

    @Id
    private String id;  //id
    private Long travelId;  //游记id
    private String travelTitle; //游记标题
    private Long userId;    //用户id
    private String nickname; //用户名
    private String city;
    private int level;
    private String headImgUrl;   // 用户头像
    private int type = TRAVLE_COMMENT_TYPE_COMMENT; //评论类别
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime; //创建时间
    private String content;  //评论内容
    private TravelComment refComment;  //关联的评论
}

