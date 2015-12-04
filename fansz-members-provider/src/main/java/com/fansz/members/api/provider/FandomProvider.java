package com.fansz.members.api.provider;

import com.fansz.members.api.FandomApi;
import com.fansz.members.api.extension.AbstractProvider;
import com.fansz.members.api.service.FandomService;
import com.fansz.members.model.NullResult;
import com.fansz.members.model.relationship.ExitFandomParam;
import com.fansz.members.model.relationship.JoinFandomParam;
import com.fansz.members.model.relationship.MemberFandomQueryParam;
import com.fansz.members.tools.Constants;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.fandom.*;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 圈子接口类
 * Created by root on 15-11-3.
 */
@Component("fandomProvider")
public class FandomProvider extends AbstractProvider implements FandomApi {

    @Autowired
    private FandomService fandomService;

    @Override
    public CommonResult<List<FandomInfoResult>> listFandoms(FandomQueryParam fandomQueryParam) {
        CommonResult<List<FandomInfoResult>> result = new CommonResult<List<FandomInfoResult>>();
        result.setStatus(Constants.SUCCESS);
        result.setResult(fandomService.listFandom(fandomQueryParam));
        result.setMessage("List fandoms successfully");
        return result;

    }
    @Override
    public CommonResult<NullResult> joinFandom(JoinFandomParam joinFandomParam) {
        fandomService.joinFandom(joinFandomParam);
        return renderSuccess(PRESENCE, "Join fandom successfully");
    }

    @Override
    public CommonResult<NullResult> exitFandom(ExitFandomParam exitFandomParam) {
        fandomService.exitFandom(exitFandomParam);
        return renderSuccess(PRESENCE, "Exit fandom successfully");
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

    @Override
    public CommonResult<List<FandomInfoResult>> getMemberFandoms(MemberFandomQueryParam fandomParam) {
        // 获得我关注的fandom
        PageBounds pageBounds = new PageBounds(fandomParam.getOffset(), fandomParam.getLimit());
        List<FandomInfoResult> fandoms = fandomService.findFandomsByMemberSn(fandomParam.getSn(), pageBounds);
        return renderSuccess(fandoms);
    }
}

