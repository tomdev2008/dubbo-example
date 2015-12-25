package fansz.newsfeed.api.model.comment;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

/**
 * Created by allan on 15/12/8.
 */
public class CommentInfoResult {
    @JsonProperty("id")
    private long id;
    @JsonProperty("post_id")
    private int postId;
    @JsonProperty("comment_content")
    private String commentContent;
    @JsonProperty("commentator_sn")
    private String commentatorSn;
    @JsonProperty("commentator_nickname")
    private String commentatorNickname;
    @JsonProperty("comment_time")
    private Date commentTime;
    @JsonProperty("commentator_avatar")
    private String commentatorAvatar;
}
