package com.fansz.relations.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by dell on 2016/1/7.
 */
public class AddContactsRemarkParam extends AddFriendParam{

    @JSONField(name="remark")
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
