package cn.wolfcode.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/13 20:06
 */
@Getter
@Setter
public class BaseDomain implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
}
