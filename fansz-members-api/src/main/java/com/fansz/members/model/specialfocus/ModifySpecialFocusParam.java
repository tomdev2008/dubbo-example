package com.fansz.members.model.specialfocus;

import com.fansz.members.model.AbstractToken;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2015/12/9.
 */
public class ModifySpecialFocusParam extends AbstractToken {

    @JsonProperty("member_sn")
    private String memberSn;

    @JsonProperty("list")
    private List<SpecialFocusParam> list;

    public String getMemberSn() {
        return memberSn;
    }

    public void setMemberSn(String memberSn) {
        this.memberSn = memberSn;
    }


    public List<SpecialFocusParam> getList() {
        return list;
    }

    public void setList(List<SpecialFocusParam> list) {
        this.list = list;
    }
}
