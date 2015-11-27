package com.fansz.members.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户实体类
 * Created by root on 15-11-3.
 */
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Friendship getFriendship() {
        return friendship;
    }

    public void setFriendship(Friendship friendship) {
        this.friendship = friendship;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public List<String> getFandomIds() {
        return fandomIds;
    }

    public void setFandomIds(List<String> fandomIds) {
        this.fandomIds = fandomIds;
    }
}
