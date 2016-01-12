package com.fansz.push.server.model;

import java.io.Serializable;

/**
 * 注册接口
 */
public class RegisterParam implements Serializable {

    private static final long serialVersionUID = -808033496631439111L;
    /**
     * 用户标识符
     */
    private String sn;

    /**
     * 广告id(Advertising Identifier), 在同一个设备上的所有App都会取到相同的值，是苹果专门给各广告提供商用来追踪用户而设的， 用户可以在 设置|隐私|广告追踪
     * 里重置此id的值，或限制此id的使用，故此id有可能会取不到值， 但好在Apple默认是允许追踪的，而且一般用户都不知道有这么个设置，所以基本上用来监测推广效果
     */
    private String idfa;


    private String appKey;

    private String platform;

    private String osVersion;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getIdfa() {
        return idfa;
    }

    public void setIdfa(String idfa) {
        this.idfa = idfa;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
