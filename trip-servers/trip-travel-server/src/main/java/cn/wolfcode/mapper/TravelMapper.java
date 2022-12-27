package cn.wolfcode.mapper;

import cn.wolfcode.domain.Region;
import cn.wolfcode.domain.Travel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/17 11:39
 */
public interface TravelMapper extends BaseMapper<Travel> {

    @Update("update travel set state = #{newState} where id = #{id} and state = #{oldState}")
    int updateStatus(@Param("id") Long id, @Param("newState") Integer newState, @Param("oldState") Integer oldState);
}
