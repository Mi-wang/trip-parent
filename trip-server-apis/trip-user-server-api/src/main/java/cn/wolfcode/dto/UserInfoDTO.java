package cn.wolfcode.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author wby
 * @version 1.0
 * @date 2023-01-07 007 19:25
 */
@Getter
@Setter
public class UserInfoDTO implements Serializable {

    private Long id;
    private String nickname;  //昵称
    private String phone;  //手机
    private String email;  //邮箱
    private Integer gender; //性别
    private Integer level;  //用户级别
    private String city;  //所在城市
    private String headImgUrl; //头像
    private String info;  //个性签名
    private Integer state; //状态
}

