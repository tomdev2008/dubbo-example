package com.fansz.fandom.model.vote;

import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.common.provider.model.AbstractToken;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by dell on 2016/1/14.
 */
public class VotePostParam extends VoteResultByPostId {

    @NotBlank
    @JSONField(name="selected_option")
    private String selectedOption;

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }
}
