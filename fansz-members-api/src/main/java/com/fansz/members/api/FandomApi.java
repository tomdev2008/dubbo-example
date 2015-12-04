package com.fansz.members.api;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.fandom.*;
import com.fansz.members.model.post.PostInfoResult;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * fandom服务
 */
@Path("/fandoms")
public interface FandomApi {


    /**
     * 获取圈子fandom接口
     *
     * @param fandomQueryParam 查询参数
     * @return CommonResult<List<FandomInfoResult>> 返回对象
     */
    @POST
    @Path("/list")
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    public CommonResult<List<FandomInfoResult>> listFandoms(FandomQueryParam fandomQueryParam);


    /**
     * 获取圈子fandom接口
     *
     * @param fandomQueryParam 查询参数
     * @return CommonResult<List<FandomInfoResult>> 返回对象
     */
    @POST
    @Path("/recommend")
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    public CommonResult<List<FandomInfoResult>> getRecommendFandom(FandomQueryParam fandomQueryParam);

    /**
     * 获取圈子fandom接口
     *
     * @param fandomQueryParam 查询参数
     * @return CommonResult<List<FandomCategorys>> 返回对象
     */
    @POST
    @Path("/category")
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    public CommonResult<List<FandomCategorys>> getFandomCategory(FandomQueryParam fandomQueryParam);
}
