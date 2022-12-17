package cn.wolfcode.service.impl;

import cn.wolfcode.domain.UserInfo;
import cn.wolfcode.mapper.UserInfoMapper;
import cn.wolfcode.service.IUserInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/15 11:42
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {
    @Override
    public UserInfo getByPhone(String phone) {
        return getOne(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getPhone, phone));
    }
}
