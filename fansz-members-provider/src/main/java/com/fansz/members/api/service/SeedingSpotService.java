package com.fansz.members.api.service;

import com.fansz.members.model.seedingspot.SeedingSpotPrama;
import com.fansz.members.model.seedingspot.SeedingSpotResult;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

/**
 * Created by dell on 2015/12/5.
 */
public interface SeedingSpotService {

    /**
     * 添加广告位信息
     * @param seedingSpotPrama
     * @return
     */
    int addSeedingSpot(SeedingSpotPrama seedingSpotPrama);

    /**
     * 删除广告位信息
     * @param seedingSpotPrama
     * @return
     */
    int delSeedingSpot(SeedingSpotPrama seedingSpotPrama);

    /**
     * 修改广告位信息
     * @param seedingSpotPrama
     * @return
     */
    int modifySeedingSpot(SeedingSpotPrama seedingSpotPrama);

    SeedingSpotResult getSeedingSpotById(SeedingSpotPrama seedingSpotPrama);

    PageList<SeedingSpotResult> getSeedingSpot(SeedingSpotPrama seedingSpotPrama);

}
