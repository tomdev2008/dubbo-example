package com.fansz.members.api.service.impl;

import com.fansz.members.api.entity.SeedingSpotEntity;
import com.fansz.members.api.repository.SeedingSpotEntityMapper;
import com.fansz.members.api.service.SeedingSpotService;
import com.fansz.members.model.seedingspot.SeedingSpotPrama;
import com.fansz.members.model.seedingspot.SeedingSpotResult;
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
    public int modifySeedingSpot(SeedingSpotPrama seedingSpotPrama) {
        SeedingSpotEntity seedingSpotEntity = BeanTools.copyAs(seedingSpotPrama, SeedingSpotEntity.class);
        return seedingSpotEntityMapper.modifySeedingSpot(seedingSpotEntity);
    }

    @Override
    public int addSeedingSpot(SeedingSpotPrama seedingSpotPrama) {
        SeedingSpotEntity seedingSpotEntity = BeanTools.copyAs(seedingSpotPrama, SeedingSpotEntity.class);
        return seedingSpotEntityMapper.addSeedingSpot(seedingSpotEntity);
    }

    @Override
    public int delSeedingSpot(SeedingSpotPrama seedingSpotPrama) {
        SeedingSpotEntity seedingSpotEntity = BeanTools.copyAs(seedingSpotPrama, SeedingSpotEntity.class);
        return seedingSpotEntityMapper.delSeedingSpot(seedingSpotEntity);
    }

    @Override
    public SeedingSpotResult getSeedingSpotById(SeedingSpotPrama seedingSpotPrama) {
        return seedingSpotEntityMapper.getSeedingSpotById(seedingSpotPrama.getId());
    }

    @Override
    public PageList<SeedingSpotResult> getSeedingSpot(SeedingSpotPrama seedingSpotPrama) {
        PageBounds pageBounds = new PageBounds(seedingSpotPrama.getOffset(), seedingSpotPrama.getLimit());
        return seedingSpotEntityMapper.getSeedingSpot(pageBounds);
    }
}
