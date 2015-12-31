package com.fansz.fandom.model.fandom;

import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.common.provider.model.AbstractToken;

/**
 * Created by dell on 2015/12/29.
 */
public class FandomTagParam extends AbstractToken {

    @JSONField(name="fandom_id")
    private Long fandomId;
    @JSONField(name="tag_id")
    private Long id;
    @JSONField(name="tag_name")
    private String tagName;

    public Long getFandomId() {
        return fandomId;
    }

    public void setFandomId(Long fandomId) {
        this.fandomId = fandomId;
    }

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

    @Override
    public String getCurrentSn() {
        return null;
    }

    @Override
    public void setCurrentSn(String s) {

    }
}
