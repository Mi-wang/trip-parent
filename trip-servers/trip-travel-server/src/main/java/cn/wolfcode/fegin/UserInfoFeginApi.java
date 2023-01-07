package cn.wolfcode.fegin;

import cn.wolfcode.domain.UserInfo;
import cn.wolfcode.vo.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wby
 * @version 1.0
 * @date 2022-12-27 027 11:50
 */
@FeignClient("trip-user-server")
@RequestMapping("/users")
public interface UserInfoFeginApi {

    @GetMapping("/{id}")
    R<UserInfo> getById(@PathVariable Long id);
}
