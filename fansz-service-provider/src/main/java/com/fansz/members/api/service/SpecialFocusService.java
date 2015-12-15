package com.fansz.members.api.service;

import com.fansz.members.model.specialfocus.ModifySpecialFocusParam;
import com.fansz.members.model.specialfocus.SpecialFocusParam;
import com.fansz.members.model.specialfocus.SpecialFocusResult;

import java.util.List;

/**
 * Created by dell on 2015/12/9.
 */
public interface SpecialFocusService {

    /**
     *获取某人特别关注的好友和圈子信息
     * @param specialFocusParam
     * @return
     */
    List<SpecialFocusResult> getSpecialFocusInfo(SpecialFocusParam specialFocusParam);

    /**
     * 添加特别关注关联信息
     * @param specialFocusParam
     * @return
     */
    int addSpecialFocusInfo(SpecialFocusParam specialFocusParam);

    /**
     * 修改特别关注关联信息
     * @param specialFocusParam
     * @return
     */
    int modifySpecialFocusInfo(ModifySpecialFocusParam specialFocusParam);

    /**
     * 删除特别关注关联信息
     * @param specialFocusParam
     * @return
     */
    int delSpecialFocusInfo(SpecialFocusParam specialFocusParam);

}
