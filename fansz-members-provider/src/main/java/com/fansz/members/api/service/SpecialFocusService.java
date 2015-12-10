package com.fansz.members.api.service;

import com.fansz.members.model.CommonResult;
import com.fansz.members.model.NullResult;
import com.fansz.members.model.specialfocus.ModifySpecialFocusParam;
import com.fansz.members.model.specialfocus.SpecialFocusParam;
import com.fansz.members.model.specialfocus.SpecialFocusResult;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * Created by dell on 2015/12/9.
 */
public interface SpecialFocusService {

    /**
     *获取某人特别关注的好友和圈子信息
     * @param specialFocusParam
     * @return
     */
    @POST
    @Path("/getInfo")
    PageList<SpecialFocusResult> getSpecialFocusInfo(SpecialFocusParam specialFocusParam);

    /**
     * 添加特别关注关联信息
     * @param specialFocusParam
     * @return
     */
    @POST
    @Path("/add")
    int addSpecialFocusInfo(SpecialFocusParam specialFocusParam);

    /**
     * 修改特别关注关联信息
     * @param specialFocusParam
     * @return
     */
    @POST
    @Path("/modify")
    int modifySpecialFocusInfo(ModifySpecialFocusParam specialFocusParam);

    /**
     * 删除特别关注关联信息
     * @param specialFocusParam
     * @return
     */
    @POST
    @Path("/del")
    int delSpecialFocusInfo(SpecialFocusParam specialFocusParam);

}
