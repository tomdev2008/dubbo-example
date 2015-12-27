package com.fansz.fandom.provider;

import com.fansz.common.provider.AbstractProvider;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;
import com.fansz.fandom.api.SpecialFocusApi;
import com.fansz.fandom.model.specialfocus.ModifySpecialFocusParam;
import com.fansz.fandom.model.specialfocus.SpecialFocusParam;
import com.fansz.fandom.model.specialfocus.SpecialFocusResult;
import com.fansz.fandom.service.SpecialFocusService;
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
