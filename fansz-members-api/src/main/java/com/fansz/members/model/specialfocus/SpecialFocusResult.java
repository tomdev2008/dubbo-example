package com.fansz.members.model.specialfocus;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by dell on 2015/12/9.
 */
public class SpecialFocusResult implements Serializable{

    private static final long serialVersionUID = 5119301795049966905L;

    @JsonProperty("special_id")
    private Long id;

    @JsonProperty("special_fandom")
    private SpecialFandomResult specialFandomResult;

    @JsonProperty("special_member")
    private SpecialMemberResult specialMemberResult;

    public SpecialFandomResult getSpecialFandomResult() {
        return specialFandomResult;
    }

    public void setSpecialFandomResult(SpecialFandomResult specialFandomResult) {
        this.specialFandomResult = specialFandomResult;
    }

    public SpecialMemberResult getSpecialMemberResult() {
        return specialMemberResult;
    }

    public void setSpecialMemberResult(SpecialMemberResult specialMemberResult) {
        this.specialMemberResult = specialMemberResult;
    }
}
