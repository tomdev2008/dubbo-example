package com.fansz.fandom.model.relationship;

import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.common.provider.model.PageParam;

import java.io.Serializable;

/**
 * 获取用户关注的fandom请求参数模型
 */
public class MemberFandomQueryParam extends PageParam implements Serializable {

    private static final long serialVersionUID = 394668565041221041L;

    @JSONField(name="access_token")
    private String accessToken;

    private String sn;


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}