package fansz.newsfeed.api.model.post;

import com.fansz.common.provider.model.AccessTokenAware;
import com.fansz.common.provider.model.PageParam;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * 用户点赞/查询单个POST详细信息,传入参数模型
 */
public class PostParam extends PageParam implements AccessTokenAware, Serializable {

    private static final long serialVersionUID = -1772542305173463716L;

    @JsonProperty("post_id")
    private Long postId;

    @NotBlank
    @JsonProperty("member_sn")
    private String currentSn;

    @JsonProperty("access_token")
    private String accessToken;

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    @Override
    public String getCurrentSn() {
        return currentSn;
    }

    @Override
    public void setCurrentSn(String currentSn) {
        this.currentSn = currentSn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
