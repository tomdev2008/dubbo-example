package com.fansz.fandom.service;

import com.fansz.fandom.model.fandom.*;
import com.fansz.fandom.model.relationship.ExitFandomParam;
import com.fansz.fandom.model.relationship.FriendInfoResult;
import com.fansz.fandom.model.relationship.JoinFandomsParam;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import java.util.List;

/**
 * Created by root on 15-11-3.
 */
public interface FandomService {

    List<FandomInfoResult> listFandom(FandomQueryParam fandomQueryParam);


    PageList<FandomInfoResult> findFandomsByMemberSn(String sn, PageBounds pageBounds);

    boolean joinFandom(JoinFandomsParam joinFandomParam);

    boolean exitFandom(ExitFandomParam joinFandomParam);

    PageList<FandomInfoResult> getRecommendFandom(FandomQueryParam fandomQueryParam);

    List<FandomCategory> getFandomCategory(FandomQueryParam fandomQueryParam);

    PageList<FriendInfoResult> getFandomMembers(FandomQueryParam fandomQueryParam);

    FandomInfoResult getFandomInfo(FandomInfoParam fandomInfoParam);

    PageList<SearchFandomResult> searchFandoms(SearchFandomParam searchFandomParam);

    FandomInfoResult addFandom(AddFandomParam addFandomParam);

    int delFandom(DelFandomParam delFandomParam);

    FandomInfoResult modifyFandom(ModifyFandomParam modifyFandomParam);

}
