package cn.wolfcode.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Setter;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/22 10:29
 */
@Setter
@Getter
@TableName("region")
public class Region extends BaseDomain {
    public static final int STATE_HOT = 1;
    public static final int STATE_NORMAL = 0;


    //地区名
    private String name;
    //地区编码
    private String sn;

    //关联的id， 多个以，隔开
    private String refIds;

    //是否为热点
    private Integer ishot = STATE_NORMAL;
    //序号
    private Integer seq;
    //简介
    private String info;


    public List<Long> parseRefIds(){
        List<Long> ids = new ArrayList<>();
        if(StringUtils.hasLength(refIds)){
            String[] split = refIds.split(",");
            if(split.length > 0){
                for (int i = 0;i <split.length; i++) {
                    ids.add(Long.parseLong(split[i]));
                }
            }
        }
        return ids;
    }
}
