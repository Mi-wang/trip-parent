package cn.wolfcode.service;

import cn.wolfcode.domain.Region;
import cn.wolfcode.domain.Travel;
import cn.wolfcode.domain.TravelContent;
import cn.wolfcode.query.BaseQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/22 10:44
 */
public interface ITravelService extends IService<Travel> {

    Page<Travel> queryPage(BaseQuery qo);

    TravelContent getContent(Long id);

    void audit(Long id, Integer state);

}
