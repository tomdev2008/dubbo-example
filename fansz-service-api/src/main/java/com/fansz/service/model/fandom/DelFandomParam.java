package com.fansz.service.model.fandom;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by dell on 2015/12/16.
 * 删除Fandom传入参数
 */
public class DelFandomParam implements Serializable {

    private static final long serialVersionUID = -4699229052271883803L;

    @NotBlank
    @JsonProperty("fandom_admin_sn")
    private String adminSn;

    @NotBlank
    @JsonProperty("access_token")
    private String accessToken;

    @NotNull
    @JsonProperty("fandom_id")
    private Long id;

    public String getAdminSn() {
        return adminSn;
    }

    public void setAdminSn(String adminSn) {
        this.adminSn = adminSn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
