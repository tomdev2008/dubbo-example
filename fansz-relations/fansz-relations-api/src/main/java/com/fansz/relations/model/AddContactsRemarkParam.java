package com.fansz.relations.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by dell on 2016/1/7.
 */
public class AddContactsRemarkParam extends AddFriendParam{

    @NotBlank
    @JSONField(name="remark")
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
