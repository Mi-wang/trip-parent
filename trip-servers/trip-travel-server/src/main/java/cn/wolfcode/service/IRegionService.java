package cn.wolfcode.service;

import cn.wolfcode.domain.Region;
import cn.wolfcode.query.BaseQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/22 10:44
 */
public interface IRegionService extends IService<Region> {

    Page<Region> queryPage(BaseQuery qo);
}
