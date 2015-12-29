package com.fansz.fandom.repository;

import com.fansz.fandom.entity.SeedingSpotEntity;
import com.fansz.fandom.model.seedingspot.SeedingSpotResult;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;


/**
 * Created by dell on 2015/12/5.
 */
@MapperScan
public interface SeedingSpotEntityMapper {

    /**
     * 添加广告位信息
     */
    int addSeedingSpot(SeedingSpotEntity seedingSpotEntity);

    /**
     * 删除广告位信息
     */
    int delSeedingSpot(SeedingSpotEntity seedingSpotEntity);

    /**
     * 修改广告位信息
     */
    int modifySeedingSpot(SeedingSpotEntity seedingSpotEntity);

    /**
     * 根据ID查询广告位信息
     */
    SeedingSpotResult getSeedingSpotById(Long id);

    /**
     * 查询所有广告位信息（支持分页）
     */
    PageList<SeedingSpotResult> getSeedingSpot(PageBounds pageBounds);

    /**
     * 查询推荐位（查询生效的，查询历史的）
     * @param status
     * @param pageBounds
     * @return
     */
    PageList<SeedingSpotResult> getSeedingSpotByStatus(String status,PageBounds pageBounds);

    /**
     * 推荐位上架、失效
     * @param id
     * @param stauts
     * @return
     */
    int modifySeedingSpotStatus(@Param("id") Long id,@Param("stauts") String stauts);

}
