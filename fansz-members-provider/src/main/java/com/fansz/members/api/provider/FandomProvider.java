package com.fansz.members.api.provider;

import com.fansz.members.api.FandomApi;
import com.fansz.members.api.extension.AbstractProvider;
import com.fansz.members.api.service.FandomService;
import com.fansz.members.model.CommonPagedResult;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.NullResult;
import com.fansz.members.model.fandom.*;
import com.fansz.members.model.profile.ContactInfoResult;
import com.fansz.members.model.relationship.ExitFandomParam;
import com.fansz.members.model.relationship.JoinFandomParam;
import com.fansz.members.model.relationship.MemberFandomQueryParam;
import com.fansz.members.tools.Constants;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 圈子接口类
 */
@Component("fandomProvider")
public class FandomProvider extends AbstractProvider implements FandomApi {

    @Autowired
    private FandomService fandomService;

    @Override
    public CommonResult<List<FandomInfoResult>> listAllFandoms(FandomQueryParam fandomQueryParam) {
        fandomQueryParam.setMemberSn(null);
        fandomQueryParam.setFandomId(null);
        fandomQueryParam.setFandomParentId(null);
        return renderSuccess(fandomService.listFandom(fandomQueryParam));

    }

    @Override
    public CommonResult<NullResult> joinFandom(JoinFandomParam joinFandomParam) {
        fandomService.joinFandom(joinFandomParam);
        return renderSuccess();
    }

    @Override
    public CommonResult<NullResult> exitFandom(ExitFandomParam exitFandomParam) {
        fandomService.exitFandom(exitFandomParam);
        return renderSuccess();
    }

    @Override
    public CommonResult<NullResult> markSpecialFandom(JoinFandomParam joinFandomParam) {
        fandomService.markAsSpecial(joinFandomParam);
        return renderSuccess(PRESENCE);
    }

    @Override
    public CommonResult<NullResult> removeSpecialFandom(JoinFandomParam joinFandomParam) {
        fandomService.unmarkAsSpecial(joinFandomParam);
        return renderSuccess(PRESENCE);
    }


    public CommonResult<FandomInfoResult> getFandom(FandomInfoParam fandomInfoParam) {
        FandomInfoResult result = this.fandomService.getFandomInfo(fandomInfoParam);
        return renderSuccess(result);
    }

    @Override
    public CommonResult<FandomInfoResult> addFandom(AddFandomParam addFandomParam) {
        FandomInfoResult fandomInfoResult=fandomService.addFandom(addFandomParam);
        return renderSuccess(fandomInfoResult);
    }

    @Override
    public CommonPagedResult<FandomInfoResult> getMyFandoms(MemberFandomQueryParam fandomParam) {
        // 获得我关注的fandom
        PageBounds pageBounds = new PageBounds(fandomParam.getOffset(), fandomParam.getLimit());
        PageList<FandomInfoResult> fandoms = fandomService.findFandomsByMemberSn(fandomParam.getSn(), pageBounds);
        return renderPagedSuccess(fandoms);
    }

    /**
     * 获取圈子fandom接口
     *
     * @param fandomQueryParam 查询参数
     * @return CommonResult<List<FandomInfoResult>> 返回对象
     */
    public CommonPagedResult<FandomInfoResult> getRecommendFandom(FandomQueryParam fandomQueryParam) {
        PageList<FandomInfoResult> result = fandomService.getRecommendFandom(fandomQueryParam);
        return renderPagedSuccess(result);
    }

    /**
     * 获取fandom大小分类接口
     *
     * @param fandomQueryParam 查询参数
     * @return CommonResult<List<FandomCategorys>> 返回对象
     */
    @Override
    public CommonResult<List<FandomCategorys>> getFandomCategory(FandomQueryParam fandomQueryParam) {
        List<FandomCategorys> result = fandomService.getFandomCategory(fandomQueryParam);
        return renderSuccess(result);
    }

    /**
     * 获取圈子fandom接口
     *
     * @param fandomQueryParam 查询参数
     * @return CommonResult<List<FandomCategorys>> 返回对象
     */
    @Override
    public CommonPagedResult<ContactInfoResult> getFandomMembers(FandomQueryParam fandomQueryParam) {
        PageList<ContactInfoResult> result = fandomService.getFandomMembers(fandomQueryParam);
        return renderPagedSuccess(result);
    }

    @Override
    public CommonPagedResult<SearchFandomResult> searchFandoms(SearchFandomParam searchFandomParam) {
        PageList<SearchFandomResult> pageList = fandomService.searchFandoms(searchFandomParam);
        return super.renderPagedSuccess(pageList);
    }

    @Override
    public CommonResult<NullResult> addJoinFandom(JoinFandomParam joinFandomParam) {
        fandomService.joinFandom(joinFandomParam);
        return renderSuccess(PRESENCE);
    }
}

