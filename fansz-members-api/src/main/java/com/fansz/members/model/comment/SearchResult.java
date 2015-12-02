package com.fansz.members.model.comment;

import com.fansz.members.model.post.PostInfoResult;
import com.fansz.members.model.post.PostLikeInfoResult;
import com.fansz.members.model.profile.UserInfoResult;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Created by dell on 2015/12/2.
 */
public class SearchResult {

    @JsonProperty("member_list")
    private List<UserInfoResult> memberList;
    @JsonProperty("post_list")
    private List<PostInfoResult> postList;

    public List<UserInfoResult> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<UserInfoResult> memberList) {
        this.memberList = memberList;
    }

    public List<PostInfoResult> getPostList() {
        return postList;
    }

    public void setPostList(List<PostInfoResult> postList) {
        this.postList = postList;
    }
}
