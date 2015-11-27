package com.fansz.members.model.param;

/**
 * Created by root on 15-11-4.
 */
public class CommentPagePara {

    private String postId;

    private int pageStart;

    private int count;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public int getPageStart() {
        return pageStart;
    }

    public void setPageStart(int pageStart) {
        this.pageStart = pageStart;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
