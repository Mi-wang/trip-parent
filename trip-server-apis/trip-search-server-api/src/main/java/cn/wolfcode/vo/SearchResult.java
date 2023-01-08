package cn.wolfcode.vo;

import cn.wolfcode.domain.Strategy;
import cn.wolfcode.domain.Travel;
import cn.wolfcode.domain.UserInfo;
import cn.wolfcode.dto.StrategyDTO;
import cn.wolfcode.dto.TravelDTO;
import cn.wolfcode.dto.UserInfoDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wby
 * @version 1.0
 * @date 2023-01-06 006 20:43
 */
@Getter
@Setter
public class SearchResult {

    private Long total = 0L;
    private List<StrategyDTO> strategies = new ArrayList<>();
    private List<TravelDTO> travels = new ArrayList<>();
    private List<UserInfoDTO> users = new ArrayList<>();

}
