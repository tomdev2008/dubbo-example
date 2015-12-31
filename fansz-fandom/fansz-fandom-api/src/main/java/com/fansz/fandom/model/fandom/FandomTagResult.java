package com.fansz.fandom.model.fandom;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dell on 2015/12/29.
 */
public class FandomTagResult implements Serializable {
    private static final long serialVersionUID = -3084794364186159640L;

    @JSONField(name="tag_id")
    private Long id;
    @JSONField(name="tag_name")
    private String tagName;

    @JSONField(name="fandom_id")
    private Long fandomId;

    @JSONField(name="create_time")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Long getFandomId() {
        return fandomId;
    }

    public void setFandomId(Long fandomId) {
        this.fandomId = fandomId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
