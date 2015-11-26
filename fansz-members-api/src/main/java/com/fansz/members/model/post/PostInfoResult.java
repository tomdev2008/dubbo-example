package com.fansz.members.model.post;


import java.io.InputStream;
import java.util.List;
import java.util.Vector;

/**
 *
 *
 * Created by root on 15-11-3.
 */
public class PostInfoResult {

    private String title;

    private String content;

    private String type;

    private String direction;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
