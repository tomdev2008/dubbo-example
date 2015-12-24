package com.fansz.service.api.service.impl;

import com.fansz.service.api.entity.SeedingSpotEntity;
import com.fansz.service.api.repository.SeedingSpotEntityMapper;
import com.fansz.service.api.service.SeedingSpotService;
import com.fansz.service.model.seedingspot.SeedingSpotParam;
import com.fansz.service.model.seedingspot.SeedingSpotResult;
import com.fansz.pub.utils.BeanTools;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by dell on 2015/12/5.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class SeedingSpotServiceImpl implements SeedingSpotService {

    @Autowired
    private SeedingSpotEntityMapper seedingSpotEntityMapper;

    @Override
    public int modifySeedingSpot(SeedingSpotParam seedingSpotParam) {
        SeedingSpotEntity seedingSpotEntity = BeanTools.copyAs(seedingSpotParam, SeedingSpotEntity.class);
        return seedingSpotEntityMapper.modifySeedingSpot(seedingSpotEntity);
    }

    @Override
    public int addSeedingSpot(SeedingSpotParam seedingSpotParam) {
        SeedingSpotEntity seedingSpotEntity = BeanTools.copyAs(seedingSpotParam, SeedingSpotEntity.class);
        return seedingSpotEntityMapper.addSeedingSpot(seedingSpotEntity);
    }

    @Override
    public int delSeedingSpot(SeedingSpotParam seedingSpotParam) {
        SeedingSpotEntity seedingSpotEntity = BeanTools.copyAs(seedingSpotParam, SeedingSpotEntity.class);
        return seedingSpotEntityMapper.delSeedingSpot(seedingSpotEntity);
    }

    @Override
    public SeedingSpotResult getSeedingSpotById(SeedingSpotParam seedingSpotParam) {
        return seedingSpotEntityMapper.getSeedingSpotById(seedingSpotParam.getId());
    }

    @Override
    public PageList<SeedingSpotResult> getSeedingSpot(SeedingSpotParam seedingSpotParam) {
        PageBounds pageBounds = new PageBounds(seedingSpotParam.getPageNum(), seedingSpotParam.getPageSize());
        return seedingSpotEntityMapper.getSeedingSpot(pageBounds);
    }

    @Override
    public PageList<SeedingSpotResult> getSeedingSpotByStatus(SeedingSpotParam seedingSpotParam) {
        PageBounds pageBounds = new PageBounds(seedingSpotParam.getPageNum(), seedingSpotParam.getPageSize());
        return seedingSpotEntityMapper.getSeedingSpotByStatus(seedingSpotParam.getStatus(),pageBounds);
    }

    @Override
    public int modifySeedingSpotStatus(SeedingSpotParam seedingSpotParam) {
        return seedingSpotEntityMapper.modifySeedingSpotStatus(seedingSpotParam.getId(), seedingSpotParam.getStatus());
    }
}
