package cn.wolfcode.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wby
 * @version 1.0
 * @date 2023-01-02 002 15:30
 */
@Getter
@Setter
public class ArticleStatVo {

    public static final String VIEW_NUM = "viewnum";
    public static final String REPLAY_NUM = "replynum";
    public static final String FAVOR_NUM = "favornum";
    public static final String SHARE_NUM = "sharenum";
    public static final String THUMBS_NUM = "thumbsupnum";

    private Long articleId;
    private int viewnum;
    private int replynum;
    private int favornum;
    private int sharenum;
    private int thumbsupnum;
}
