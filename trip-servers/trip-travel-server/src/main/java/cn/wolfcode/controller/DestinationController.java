package cn.wolfcode.controller;

import cn.wolfcode.domain.Destination;
import cn.wolfcode.domain.StrategyCatalog;
import cn.wolfcode.query.DestinationQuery;
import cn.wolfcode.service.IDestinationService;
import cn.wolfcode.service.IStrategyCatalogService;
import cn.wolfcode.vo.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/22 20:43
 */
@RestController
@RequestMapping("/destinations")
public class DestinationController {

    @Autowired
    private IDestinationService destinationService;

    @Autowired
    private IStrategyCatalogService strategyCatalogService;

    @GetMapping
    public R<?> list() {
        return R.success(destinationService.list());
    }

    @GetMapping("/query")
    public R<?> query(DestinationQuery qo) {
        Page<Destination> page = destinationService.queryPage(qo);
        return R.success(page);
    }

    @GetMapping("/toasts")
    public R<?> queryToasts(Long destId) {
        List<Destination> list = destinationService.queryToasts(destId);
        return R.success(list);
    }

    @GetMapping("/catalogs")
    public R<?> catalogs (Long destId) {
        List<StrategyCatalog> list = strategyCatalogService.queryCatalogsByDestId(destId);
        return R.success(list);
    }
}

