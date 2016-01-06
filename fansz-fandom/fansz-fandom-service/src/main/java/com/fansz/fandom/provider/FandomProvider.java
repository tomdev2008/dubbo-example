package com.fansz.fandom.provider;

import com.fansz.common.provider.AbstractProvider;
import com.fansz.common.provider.model.CommonPagedResult;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;
import com.fansz.fandom.api.FandomApi;
import com.fansz.fandom.model.fandom.*;
import com.fansz.fandom.model.profile.ContactInfoResult;
import com.fansz.fandom.model.relationship.ExitFandomParam;
import com.fansz.fandom.model.relationship.JoinFandomsParam;
import com.fansz.fandom.model.relationship.MemberFandomQueryParam;
import com.fansz.fandom.service.FandomService;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
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
        fandomQueryParam.setCurrentSn(null);
        fandomQueryParam.setFandomId(null);
        fandomQueryParam.setFandomParentId(null);
        return renderSuccess(fandomService.listFandom(fandomQueryParam));

    }

    @Override
    public CommonResult<NullResult> joinFandom(JoinFandomsParam joinFandomsParam) {
        fandomService.joinFandom(joinFandomsParam);
        return renderSuccess();
    }

    @Override
    public CommonResult<NullResult> exitFandom(ExitFandomParam exitFandomParam) {
        fandomService.exitFandom(exitFandomParam);
        return renderSuccess();
    }


    public CommonResult<FandomInfoResult> getFandom(FandomInfoParam fandomInfoParam) {
        FandomInfoResult result = this.fandomService.getFandomInfo(fandomInfoParam);
        return renderSuccess(result);
    }

    @Override
    public CommonResult<FandomInfoResult> addFandom(AddFandomParam addFandomParam) {
        FandomInfoResult fandomInfoResult = fandomService.addFandom(addFandomParam);
        return renderSuccess(fandomInfoResult);
    }

    @Override
    public CommonPagedResult<FandomInfoResult> getMyFandoms(MemberFandomQueryParam fandomParam) {
        // 获得我关注的fandom
        PageBounds pageBounds = new PageBounds(fandomParam.getPageNum(), fandomParam.getPageSize());
        PageList<FandomInfoResult> fandoms = fandomService.findFandomsByMemberSn(fandomParam.getCurrentSn(), pageBounds);
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
     * @return CommonResult<List<FandomCategory>> 返回对象
     */
    @Override
    public CommonResult<List<FandomCategory>> getFandomCategory(FandomQueryParam fandomQueryParam) {
        List<FandomCategory> result = fandomService.getFandomCategory(fandomQueryParam);
        return renderSuccess(result);
    }

    /**
     * 获取圈子fandom接口
     *
     * @param fandomQueryParam 查询参数
     * @return CommonResult<List<FandomCategory>> 返回对象
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
    public CommonResult<FandomInfoResult> addJoinFandom(AddFandomParam addFandomParam) {
        FandomInfoResult fandomInfoResult = fandomService.addFandom(addFandomParam);
        JoinFandomsParam joinFandomsParam = new JoinFandomsParam();
        joinFandomsParam.setCurrentSn(fandomInfoResult.getFandomCreatorSn());
        joinFandomsParam.setFandomIds(Arrays.asList(String.valueOf(fandomInfoResult.getId())));
        fandomService.joinFandom(joinFandomsParam);
        return renderSuccess(fandomInfoResult);
    }

    @Override
    public CommonResult<NullResult> delFandom(DelFandomParam delFandomParam) {
        fandomService.delFandom(delFandomParam);
        return renderSuccess();
    }

    @Override
    public CommonResult<FandomInfoResult> modifyFandom(ModifyFandomParam modifyFandomParam) {
        FandomInfoResult fandomInfoResult = fandomService.modifyFandom(modifyFandomParam);
        return renderSuccess(fandomInfoResult);
    }
}

