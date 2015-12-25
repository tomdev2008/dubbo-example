package fansz.newsfeed.api.model.post;

import com.fansz.service.model.profile.UserInfoResult;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dell on 2015/12/5.
 */
public class SearchPostResult implements Serializable{

    private static final long serialVersionUID = -7786572952854806697L;
    @JsonProperty("post_id")
    private Long postId;

    @JsonProperty("post_title")
    private String postTitle;

    @JsonProperty("post_content")
    private String postContent;

    @JsonProperty("post_newsfeeds")
    private String postNewsfeeds;

    @JsonProperty("post_time")
    private Date postTime;

    @JsonProperty("post_member")
    private UserInfoResult postMember;

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
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

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public UserInfoResult getPostMember() {
        return postMember;
    }

    public void setPostMember(UserInfoResult postMember) {
        this.postMember = postMember;
    }
}
