package com.fansz.fandom.model.post;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * 创建投票帖传入参数
 */
public class AddVotePostParam extends AddPostParam {

    @JSONField(name = "effective_time")
    private Date effectiveTime;

    public Date getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }
}
