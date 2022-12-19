package cn.wolfcode.controller;

import cn.wolfcode.domain.UserInfo;
import cn.wolfcode.dto.UserRegisterDTO;
import cn.wolfcode.service.IUserInfoService;
import cn.wolfcode.vo.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/17 15:15
 */
@Validated
@RestController
@RequestMapping("/users")
public class UserInfoController extends BaseController {

    @Autowired
    private IUserInfoService userInfoService;


    @PostMapping("/login")
    public ResponseEntity<AjaxResult<Map<String, Object>>> login(
            @NotEmpty(message = "用户名不能为空") String username,
            @NotEmpty(message = "密码不能为空") String password) {

        // service 登录操作
        Map<String, Object> result = userInfoService.login(username, password);

        return success(result);
    }
    @PostMapping("/phone/exists")
    public ResponseEntity<AjaxResult<Boolean>> phoneExistx(
            @NotNull(message = "手机号不能为空")
            String phone) {
        UserInfo userInfo = userInfoService.getByPhone(phone);
        return success(userInfo != null);
    };
    @PostMapping("/register")
    public ResponseEntity<AjaxResult<Object>> register(@Valid UserRegisterDTO registerDTO) {
        userInfoService.register(registerDTO);
        return success();
    };
}
