package fansz.newsfeed.api.model.post;

import com.fansz.common.provider.model.AccessTokenAware;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by dell on 2015/12/4.
 */
public class RemovePostParam implements AccessTokenAware,Serializable{

    private static final long serialVersionUID = 6298573938660712413L;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("member_sn")
    private String currentSn;

    @JsonProperty("post_id")
    private Long postId;

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String getCurrentSn() {
        return currentSn;
    }

    @Override
    public void setCurrentSn(String currentSn) {
        this.currentSn = currentSn;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
