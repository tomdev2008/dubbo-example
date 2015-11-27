package com.fansz.members.model.param;

import java.util.List;
import java.util.Vector;

/**
 *
 *
 * Created by root on 15-11-3.
 */
public class PostParam {

    private String title;

    private String content;

    private String type;

    private String direction;

    private List<String> fandoms = new Vector<String>();

    private List<String> attachments = new Vector<String>();

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

    public List<String> getFandoms() {
        return fandoms;
    }

    public void setFandoms(List<String> fandoms) {
        this.fandoms = fandoms;
    }

    public List<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }
}
