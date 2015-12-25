package fansz.newsfeed.api.model.post;

import com.fansz.common.provider.model.AccessTokenAware;
import com.fansz.common.provider.model.PageParam;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by LiZhe on 12/5/2015.
 */
public class PostsQueryParam extends PageParam implements AccessTokenAware {

    @JsonProperty("member_sn")
    private String currentSn;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("fandom_id")
    private Long fandomId;

    @JsonProperty("type")
    private String type;


    @Override
    public String getCurrentSn() {
        return currentSn;
    }

    @Override
    public void setCurrentSn(String currentSn) {
        this.currentSn = currentSn;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getFandomId() {
        return fandomId;
    }

    public void setFandomId(Long fandomId) {
        this.fandomId = fandomId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
