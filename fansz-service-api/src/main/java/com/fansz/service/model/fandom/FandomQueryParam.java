package com.fansz.service.model.fandom;

import com.fansz.common.provider.model.AccessTokenAware;
import com.fansz.common.provider.model.PageParam;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by allan on 15/11/26.
 */
public class FandomQueryParam extends PageParam implements AccessTokenAware {

    private static final long serialVersionUID = 6163860431759993812L;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("member_sn")
    private String currentSn;

    @JsonProperty("fandom_id")
    private String fandomId;

    @JsonProperty("fandom_parent_id")
    private String fandomParentId;

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

    public String getFandomId() {
        return fandomId;
    }

    public void setFandomId(String fandomId) {
        this.fandomId = fandomId;
    }

    public String getFandomParentId() {
        return fandomParentId;
    }

    public void setFandomParentId(String fandomParentId) {
        this.fandomParentId = fandomParentId;
    }
}
