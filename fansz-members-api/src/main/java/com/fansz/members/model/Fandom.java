package com.fansz.members.model;

import com.fansz.members.model.param.FandomParam;

import java.util.List;

/**
 * Created by root on 15-11-4.
 */
public class Fandom {
    private String id;
    private String title;
    private String description;
    private String avatar;
    private String backImage;
    private String categoryId;
    private int followCount;
    private int postsCount;
    private String followed;
    private List<User> followers;
    private User owner;
    private List<Post> posts;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBackImage() {
        return backImage;
    }

    public void setBackImage(String backImage) {
        this.backImage = backImage;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public int getFollowCount() {
        return followCount;
    }

    public void setFollowCount(int followCount) {
        this.followCount = followCount;
    }

    public int getPostsCount() {
        return postsCount;
    }

    public void setPostsCount(int postsCount) {
        this.postsCount = postsCount;
    }

    public String getFollowed() {
        return followed;
    }

    public void setFollowed(String followed) {
        this.followed = followed;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Fandom(FandomParam fandomParam) {
        this.id = fandomParam.getUserId();
        this.title = fandomParam.getTitle();
        this.avatar = fandomParam.getAvatar();
        this.categoryId = fandomParam.getCategoryId();
        this.description = fandomParam.getDescription();
        this.backImage = fandomParam.getBackImage();
    }

    public Fandom() {

    }
}
