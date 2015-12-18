package com.fansz.members.model.messagecenter;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dell on 2015/12/11.
 */
public class MessageCenterResult implements Serializable{

    private static final long serialVersionUID = -7140164730823327853L;
    @JsonProperty("member_sn")
    private String memberSn;

    @JsonProperty("operation_time")
    private Date operationTime;

    @JsonProperty("post_id")
    private Long postId;

    @JsonProperty("op_type")
    private String opType;

    @JsonProperty("like_comment_content")
    private String likeCommentContent;

    @JsonProperty("nickname")
    private String nickname;
    
    @JsonProperty("member_avatar")
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
