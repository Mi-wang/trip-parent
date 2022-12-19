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
import javax.validation.constraints.NotNull;

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
    private IUserInfoService userInfoServer;

    @PostMapping("/phone/exists")
    public ResponseEntity<AjaxResult<Boolean>> phoneExistx(
            @NotNull(message = "手机号不能为空")
            String phone) {
        UserInfo userInfo = userInfoServer.getByPhone(phone);
        return success(userInfo != null);
    };
    @PostMapping("/register")
    public ResponseEntity<AjaxResult<Object>> register(@Valid UserRegisterDTO registerDTO) {
        userInfoServer.register(registerDTO);
        return success();
    };
}
