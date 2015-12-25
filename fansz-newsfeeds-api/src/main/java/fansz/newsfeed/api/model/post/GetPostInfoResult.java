package fansz.newsfeed.api.model.post;

import com.fansz.service.model.profile.UserInfoResult;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

/**
 * Created by dell on 2015/12/7.
 */
public class GetPostInfoResult {
    @JsonProperty("post_id")
    private Long postId;
    @JsonProperty("post_content")
    private String postContent;
    @JsonProperty("post_newsfeeds")
    private String postNewsfeeds;
    @JsonProperty("post_title")
    private String postTitle;
    @JsonProperty("like_count")
    private int likes;
    @JsonProperty("liked")
    private int liked;
    @JsonProperty("comments_count")
    private int comments;
    @JsonProperty("post_time")
    private Date postTime;
    @JsonProperty("fandom_info")
    private GetPostFandomInfo fandomInfo;
    @JsonProperty("post_member")
    private UserInfoResult postMember;

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getPostNewsfeeds() {
        return postNewsfeeds;
    }

    public void setPostNewsfeeds(String postNewsfeeds) {
        this.postNewsfeeds = postNewsfeeds;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public GetPostFandomInfo getFandomInfo() {
        return fandomInfo;
    }

    public void setFandomInfo(GetPostFandomInfo fandomInfo) {
        this.fandomInfo = fandomInfo;
    }

    public UserInfoResult getPostMember() {
        return postMember;
    }

    public void setPostMember(UserInfoResult postMember) {
        this.postMember = postMember;
    }
}
