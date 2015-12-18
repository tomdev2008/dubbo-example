package com.fansz.members.model.post;

import com.fansz.members.model.PageParam;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 用户点赞/查询单个POST详细信息,传入参数模型
 */
public class PostParam extends PageParam implements Serializable {

    private static final long serialVersionUID = -1772542305173463716L;

    @JsonProperty("post_id")
    private Long postId;

    @NotBlank
    @JsonProperty("member_sn")
    private String memberSn;

    @JsonProperty("access_token")
    private String accessToken;

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getMemberSn() {
        return memberSn;
    }

    public void setMemberSn(String memberSn) {
        this.memberSn = memberSn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
