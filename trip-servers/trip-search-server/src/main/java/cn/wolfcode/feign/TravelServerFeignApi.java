package cn.wolfcode.feign;

import cn.wolfcode.dto.DestinationDTO;
import cn.wolfcode.vo.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author wby
 * @version 1.0
 * @date 2023-01-07 007 20:56
 */
@FeignClient("trip-travel-server")
public interface TravelServerFeignApi {

    @GetMapping("/destinations/getByName")
    R<DestinationDTO> getDestByName(@RequestParam String name);

    @GetMapping("/destinations/list/{page}/{limit}")
    R<List<LinkedHashMap<String, Object>>> listDestLimit(@PathVariable int page, @PathVariable int limit);

    @GetMapping("/strategies/findByDestId")
    R<List<LinkedHashMap<String, Object>>> findStrategyByDestId(@RequestParam Long destId);

    @GetMapping("/strategies/list/{page}/{limit}")
    R<List<LinkedHashMap<String, Object>>> listStrategyLimit(@PathVariable int page, @PathVariable int limit);

    @GetMapping("/travels/findByDestId")
    R<List<LinkedHashMap<String, Object>>> findTravelByDestId(@RequestParam Long destId);

    @GetMapping("/travels/list/{page}/{limit}")
    R<List<LinkedHashMap<String, Object>>> listTravelLimit(@PathVariable int page, @PathVariable int limit);
}

