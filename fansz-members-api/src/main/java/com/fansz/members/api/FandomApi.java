package com.fansz.members.api;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.NullResult;
import com.fansz.members.model.fandom.*;
import com.fansz.members.model.relationship.ExitFandomParam;
import com.fansz.members.model.relationship.JoinFandomParam;
import com.fansz.members.model.relationship.MemberFandomQueryParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * fandom服务
 */
@Path("/fandoms")
@Consumes(ContentType.APPLICATION_JSON_UTF_8)
@Produces(ContentType.APPLICATION_JSON_UTF_8)
public interface FandomApi {
    /**
     * 获取用户关注的fandoms
     *
     * @return resp 返回对象
     */
    @POST
    @Path("/myFndoms")
    CommonResult<List<FandomInfoResult>> getMemberFandoms(MemberFandomQueryParam fandomParam);

    /**
     * 获取圈子fandom接口
     *
     * @param fandomQueryParam 查询参数
     * @return CommonResult<List<FandomInfoResult>> 返回对象
     */
    @POST
    @Path("/list")
    public CommonResult<List<FandomInfoResult>> listFandoms(FandomQueryParam fandomQueryParam);

    /**
     * 加入fandom
     *
     * @param joinFandomParam
     * @return
     */
    @POST
    @Path("/fandom/join")
    CommonResult<NullResult> joinFandom(JoinFandomParam joinFandomParam);

    /**
     * 退出fandom
     *
     * @param exitFandomParam
     * @return
     */
    @POST
    @Path("/fandom/exit")
    CommonResult<NullResult> exitFandom(ExitFandomParam exitFandomParam);

    /**
     * 获取圈子信息接口
     *
     * @param fandomInfoParam 圈子id
     * @return resp 返回对象
     */
    @POST
    @Path("/info")
    CommonResult<SingleFandomInfoResult> getFandom(FandomInfoParam fandomInfoParam);

    /**
     * 创建Fandom
     *
     * @param addFandomParam
     * @return
     */
    @POST
    @Path("/addFandom")
    CommonResult<NullResult> addFandom(AddFandomParam addFandomParam);


}
