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
 * 特殊关注服务提供者
 */
@Component("specialFocusProvider")
public class SpecialFocusProvider extends AbstractProvider implements SpecialFocusApi {

    @Autowired
    private SpecialFocusService specialFocusService;

    @Override
    public CommonResult<List<SpecialFocusResult>> getSpecialFocusInfo(SpecialFocusParam specialFocusParam) {
        List<SpecialFocusResult> pageList = specialFocusService.getSpecialFocusInfo(specialFocusParam);
        return renderSuccess(pageList);
    }

    @Override
    public CommonResult<NullResult> addSpecialFocusInfo(SpecialFocusParam specialFocusParam) {
        int count = specialFocusService.addSpecialFocusInfo(specialFocusParam);
        return renderSuccess();
    }

    @Override
    public CommonResult<NullResult> modifySpecialFocusInfo(ModifySpecialFocusParam specialFocusParam) {
        specialFocusService.modifySpecialFocusInfo(specialFocusParam);
        return renderSuccess();
    }

    @Override
    public CommonResult<NullResult> delSpecialFocusInfo(SpecialFocusParam specialFocusParam) {
        specialFocusService.delSpecialFocusInfo(specialFocusParam);
        return renderSuccess();
    }
}
