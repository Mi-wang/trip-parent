package cn.wolfcode.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 专用于用户注册场景的对象
 *
 * @author wby
 * @version 1.0
 * @date 2022/12/19 20:05
 */
@Setter
@Getter
public class UserRegisterDTO implements Serializable {

    @NotEmpty(message = "手机号不能为空")
    private String phone;
    @NotEmpty(message = "昵称不能为空")
    private String nickname;
    @NotEmpty(message = "密码不能为空")
    private String password;
    @NotEmpty(message = "验证码不能为空")
    private String code;
}
