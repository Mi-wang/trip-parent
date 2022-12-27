package cn.wolfcode.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

/**
 * @author wby
 * @version 1.0
 * @date 2022-12-27 027 11:35
 */
@Setter
@Getter
@TableName("travel_content")
public class TravelContent implements Serializable {
    private Long id;
    private String content;
}