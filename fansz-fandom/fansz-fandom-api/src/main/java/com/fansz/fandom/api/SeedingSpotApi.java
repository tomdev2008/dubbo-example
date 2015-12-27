package com.fansz.fandom.api;

import com.fansz.common.provider.annotation.DubboxMethod;
import com.fansz.common.provider.annotation.DubboxService;
import com.fansz.common.provider.model.CommonPagedResult;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;
import com.fansz.fandom.model.seedingspot.SeedingSpotParam;
import com.fansz.fandom.model.seedingspot.SeedingSpotResult;

/**
 * Created by dell on 2015/12/5.
 */
@DubboxService("seedingSpots")
public interface SeedingSpotApi {

    /**
     * 添加广告位信息
     * @param seedingSpotParam
     * @return
     */
    CommonResult<NullResult> addSeedingSpot(SeedingSpotParam seedingSpotParam);

    /**
     * 删除推荐位（生效的无法删除）
     * @param seedingSpotParam
     * @return
     */
    CommonResult<NullResult> delSeedingSpot(SeedingSpotParam seedingSpotParam);

    /**
     * 修改广告位信息
     * @param seedingSpotParam
     * @return
     */
    CommonResult<NullResult> modifySeedingSpot(SeedingSpotParam seedingSpotParam);

    /**
     * 根据ID查询广告位信息
     * @param seedingSpotParam
     * @return
     */
    CommonResult<SeedingSpotResult> getSeedingSpotById(SeedingSpotParam seedingSpotParam);

    /**
     * 查询所有广告位信息（支持分页）
     * @param seedingSpotParam
     * @return
     */
    @DubboxMethod("getRecommendInfo")
    CommonPagedResult<SeedingSpotResult> getSeedingSpot(SeedingSpotParam seedingSpotParam);

    /**
     *查询推荐位（查询生效的，查询历史的）
     * @param seedingSpotParam
     * @return
     */
    CommonPagedResult<SeedingSpotResult> getSeedingSpotByStatus(SeedingSpotParam seedingSpotParam);

    /**
     *推荐位上架、失效
     * @param seedingSpotParam
     * @return
     */
    CommonResult<NullResult> modifySeedingSpotStatus(SeedingSpotParam seedingSpotParam);

}
