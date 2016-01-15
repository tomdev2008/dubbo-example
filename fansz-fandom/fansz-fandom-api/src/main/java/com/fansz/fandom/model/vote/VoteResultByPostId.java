package com.fansz.fandom.model.vote;

import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.common.provider.model.AbstractToken;

import javax.validation.constraints.NotNull;

/**
 * Created by dell on 2016/1/15.
 */
public class VoteResultByPostId extends AbstractToken {

    private String currentSn;

    @NotNull
    @JSONField(name="post_id")
    private Long postId;

    @Override
    public String getCurrentSn() {
        return this.currentSn;
    }

    @Override
    public void setCurrentSn(String s) {
        this.currentSn = s;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
