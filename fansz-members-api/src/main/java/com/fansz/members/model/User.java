package com.fansz.members.model;

import com.fansz.appservice.resource.param.RegisterPara;
import com.fansz.appservice.utils.ShortUUIDGenerator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户实体类
 * Created by root on 15-11-3.
 */
@Data
public class User implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = -2702663652288470000L;

    // 用户id
    private String id;
    // 登陆名
    private String loginName;
    // 密码
    private String password;
    // 电话号码
    private String mobile;
    // 邮箱
    private String email;
    // 昵称
    private String nickName;
    // 性别
    private String gender;
    // 出生日期
    private Date birthday;
    // 头像
    private String avatar;
    // 会员等级
    private String level;
    // 好友关系
    private Friendship friendship;
    // 他人与我的关系
    private String relation;//Strange, Contacts,
    //注册时间
    private Date createTime;
    // 更新时间
    private Date updateTime = null;
    // 是否有效
    private boolean enabled = true;
    // 是否锁定
    private boolean locked = false;
    // 失效日期
    private Date expiredDate = null;
    // 关注的fandom
    private List<String> fandomIds;
}
