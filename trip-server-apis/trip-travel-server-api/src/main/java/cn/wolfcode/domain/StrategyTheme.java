package cn.wolfcode.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;


/**
 * @author wby
 * @version 1.0
 * @date 2022/12/23 11:37
 */
@Setter
@Getter
@TableName("strategy_theme")
public class StrategyTheme extends BaseDomain {
    //正常
    public static final int STATE_NORMAL = 0;
    //禁用
    public static final int STATE_DISABLE = 1;

    @TableId(type= IdType.AUTO)
    private Long id;

    //主题名称
    private String name;
    //主题状态
    private int state = STATE_NORMAL;

    //序号
    private int seq;
}