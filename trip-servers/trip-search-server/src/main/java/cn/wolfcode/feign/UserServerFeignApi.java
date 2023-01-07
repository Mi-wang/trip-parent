package cn.wolfcode.feign;

import cn.wolfcode.vo.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author wby
 * @version 1.0
 * @date 2023-01-07 007 20:56
 */
@FeignClient("trip-user-server")
public interface UserServerFeignApi {

    @GetMapping("/users/findByDest")
    R<List<LinkedHashMap<String, Object>>> findByDestName(@RequestParam String destName);
}

