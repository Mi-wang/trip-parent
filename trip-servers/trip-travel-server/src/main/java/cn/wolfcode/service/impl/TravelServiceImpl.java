package cn.wolfcode.service.impl;

import cn.wolfcode.domain.Travel;
import cn.wolfcode.domain.TravelContent;
import cn.wolfcode.domain.UserInfo;
import cn.wolfcode.feign.UserInfoFeignApi;
import cn.wolfcode.mapper.TravelContentMapper;
import cn.wolfcode.mapper.TravelMapper;
import cn.wolfcode.query.TravelQuery;
import cn.wolfcode.query.TravelRange;
import cn.wolfcode.service.ITravelService;
import cn.wolfcode.utils.AssertUtils;
import cn.wolfcode.vo.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
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
    private UserInfoFeignApi userInfoFeignApi;
    @Resource
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

        // 分页方法
        Page<Travel> page = super.page(new Page<>(qo.getCurrentPage(), qo.getPageSize()), wrapper);
        // 查询用户信息
        List<Travel> records = page.getRecords();
        for (Travel travel : records) {
            // 基于用户 id 查询用户对象
            R<UserInfo> result = userInfoFeignApi.getById(travel.getAuthorId());
            if (!result.hasError()) {
                UserInfo userInfo = result.data(UserInfo.class);
                travel.setAuthor(userInfo);
            }
        }
        return page;
    }

    @Override
    public Travel getById(Serializable id) {
        Travel travel = super.getById(id);
        // 查询游记内容
        travel.setContent(travelContentMapper.selectById(id));

        // 远程调用查询作者信息
        R<UserInfo> result = userInfoFeignApi.getById(travel.getAuthorId());
        if (!result.hasError()) {
            // 查询作者信息
            travel.setAuthor(result.data(UserInfo.class));
        }

        return travel;
    }

    @Override
    public TravelContent getContent(Long id) {
        return travelContentMapper.selectById(id);
    }

    @Override
    public void audit(Long id, Integer state) {
        // 1. 基于 id 获取游记对象
        // 2. 判断前置状态是否为待审核, 只有待审核才进行审核
        int ret = getBaseMapper().updateStatus(id, state, Travel.STATE_WAITING);
        AssertUtils.isTrue(ret > 0, "状态修改失败");
    }

    @Override
    public List<Travel> viewnnumTop3() {
        return list(new LambdaQueryWrapper<Travel>().orderByDesc(Travel::getViewnum).last("limit 3"));
    }

    @Override
    public List<Travel> findByDestId(Long destId) {
        LambdaQueryWrapper<Travel> wrapper = new LambdaQueryWrapper<Travel>()
                .eq(Travel::getDestId, destId)
                .orderByDesc(Travel::getViewnum);

        return list(wrapper);
    }
}
