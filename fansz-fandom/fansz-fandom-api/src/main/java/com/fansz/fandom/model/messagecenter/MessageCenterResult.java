package com.fansz.fandom.model.messagecenter;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dell on 2015/12/11.
 */
public class MessageCenterResult implements Serializable{

    private static final long serialVersionUID = -7140164730823327853L;
    @JSONField(name="member_sn")
    private String memberSn;

    @JSONField(name="operation_time")
    private Date operationTime;

    @JSONField(name="post_id")
    private Long postId;

    @JSONField(name="op_type")
    private String opType;

    @JSONField(name="like_comment_content")
    private String likeCommentContent;

    @JSONField(name="nickname")
    private String nickname;
    
    @JSONField(name="member_avatar")
    private String memberAvatar;

    public String getMemberSn() {
        return memberSn;
    }

    public void setMemberSn(String memberSn) {
        this.memberSn = memberSn;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    public String getLikeCommentContent() {
        return likeCommentContent;
    }

    public void setLikeCommentContent(String likeCommentContent) {
        this.likeCommentContent = likeCommentContent;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMemberAvatar() {
        return memberAvatar;
    }

    public void setMemberAvatar(String memberAvatar) {
        this.memberAvatar = memberAvatar;
    }
}
