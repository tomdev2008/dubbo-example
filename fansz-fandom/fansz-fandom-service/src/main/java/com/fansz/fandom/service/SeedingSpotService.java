package com.fansz.fandom.service;

import com.fansz.fandom.model.seedingspot.SeedingSpotParam;
import com.fansz.fandom.model.seedingspot.SeedingSpotResult;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

/**
 * Created by dell on 2015/12/5.
 */
public interface SeedingSpotService {

    /**
     * 添加广告位信息
     * @param seedingSpotParam
     * @return
     */
    int addSeedingSpot(SeedingSpotParam seedingSpotParam);

    /**
     * 删除广告位信息
     * @param seedingSpotParam
     * @return
     */
    int delSeedingSpot(SeedingSpotParam seedingSpotParam);

    /**
     * 修改广告位信息
     * @param seedingSpotParam
     * @return
     */
    int modifySeedingSpot(SeedingSpotParam seedingSpotParam);

    SeedingSpotResult getSeedingSpotById(SeedingSpotParam seedingSpotParam);

    PageList<SeedingSpotResult> getSeedingSpot(SeedingSpotParam seedingSpotParam);

    PageList<SeedingSpotResult> getSeedingSpotByStatus(SeedingSpotParam seedingSpotParam);

    int modifySeedingSpotStatus(SeedingSpotParam seedingSpotParam);
}
