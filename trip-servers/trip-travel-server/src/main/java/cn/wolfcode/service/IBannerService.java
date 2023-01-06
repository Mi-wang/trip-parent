package cn.wolfcode.service;

import cn.wolfcode.domain.Banner;
import cn.wolfcode.query.BaseQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author wby
 * @version 1.0
 * @date 2023-01-06 006 18:49
 */
public interface IBannerService extends IService<Banner> {

    Page<Banner> queryPage(BaseQuery qo);
}