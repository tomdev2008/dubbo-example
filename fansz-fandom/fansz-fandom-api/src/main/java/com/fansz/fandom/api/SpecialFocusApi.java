package com.fansz.fandom.api;

import com.fansz.common.provider.annotation.DubboxMethod;
import com.fansz.common.provider.annotation.DubboxService;
import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;
import com.fansz.fandom.model.specialfocus.ModifySpecialFocusParam;
import com.fansz.fandom.model.specialfocus.SpecialFocusParam;
import com.fansz.fandom.model.specialfocus.SpecialFocusResult;

import java.util.List;

/**
 * 特别关注
 * Created by dell on 2015/12/9.
 */
@DubboxService("specialFocus")
public interface SpecialFocusApi {
    /**
     * 获取某人特别关注的好友和圈子信息
     *
     * @param specialFocusParam
     * @return
     */
    @DubboxMethod("getAllSpecialFocus")
    CommonResult<List<SpecialFocusResult>> getSpecialFocusInfo(SpecialFocusParam specialFocusParam) throws ApplicationException;

    /**
     * 添加特别关注关联信息
     *
     * @param specialFocusParam
     * @return
     */
    CommonResult<NullResult> addSpecialFocusInfo(SpecialFocusParam specialFocusParam) throws ApplicationException;

    /**
     * 修改特别关注关联信息
     *
     * @param specialFocusParam
     * @return
     */
    @DubboxMethod("setSpecialFocusOrder")
    CommonResult<NullResult> modifySpecialFocusInfo(ModifySpecialFocusParam specialFocusParam) throws ApplicationException;

    /**
     * 删除特别关注关联信息
     *
     * @param specialFocusParam
     * @return
     */
    CommonResult<NullResult> delSpecialFocusInfo(SpecialFocusParam specialFocusParam) throws ApplicationException;

}
