package com.fansz.db.entity;

/**
 * Created by allan on 15/12/28.
 */

import javax.persistence.*;
import java.io.Serializable;

@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "connects_special_scrolling_bar")
public class SpecialFocus implements Serializable {

    private static final long serialVersionUID = -9091247540951563589L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "member_sn")
    private String memberSn;

    @Column(name = "special_member_sn")
    private String specialMemberSn;

    @Column(name = "special_fandom_id")
    private Long specialFandomId;

    @Column(name = "position_tag")
    private Long positionTag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMemberSn() {
        return memberSn;
    }

    public void setMemberSn(String memberSn) {
        this.memberSn = memberSn;
    }

    public String getSpecialMemberSn() {
        return specialMemberSn;
    }

    public void setSpecialMemberSn(String specialMemberSn) {
        this.specialMemberSn = specialMemberSn;
    }

    public Long getSpecialFandomId() {
        return specialFandomId;
    }

    public void setSpecialFandomId(Long specialFandomId) {
        this.specialFandomId = specialFandomId;
    }

    public Long getPositionTag() {
        return positionTag;
    }

    public void setPositionTag(Long positionTag) {
        this.positionTag = positionTag;
    }
}
