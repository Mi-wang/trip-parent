package cn.wolfcode.service;

import cn.wolfcode.domain.Region;
import cn.wolfcode.domain.StrategyCatalog;
import cn.wolfcode.query.BaseQuery;
import cn.wolfcode.vo.CatalogGroupVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/25 15:24
 */
public interface IStrategyCatalogService extends IService<StrategyCatalog> {

    Page<StrategyCatalog> queryPage(BaseQuery qo);

    List<CatalogGroupVO> groupList();

    List<StrategyCatalog> queryCatalogsByDestId(Long destId);
}
