package cn.wolfcode.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wby
 * @version 1.0
 * @date 2023-01-06 006 18:48
 */
@Setter
@Getter
@TableName("banner")
public class Banner extends BaseDomain {

    public static final int STATE_NORMAL = 0;   // 正常
    public static final int STATE_DISABLE = 1;  // 禁用

    public static final int TYPE_TRAVEL = 1;  // 游记
    public static final int TYPE_STRATEGY = 2;  // 攻略

    private Long refid;  // 关联 id

    private String title;  // 标题

    private String subtitle; // 副标题

    private String coverUrl; // 封面

    private Integer state = STATE_NORMAL; // 状态

    private Integer seq; // 排序

    private Integer type;

}

