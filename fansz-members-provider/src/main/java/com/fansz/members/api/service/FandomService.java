package com.fansz.members.api.service;

import com.fansz.members.api.entity.FandomPostEntity;
import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.model.fandom.*;

import com.fansz.members.model.post.GetPostsParam;
import com.fansz.members.model.relationship.ExitFandomParam;
import com.fansz.members.model.relationship.JoinFandomParam;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.fansz.members.model.profile.ContactInfoResult;

import java.util.List;

/**
 * Created by root on 15-11-3.
 */
public interface FandomService {

    List<FandomInfoResult> listFandom(FandomQueryParam fandomQueryParam);


    List<FandomInfoResult> findFandomsByMemberSn(String sn, PageBounds pageBounds);

    boolean joinFandom(JoinFandomParam joinFandomParam);

    boolean exitFandom(ExitFandomParam joinFandomParam);

    boolean markAsSpecial(JoinFandomParam joinFandomParam);

    boolean unmarkAsSpecial(JoinFandomParam joinFandomParam);

    List<FandomInfoResult> getRecommendFandom(FandomQueryParam fandomQueryParam);


    List<FandomCategorys> getFandomCategory(FandomQueryParam fandomQueryParam);

    List<ContactInfoResult> getFandomMembers(FandomQueryParam fandomQueryParam);
}
