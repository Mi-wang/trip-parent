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

    private static final String VIEW_NUM = "viewnum";
    private static final String REPLY_NUM = "replynum";
    private static final String FAVOR_NUM = "favornum";
    private static final String SHARE_NUM = "sharenum";
    private static final String THUMBS_NUM = "thumbsupnum";

    private Long articleId;
    private int viewnum;
    private int replynum;
    private int favornum;
    private int sharenum;
    private int thumbsupnum;
}
