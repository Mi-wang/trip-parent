package cn.wolfcode.service.impl;

import cn.wolfcode.domain.Region;
import cn.wolfcode.domain.Travel;
import cn.wolfcode.domain.TravelContent;
import cn.wolfcode.domain.UserInfo;
import cn.wolfcode.fegin.UserInfoFeginApi;
import cn.wolfcode.mapper.RegionMapper;
import cn.wolfcode.mapper.TravelContentMapper;
import cn.wolfcode.mapper.TravelMapper;
import cn.wolfcode.query.BaseQuery;
import cn.wolfcode.query.TravelQuery;
import cn.wolfcode.query.TravelRange;
import cn.wolfcode.service.ITravelService;
import cn.wolfcode.utils.AssertUtils;
import cn.wolfcode.vo.AjaxResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @author wby
 * @version 1.0
 * @date 2022-12-27 027 11:40
 */
@Service
public class TravelServiceImpl extends ServiceImpl<TravelMapper, Travel> implements ITravelService {

    @Autowired
    private UserInfoFeginApi userInfoFeginApi;

    @Autowired
    private TravelContentMapper travelContentMapper;

    @Override
    public Page<Travel> queryPage(TravelQuery qo) {
        // 条件构造器
        QueryWrapper<Travel> wrapper = new QueryWrapper<Travel>()
                // 目的地条件过滤
                .eq(qo.getDestId() != null, "dest_id", qo.getDestId())
                .like(StringUtils.hasLength(qo.getKeyword()), "title", qo.getKeyword());

        TravelRange travelTimeRange = qo.getTravelTimeType();
        if (travelTimeRange != null) {
            // 拼接出行时间条件
            // DATE_FORMAT(travel_time, '%m') between min and max
            wrapper.between("month(travel_time)", travelTimeRange.getMin(), travelTimeRange.getMax());
        }
        TravelRange avgConsumeRange = qo.getConsumeType();
        if (avgConsumeRange != null) {
            // 拼接平均消费条件
            wrapper.ge(avgConsumeRange.getMin() != null, "avg_consume", avgConsumeRange.getMin())
                    .le(avgConsumeRange.getMax() != null, "avg_consume", avgConsumeRange.getMax());
        }
        TravelRange dayRange = qo.getDayType();
        if (dayRange != null) {
            // 拼接出行天数条件
            wrapper.ge(dayRange.getMin() != null, "day", dayRange.getMin())
                    .le(dayRange.getMax() != null, "day", dayRange.getMax());
        }
        // 排序列
        wrapper.orderByDesc(qo.getOrderBy());

        wrapper.orderByDesc(qo.getOrderBy());
        // 分页查询方法
        Page<Travel> page = page(new Page<>(qo.getCurrentPage(), qo.getPageSize()), wrapper);
        List<Travel> records = page.getRecords();
        for (Travel travel : records) {
            AjaxResult<UserInfo> result = userInfoFeginApi.getById(travel.getAuthorId());
            if (!result.hasError()) {
                UserInfo userInfo = result.getData(UserInfo.class);
                travel.setAuthor(userInfo);
            }
        }
        return page;
    }

    @Override
    public TravelContent getContent(Long id) {
        return travelContentMapper.selectById(id);
    }

    @Override
    public void audit(Long id, Integer state) {
        int ret = getBaseMapper().updateStatus(id, state, Travel.STATE_WAITING);
        AssertUtils.isTrue(ret > 0, "状态修改失败");
    }

    @Override
    public List<Travel> viewnnumTop3() {
        return list(new LambdaQueryWrapper<Travel>().orderByDesc(Travel::getViewnum).last("limit 3"));
    }

    @Override
    public Travel getById(Serializable id) {
        Travel travel = super.getById(id);
        travel.setContent(travelContentMapper.selectById(travel.getAuthorId()));

        // 远程调用查询作者信息
        AjaxResult<UserInfo> result = userInfoFeginApi.getById(travel.getAuthorId());
        if (!result.hasError()) {
            // 查询作者信息
            travel.setAuthor(result.getData(UserInfo.class));
        }
        return travel;
    }
}
