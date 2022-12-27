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
import cn.wolfcode.service.ITravelService;
import cn.wolfcode.utils.AssertUtils;
import cn.wolfcode.vo.AjaxResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    public Page<Travel> queryPage(BaseQuery qo) {
        // 条件构造器
        LambdaQueryWrapper<Travel> wrapper = new LambdaQueryWrapper<Travel>()

                .like(StringUtils.hasLength(qo.getKeyword()), Travel::getTitle, qo.getKeyword());
        // 分页查询方法
        Page<Travel> page = page(new Page<Travel>(qo.getCurrentPage(), qo.getPageSize()), wrapper);
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
        int ret = getBaseMapper().updateStatus(id,state,Travel.STATE_WAITING);
        AssertUtils.isTrue(ret > 0, "状态修改失败");
    }
}
