package com.fansz.members.model.post;

/**
 * Created by allan on 15/11/27.
 */
public class GetPostsParam {
    private String kind;

    private Long id;


    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
