package com.fansz.service.api.service;

import com.fansz.service.model.fandom.*;
import com.fansz.service.model.profile.ContactInfoResult;
import com.fansz.service.model.relationship.ExitFandomParam;
import com.fansz.service.model.relationship.JoinFandomParam;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import java.util.List;

/**
 * Created by root on 15-11-3.
 */
public interface FandomService {

    List<FandomInfoResult> listFandom(FandomQueryParam fandomQueryParam);


    PageList<FandomInfoResult> findFandomsByMemberSn(String sn, PageBounds pageBounds);

    boolean joinFandom(JoinFandomParam joinFandomParam);

    boolean exitFandom(ExitFandomParam joinFandomParam);

    boolean markAsSpecial(JoinFandomParam joinFandomParam);

    boolean unmarkAsSpecial(JoinFandomParam joinFandomParam);

    PageList<FandomInfoResult> getRecommendFandom(FandomQueryParam fandomQueryParam);

    List<FandomCategory> getFandomCategory(FandomQueryParam fandomQueryParam);

    PageList<ContactInfoResult> getFandomMembers(FandomQueryParam fandomQueryParam);

    FandomInfoResult getFandomInfo(FandomInfoParam fandomInfoParam);

    PageList<SearchFandomResult> searchFandoms(SearchFandomParam searchFandomParam);

    FandomInfoResult addFandom(AddFandomParam addFandomParam);

    int delFandom(DelFandomParam delFandomParam);

    FandomInfoResult modifyFandom(ModifyFandomParam modifyFandomParam);

}
