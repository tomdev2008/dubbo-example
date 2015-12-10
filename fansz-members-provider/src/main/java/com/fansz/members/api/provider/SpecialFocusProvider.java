package com.fansz.members.api.provider;

import com.fansz.members.api.SpecialFocusApi;
import com.fansz.members.api.extension.AbstractProvider;
import com.fansz.members.api.service.SpecialFocusService;
import com.fansz.members.model.CommonPagedResult;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.NullResult;
import com.fansz.members.model.specialfocus.ModifySpecialFocusParam;
import com.fansz.members.model.specialfocus.SpecialFocusParam;
import com.fansz.members.model.specialfocus.SpecialFocusResult;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by dell on 2015/12/9.
 */
@Component("specialFocusProvider")
public class SpecialFocusProvider extends AbstractProvider implements SpecialFocusApi {

    private final static NullResult PRESENCE = new NullResult();
    @Autowired
    private SpecialFocusService specialFocusService;

    @Override
    public CommonPagedResult<SpecialFocusResult> getSpecialFocusInfo(SpecialFocusParam specialFocusParam) {
        PageList<SpecialFocusResult> pageList = specialFocusService.getSpecialFocusInfo(specialFocusParam);
        return super.renderPagedSuccess(pageList);
    }

    @Override
    public CommonResult<NullResult> addSpecialFocusInfo(SpecialFocusParam specialFocusParam) {
        int count = specialFocusService.addSpecialFocusInfo(specialFocusParam);
        return super.renderSuccess(PRESENCE);
    }

    @Override
    public CommonResult<NullResult> modifySpecialFocusInfo(ModifySpecialFocusParam specialFocusParam) {
        specialFocusService.modifySpecialFocusInfo(specialFocusParam);
        return super.renderSuccess(PRESENCE);
    }

    @Override
    public CommonResult<NullResult> delSpecialFocusInfo(SpecialFocusParam specialFocusParam) {
        specialFocusService.delSpecialFocusInfo(specialFocusParam);
        return super.renderSuccess(PRESENCE);
    }
}
