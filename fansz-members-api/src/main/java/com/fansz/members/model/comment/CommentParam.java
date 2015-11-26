package com.fansz.members.model.comment;


import java.io.Serializable;

/**
 * Created by root on 15-11-4.
 */
public class CommentParam implements Serializable{

    private static final long serialVersionUID = 7165927018098624209L;

    private String content;

    private String postId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}
