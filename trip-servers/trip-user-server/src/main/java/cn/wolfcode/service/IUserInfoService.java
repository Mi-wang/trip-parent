package cn.wolfcode.service;

import cn.wolfcode.domain.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/15 11:41
 */
public interface IUserInfoService extends IService<UserInfo> {
    /**
     * 基于手机号查询用户通用对象
     * @param phone
     * @return
     */
    UserInfo getByPhone(String phone);
}
