package cn.wolfcode.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/15 11:34
 */
@Getter
@Setter
@TableName("userinfo")
public class UserInfo extends BaseDomain {
    public static final int GENDER_SECRET = 0; //保密
    public static final int GENDER_MALE = 1;   //男
    public static final int GENDER_FEMALE = 2;  //女
    public static final int STATE_NORMAL = 0;  //正常
    public static final int STATE_DISABLE = 1;  //冻结

    private String nickname;  //昵称
    private String phone;  //手机
    private String email;  //邮箱


    // json 序列化， 忽略这个属性
    // RequestBody 将 json 序列化为 Java 对象 / @ResponseBody 将 java 对象序列化为 json 字符串
    @JsonIgnore
    private String password; //密码
    private Integer gender = GENDER_SECRET; //性别
    private Integer level = 0;  //用户级别
    private String city;  //所在城市
    private String headImgUrl; //头像
    private String info;  //个性签名
    private Integer state = STATE_NORMAL; //状态

}
