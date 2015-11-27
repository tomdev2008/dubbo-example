package com.fansz.members.model.fandom;


/**
 * Created by root on 15-11-20.
 */
public class FandomFollowers  {

    private String id;

    private int pageStart;

    private int count;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
