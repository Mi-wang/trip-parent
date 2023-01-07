package cn.wolfcode.controller;

import cn.wolfcode.service.ISmsService;
import cn.wolfcode.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/17 17:04
 */
@RestController
@RequestMapping("/sms")
public class SmsController extends BaseController {

    @Autowired
    private ISmsService smsService;

    /**
     * 通用发送短信接口
     *
     * @param phone 手机号
     * @param type  类型(REGISTER=注册, CHANGEPASS=修改密码)
     */
    @PostMapping("/send")
    public ResponseEntity<R<Object>> send(String phone, String type) {
        // 在 service 发送短信
        smsService.send(phone, type);
        return success("发送短信成功");
    }
}
