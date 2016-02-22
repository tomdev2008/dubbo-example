package com.fansz.fandom.service;

import com.fansz.fandom.model.fandom.*;
import com.fansz.fandom.model.profile.UserInfoResult;
import com.fansz.fandom.model.relationship.ExitFandomParam;
import com.fansz.fandom.model.relationship.JoinFandomsParam;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import java.util.List;
import java.util.Map;

/**
 * Created by root on 15-11-3.
 */
public interface FandomService {

     void init();

    PageList<FandomInfoResult> listAllFandom(FandomQueryParam fandomQueryParam);

    PageList<FandomInfoResult> listUnfocusFandoms(FandomQueryParam fandomQueryParam);

    PageList<FandomInfoResult> findFandomsByMemberSn(String sn, PageBounds pageBounds);

    boolean joinFandom(JoinFandomsParam joinFandomParam);

    boolean exitFandom(ExitFandomParam joinFandomParam);

    PageList<FandomInfoResult> getRecommendFandom(FandomQueryParam fandomQueryParam);

    List<Map<String, Object>> getFandomCategory(FandomQueryParam fandomQueryParam);

    PageList<UserInfoResult> getFandomMembers(FandomQueryParam fandomQueryParam);

    FandomInfoResult getFandomInfo(FandomInfoParam fandomInfoParam);

    PageList<FandomInfoResult> searchFandoms(SearchFandomParam searchFandomParam);

    FandomInfoResult addFandom(AddFandomParam addFandomParam);

    int delFandom(DelFandomParam delFandomParam);

    FandomInfoResult modifyFandom(ModifyFandomParam modifyFandomParam);
}
