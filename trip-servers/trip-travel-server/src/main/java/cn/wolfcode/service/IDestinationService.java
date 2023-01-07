package cn.wolfcode.service;

import cn.wolfcode.domain.Destination;
import cn.wolfcode.query.BaseQuery;
import cn.wolfcode.query.DestinationQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/22 20:33
 */
public interface IDestinationService extends IService<Destination> {

    Page<Destination> queryPage(DestinationQuery qo);

    /**
     * 查询吐司
     */
    List<Destination> queryToasts(Long destId);

    /**
     * 基于热门 区域查询目的地信息
     */
    List<Destination> queryByRegionId(Long rid);

    /**
     * 基于名称查询目的地对象
     *
     * @param name 要查询的名称
     * @return 查询到的目的地
     */
    Destination findByName(String name);
}
