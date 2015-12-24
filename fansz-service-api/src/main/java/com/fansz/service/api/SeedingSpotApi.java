package com.fansz.service.api;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fansz.common.provider.model.CommonPagedResult;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;
import com.fansz.service.extension.DubboxService;
import com.fansz.service.model.seedingspot.SeedingSpotParam;
import com.fansz.service.model.seedingspot.SeedingSpotResult;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by dell on 2015/12/5.
 */
@Path("/seeding")
@Consumes(ContentType.APPLICATION_JSON_UTF_8)
@Produces(ContentType.APPLICATION_JSON_UTF_8)
public interface SeedingSpotApi {

    /**
     * 添加广告位信息
     * @param seedingSpotParam
     * @return
     */
    @POST
    @Path("/add")
    CommonResult<NullResult> addSeedingSpot(SeedingSpotParam seedingSpotParam);

    /**
     * 删除推荐位（生效的无法删除）
     * @param seedingSpotParam
     * @return
     */
    @POST
    @Path("del")
    CommonResult<NullResult> delSeedingSpot(SeedingSpotParam seedingSpotParam);

    /**
     * 修改广告位信息
     * @param seedingSpotParam
     * @return
     */
    @POST
    @Path("modify")
    CommonResult<NullResult> modifySeedingSpot(SeedingSpotParam seedingSpotParam);

    /**
     * 根据ID查询广告位信息
     * @param seedingSpotParam
     * @return
     */
    @POST
    @Path("getById")
    CommonResult<SeedingSpotResult> getSeedingSpotById(SeedingSpotParam seedingSpotParam);

    /**
     * 查询所有广告位信息（支持分页）
     * @param seedingSpotParam
     * @return
     */
    @POST
    @Path("get")
    @DubboxService("getRecommendInfo")
    CommonPagedResult<SeedingSpotResult> getSeedingSpot(SeedingSpotParam seedingSpotParam);

    /**
     *查询推荐位（查询生效的，查询历史的）
     * @param seedingSpotParam
     * @return
     */
    @POST
    @Path("getByStatus")
    CommonPagedResult<SeedingSpotResult> getSeedingSpotByStatus(SeedingSpotParam seedingSpotParam);

    /**
     *推荐位上架、失效
     * @param seedingSpotParam
     * @return
     */
    @POST
    @Path("modifyStatus")
    CommonResult<NullResult> modifySeedingSpotStatus(SeedingSpotParam seedingSpotParam);

}
