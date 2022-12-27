package cn.wolfcode.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


/**
 * @author wby
 * @version 1.0
 * @date 2022-12-27 027 9:39
 */
@Setter
@Getter
@TableName("strategy_condition")
public class StrategyCondition extends BaseDomain {

    public static final int TYPE_ABROAD = 1;  //国外
    public static final int TYPE_CHINA = 2;   //国内
    public static final int TYPE_THEME = 3;     //热门

    private String name;
    private Integer count; //个数
    private Long refid; //关联id
    private int type; //条件类型
    private Date statisTime; //归档统计时间
}
