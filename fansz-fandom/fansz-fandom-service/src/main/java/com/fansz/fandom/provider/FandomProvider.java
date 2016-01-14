package com.fansz.fandom.provider;

import com.fansz.common.provider.AbstractProvider;
import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.common.provider.model.CommonPagedResult;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;
import com.fansz.fandom.api.FandomApi;
import com.fansz.fandom.model.fandom.*;
import com.fansz.fandom.model.profile.UserInfoResult;
import com.fansz.fandom.model.relationship.ExitFandomParam;
import com.fansz.fandom.model.relationship.JoinFandomsParam;
import com.fansz.fandom.model.relationship.MemberFandomQueryParam;
import com.fansz.fandom.service.FandomService;
import com.fansz.pub.utils.StringTools;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 圈子接口类
 */
@Component("fandomProvider")
public class FandomProvider extends AbstractProvider implements FandomApi {

    @Autowired
    private FandomService fandomService;

    @Override
    public CommonResult<List<FandomInfoResult>> listAllFandoms(FandomQueryParam fandomQueryParam) throws ApplicationException {
        fandomQueryParam.setCurrentSn(null);
        fandomQueryParam.setFandomId(null);
        fandomQueryParam.setFandomParentId(null);
        return renderSuccess(fandomService.listFandom(fandomQueryParam));

    }

    @Override
    public CommonResult<NullResult> joinFandom(JoinFandomsParam joinFandomsParam) throws ApplicationException{
        List<String> fandomIdList=joinFandomsParam.getFandomIds();
        if(fandomIdList==null){
            fandomIdList=new ArrayList<String>();
        }
        if(StringTools.isNotBlank(joinFandomsParam.getFandomId())){
            fandomIdList.add(joinFandomsParam.getFandomId());
        }
        joinFandomsParam.setFandomIds(fandomIdList);
        fandomService.joinFandom(joinFandomsParam);
        return renderSuccess();
    }

    @Override
    public CommonResult<NullResult> exitFandom(ExitFandomParam exitFandomParam) throws ApplicationException{
        fandomService.exitFandom(exitFandomParam);
        return renderSuccess();
    }

    @Override
    public CommonPagedResult<FandomInfoResult> getMyFandoms(MemberFandomQueryParam fandomParam) throws ApplicationException{
        // 获得我关注的fandom
        PageBounds pageBounds = new PageBounds(fandomParam.getPageNum(), fandomParam.getPageSize());
        String sn = fandomParam.getFriendSn();
        if(null == sn || "".equals(sn)){
            sn = fandomParam.getCurrentSn();
        }
        PageList<FandomInfoResult> fandoms = fandomService.findFandomsByMemberSn(sn, pageBounds);
        return renderPagedSuccess(fandoms);
    }

    /**
     * 获取圈子fandom接口
     *
     * @param fandomQueryParam 查询参数
     * @return CommonResult<List<FandomInfoResult>> 返回对象
     */
    public CommonPagedResult<FandomInfoResult> getRecommendFandom(FandomQueryParam fandomQueryParam) throws ApplicationException{
        PageList<FandomInfoResult> result = fandomService.getRecommendFandom(fandomQueryParam);
        return renderPagedSuccess(result);
    }

    /**
     * 获取fandom大小分类接口
     *
     * @param fandomQueryParam 查询参数
     * @return CommonResult<List<Map<String, Object>>> 返回对象
     */
    @Override
    public CommonResult<List<Map<String, Object>>> getFandomCategory(FandomQueryParam fandomQueryParam) throws ApplicationException{
        List<Map<String, Object>> result = fandomService.getFandomCategory(fandomQueryParam);
        return renderSuccess(result);
    }

    /**
     * 获取圈子fandom接口
     *
     * @param fandomQueryParam 查询参数
     * @return CommonResult<List<FandomCategory>> 返回对象
     */
    @Override
    public CommonPagedResult<UserInfoResult> getFandomMembers(FandomQueryParam fandomQueryParam) throws ApplicationException{
        PageList<UserInfoResult> result = fandomService.getFandomMembers(fandomQueryParam);
        return renderPagedSuccess(result);
    }

    @Override
    public CommonPagedResult<FandomInfoResult> searchFandoms(SearchFandomParam searchFandomParam) throws ApplicationException{
        PageList<FandomInfoResult> pageList = fandomService.searchFandoms(searchFandomParam);
        return super.renderPagedSuccess(pageList);
    }

    public CommonResult<FandomInfoResult> getFandom(FandomInfoParam fandomInfoParam) throws ApplicationException{
        FandomInfoResult result = this.fandomService.getFandomInfo(fandomInfoParam);
        return renderSuccess(result);
    }

    @Override
    public CommonResult<FandomInfoResult> addFandom(AddFandomParam addFandomParam) throws ApplicationException{
        FandomInfoResult fandomInfoResult = fandomService.addFandom(addFandomParam);
        return renderSuccess(fandomInfoResult);
    }
    @Override
    public CommonResult<FandomInfoResult> addJoinFandom(AddFandomParam addFandomParam) throws ApplicationException{
        FandomInfoResult fandomInfoResult = fandomService.addFandom(addFandomParam);

        //关注fandom
        JoinFandomsParam joinFandomsParam = new JoinFandomsParam();
        joinFandomsParam.setCurrentSn(fandomInfoResult.getFandomCreatorSn());
        joinFandomsParam.setFandomIds(Arrays.asList(String.valueOf(fandomInfoResult.getId())));
        fandomService.joinFandom(joinFandomsParam);

        //查询fandom详细信息
        FandomInfoParam fandomInfoParam = new FandomInfoParam();
        fandomInfoParam.setCurrentSn(fandomInfoResult.getFandomCreatorSn());
        fandomInfoParam.setFandomId(fandomInfoResult.getId());
        fandomInfoResult = this.fandomService.getFandomInfo(fandomInfoParam);

        return renderSuccess(fandomInfoResult);
    }

    @Override
    public CommonResult<NullResult> delFandom(DelFandomParam delFandomParam) throws ApplicationException{
        fandomService.delFandom(delFandomParam);
        return renderSuccess();
    }

    @Override
    public CommonResult<FandomInfoResult> modifyFandom(ModifyFandomParam modifyFandomParam) throws ApplicationException{
        FandomInfoResult fandomInfoResult = fandomService.modifyFandom(modifyFandomParam);
        return renderSuccess(fandomInfoResult);
    }
}

