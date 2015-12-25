package fansz.newsfeed.api.model.post;

import com.fansz.service.model.profile.UserInfoResult;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 *
 */
public class PostLikeInfoResult implements Serializable {

    private static final long serialVersionUID = -8769041146051550921L;

    @JsonProperty("like_id")
    private Long likeId;

    @JsonProperty("like_time")
    private Date likeTime;

    @JsonProperty("like_member")
    private UserInfoResult likeMember;


    public Long getLikeId() {
        return likeId;
    }

    public void setLikeId(Long likeId) {
        this.likeId = likeId;
    }

    public Date getLikeTime() {
        return likeTime;
    }

    public void setLikeTime(Date likeTime) {
        this.likeTime = likeTime;
    }

    public UserInfoResult getLikeMember() {
        return likeMember;
    }

    public void setLikeMember(UserInfoResult likeMember) {
        this.likeMember = likeMember;
    }
}
