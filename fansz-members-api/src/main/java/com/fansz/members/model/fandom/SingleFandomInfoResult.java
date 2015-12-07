package com.fansz.members.model.fandom;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by LiZhe on 12/4/2015.
 */
public class SingleFandomInfoResult implements Serializable {

    @JsonProperty("fandom_name")
    private String fandomName;

    @JsonProperty("fandom_avatar_url")
    private String fandomAvatarUrl;

    @JsonProperty("fandom_intro")
    private String fandomIntro;

    @JsonProperty("fandom_create_time")
    private String fandomCreateTime;

    @JsonProperty("fandom_creator")
    private Creator creator;

    public String getFandomName() {
        return fandomName;
    }

    public void setFandomName(String fandomName) {
        this.fandomName = fandomName;
    }

    public String getFandomAvatarUrl() {
        return fandomAvatarUrl;
    }

    public void setFandomAvatarUrl(String fandomAvatarUrl) {
        this.fandomAvatarUrl = fandomAvatarUrl;
    }

    public String getFandomIntro() {
        return fandomIntro;
    }

    public void setFandomIntro(String fandomIntro) {
        this.fandomIntro = fandomIntro;
    }

    public String getFandomCreateTime() {
        return fandomCreateTime;
    }

    public void setFandomCreateTime(String fandomCreateTime) {
        this.fandomCreateTime = fandomCreateTime;
    }

    public Creator getCreator() {
        return creator;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    public class Creator {

        @JsonProperty("sn")
        private String sn;

        @JsonProperty("nickname")
        private String nickname;

        @JsonProperty("member_avatar")
        private String memberAvatar;

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getMemberAvatar() {
            return memberAvatar;
        }

        public void setMemberAvatar(String memberAvatar) {
            this.memberAvatar = memberAvatar;
        }
    }

}
