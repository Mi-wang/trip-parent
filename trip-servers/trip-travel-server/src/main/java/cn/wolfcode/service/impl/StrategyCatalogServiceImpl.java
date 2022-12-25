package cn.wolfcode.service.impl;

import cn.wolfcode.domain.Region;
import cn.wolfcode.domain.StrategyCatalog;
import cn.wolfcode.mapper.RegionMapper;
import cn.wolfcode.mapper.StrategyCatalogMapper;
import cn.wolfcode.query.BaseQuery;
import cn.wolfcode.service.IRegionService;
import cn.wolfcode.service.IStrategyCatalogService;
import cn.wolfcode.vo.CatalogGroupVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/25 15:26
 */
@Service
public class StrategyCatalogServiceImpl extends ServiceImpl<StrategyCatalogMapper, StrategyCatalog> implements IStrategyCatalogService {

    @Override
    public Page<StrategyCatalog> queryPage(BaseQuery qo) {
        // 条件构造器
        LambdaQueryWrapper<StrategyCatalog> wrapper = new LambdaQueryWrapper<StrategyCatalog>()

                // 当condition成立的时候，才会将这个条件拼接在 sql 语句上
                .like(StringUtils.hasLength(qo.getKeyword()), StrategyCatalog::getName, qo.getKeyword());
        // 分页查询方法
        return super.page(new Page<>(qo.getCurrentPage(), qo.getPageSize()), wrapper);
    }

    @Override
    public List<CatalogGroupVO> groupList() {
        // 1. 查询到所有攻略分类对象（状态为 0）
        List<StrategyCatalog> catalogList =
                list(Wrappers.<StrategyCatalog>lambdaQuery()
                        .eq(StrategyCatalog::getState, StrategyCatalog.STATE_NORMAL));

        // 2. 对攻略分类进行分组操作，并转换为 CatalogGroupVO 对象
        Map<String, List<StrategyCatalog>> groupMap = catalogList.stream()
                // 按照 destName 进行分组
                .collect(Collectors.groupingBy(StrategyCatalog::getDestName));
        // {成都: [{destName: 成都, id: 1, name: xxx}, {destName: 成都, id: 2, name: aaa}, {destName: 成都, id: 3, name: cccc}]}

        // 3. 将分组后的 map 转换为 vo list 并返回
        List<CatalogGroupVO> voList = new ArrayList<>();
        for (Map.Entry<String, List<StrategyCatalog>> entry : groupMap.entrySet()) {
            CatalogGroupVO groupVO = new CatalogGroupVO();
            // Map 的 key 就是分组目的地的名称
            groupVO.setDestName(entry.getKey());
            // Map 的 value 就是这个分组的所有分类
            groupVO.setCatalogList(entry.getValue());

            voList.add(groupVO);
        }
        return voList;
    }
}
