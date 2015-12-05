package com.fansz.members.api.repository;

import com.fansz.members.api.entity.SeedingSpotEntity;
import com.fansz.members.model.seedingspot.SeedingSpotPrama;
import com.fansz.members.model.seedingspot.SeedingSpotResult;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.mybatis.spring.annotation.MapperScan;


/**
 * Created by dell on 2015/12/5.
 */
@MapperScan
public interface SeedingSpotEntityMapper {

    /**
     * 添加广告位信息
     * @param seedingSpotEntity
     * @return
     */
    int addSeedingSpot(SeedingSpotEntity seedingSpotEntity);

    /**
     * 删除广告位信息
     * @param seedingSpotEntity
     * @return
     */
    int delSeedingSpot(SeedingSpotEntity seedingSpotEntity);

    /**
     * 修改广告位信息
     * @param seedingSpotEntity
     * @return
     */
    int modifySeedingSpot(SeedingSpotEntity seedingSpotEntity);

    /**
     * 根据ID查询广告位信息
     * @param id
     * @return
     */
    SeedingSpotResult getSeedingSpotById(Long id);

    PageList<SeedingSpotResult> getSeedingSpot(PageBounds pageBounds);

}
