package com.fansz.members.model.comment;

import com.fansz.members.model.AccessTokenAware;
import com.fansz.members.model.PageParam;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by xuyubiao on 15/12/1.
 */
public class CommentFromFandomQueryParam extends PageParam implements AccessTokenAware {

    private String sn;
    @JsonProperty("access_token")
    private String accessToken;

    @NotBlank
    @JsonProperty("comment_source")
    private String commentSource;

    @JsonProperty("post_id")
    private Long postId;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }


    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getCommentSource() {
        return commentSource;
    }

    public void setCommentSource(String commentSource) {
        this.commentSource = commentSource;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
