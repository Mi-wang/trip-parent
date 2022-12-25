package cn.wolfcode.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/23 11:39
 */
@Setter
@Getter
@TableName("strategy_content")
public class StrategyContent implements Serializable {
    private Long id;
    private String content;
}
