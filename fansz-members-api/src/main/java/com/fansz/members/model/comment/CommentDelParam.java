package com.fansz.members.model.comment;

import java.io.Serializable;

/**
 * Created by allan on 15/11/26.
 */
public class CommentDelParam implements Serializable{

    private static final long serialVersionUID = 7097767369484809045L;
    private Long commentId;

    private String accessKey;

    private String uid;

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
