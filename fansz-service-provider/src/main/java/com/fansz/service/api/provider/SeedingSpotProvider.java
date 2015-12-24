package com.fansz.service.api.provider;

import com.fansz.common.provider.AbstractProvider;
import com.fansz.common.provider.model.CommonPagedResult;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;
import com.fansz.service.api.SeedingSpotApi;
import com.fansz.service.api.service.SeedingSpotService;
import com.fansz.service.model.seedingspot.SeedingSpotParam;
import com.fansz.service.model.seedingspot.SeedingSpotResult;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by dell on 2015/12/5.
 */
@Component("seedingSpotProvider")
public class SeedingSpotProvider extends AbstractProvider implements SeedingSpotApi {

    @Autowired
    private SeedingSpotService seedingSpotService;

    @Override
    public CommonResult<NullResult> addSeedingSpot(SeedingSpotParam seedingSpotParam) {
        seedingSpotService.addSeedingSpot(seedingSpotParam);
        return renderSuccess();
    }

    @Override
    public CommonResult<NullResult> delSeedingSpot(SeedingSpotParam seedingSpotParam) {
        seedingSpotService.delSeedingSpot(seedingSpotParam);
        return renderSuccess();
    }

    @Override
    public CommonResult<NullResult> modifySeedingSpot(SeedingSpotParam seedingSpotParam) {
        seedingSpotService.modifySeedingSpot(seedingSpotParam);
        return renderSuccess();
    }

    @Override
    public CommonResult<SeedingSpotResult> getSeedingSpotById(SeedingSpotParam seedingSpotParam) {
        SeedingSpotResult result = seedingSpotService.getSeedingSpotById(seedingSpotParam);
        return renderSuccess(result);
    }

    @Override
    public CommonPagedResult<SeedingSpotResult> getSeedingSpot(SeedingSpotParam seedingSpotParam) {
        PageList<SeedingSpotResult> pageList = seedingSpotService.getSeedingSpot(seedingSpotParam);
        return renderPagedSuccess(pageList);
    }

    @Override
    public CommonPagedResult<SeedingSpotResult> getSeedingSpotByStatus(SeedingSpotParam seedingSpotParam) {
        PageList<SeedingSpotResult> pageList = seedingSpotService.getSeedingSpotByStatus(seedingSpotParam);
        return renderPagedSuccess(pageList);
    }

    @Override
    public CommonResult<NullResult> modifySeedingSpotStatus(SeedingSpotParam seedingSpotParam) {
        seedingSpotService.modifySeedingSpotStatus(seedingSpotParam);
        return renderSuccess();
    }
}
