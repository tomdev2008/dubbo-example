package com.fansz.members.model.post;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by LiZhe on 12/4/2015.
 */
public class FandomPostInfoResult implements Serializable {

    @JsonProperty("post_id")
    private long postId;

    @JsonProperty("post_title")
    private String postTitle;

    @JsonProperty("post_content")
    private String postContent;

    @JsonProperty("post_newsfeeds")
    private String postNewsfeeds;

    @JsonProperty("post_time")
    private String postTime;

    @JsonProperty("post_member")
    private PostMember postMember;

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
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

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public PostMember getPostMember() {
        return postMember;
    }

    public void setPostMember(PostMember postMember) {
        this.postMember = postMember;
    }

    public class PostMember {

        @JsonProperty("sn")
        private String sn;

        @JsonProperty("nickname")
        private String nickname;

        @JsonProperty("member_avatar")
        private String memberAvatar;

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getMemberAvatar() {
            return memberAvatar;
        }

        public void setMemberAvatar(String memberAvatar) {
            this.memberAvatar = memberAvatar;
        }
    }

}
