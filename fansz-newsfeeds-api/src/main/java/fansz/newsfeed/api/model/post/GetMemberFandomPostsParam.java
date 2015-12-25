package fansz.newsfeed.api.model.post;

import com.fansz.common.provider.model.AccessTokenAware;
import com.fansz.common.provider.model.PageParam;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by LiZhe on 12/4/2015.
 */
public class GetMemberFandomPostsParam extends PageParam implements AccessTokenAware {

    @NotNull
    @JsonProperty("fandom_id")
    private Long fandomId;

    @NotBlank
    @JsonProperty("member_sn")
    private String currentSn;

    @JsonProperty("access_token")
    private String accessToken;


    public Long getFandomId() {
        return fandomId;
    }

    public void setFandomId(Long fandomId) {
        this.fandomId = fandomId;
    }

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

}
