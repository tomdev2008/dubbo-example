package com.fansz.fandom.model.vote;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dell on 2016/1/14.
 */
public class VotePostResult implements Serializable{

    private static final long serialVersionUID = -2193281249390919210L;

    @JSONField(name="post_id")
    private Long postId;

    @JSONField(name="voter_sn")
    private String voterSn;

    @JSONField(name="vote_time")
    private Date voteTime;

    @JSONField(name="selected_option")
    private String selectedOption;

    @JSONField(name="option_A_count")
    private Integer optionACount;

    @JSONField(name="option_B_count")
    private Integer optionBCount;

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getVoterSn() {
        return voterSn;
    }

    public void setVoterSn(String voterSn) {
        this.voterSn = voterSn;
    }

    public Date getVoteTime() {
        return voteTime;
    }

    public void setVoteTime(Date voteTime) {
        this.voteTime = voteTime;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }

    public Integer getOptionACount() {
        return optionACount;
    }

    public void setOptionACount(Integer optionACount) {
        this.optionACount = optionACount;
    }

    public Integer getOptionBCount() {
        return optionBCount;
    }

    public void setOptionBCount(Integer optionBCount) {
        this.optionBCount = optionBCount;
    }
}
