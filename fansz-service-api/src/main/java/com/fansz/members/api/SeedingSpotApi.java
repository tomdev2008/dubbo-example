package com.fansz.members.api;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fansz.members.extension.DubboxService;
import com.fansz.members.model.CommonPagedResult;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.NullResult;
import com.fansz.members.model.post.AddPostParam;
import com.fansz.members.model.seedingspot.SeedingSpotPrama;
import com.fansz.members.model.seedingspot.SeedingSpotResult;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

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
     * @param seedingSpotPrama
     * @return
     */
    @POST
    @Path("/add")
    CommonResult<NullResult> addSeedingSpot(SeedingSpotPrama seedingSpotPrama);

    /**
     * 删除推荐位（生效的无法删除）
     * @param seedingSpotPrama
     * @return
     */
    @POST
    @Path("del")
    CommonResult<NullResult> delSeedingSpot(SeedingSpotPrama seedingSpotPrama);

    /**
     * 修改广告位信息
     * @param seedingSpotPrama
     * @return
     */
    @POST
    @Path("modify")
    CommonResult<NullResult> modifySeedingSpot(SeedingSpotPrama seedingSpotPrama);

    /**
     * 根据ID查询广告位信息
     * @param seedingSpotPrama
     * @return
     */
    @POST
    @Path("getById")
    CommonResult<SeedingSpotResult> getSeedingSpotById(SeedingSpotPrama seedingSpotPrama);

    /**
     * 查询所有广告位信息（支持分页）
     * @param seedingSpotPrama
     * @return
     */
    @POST
    @Path("get")
    @DubboxService("getRecommendInfo")
    CommonPagedResult<SeedingSpotResult> getSeedingSpot(SeedingSpotPrama seedingSpotPrama);

    /**
     *查询推荐位（查询生效的，查询历史的）
     * @param seedingSpotPrama
     * @return
     */
    @POST
    @Path("getByStatus")
    CommonPagedResult<SeedingSpotResult> getSeedingSpotByStatus(SeedingSpotPrama seedingSpotPrama);

    /**
     *推荐位上架、失效
     * @param seedingSpotPrama
     * @return
     */
    @POST
    @Path("modifyStatus")
    CommonResult<NullResult> modifySeedingSpotStatus(SeedingSpotPrama seedingSpotPrama);

}
