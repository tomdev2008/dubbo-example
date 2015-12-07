package com.fansz.members.api.provider;

import com.fansz.members.api.SeedingSpotApi;
import com.fansz.members.api.extension.AbstractProvider;
import com.fansz.members.api.service.SeedingSpotService;
import com.fansz.members.model.CommonPagedResult;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.NullResult;
import com.fansz.members.model.seedingspot.SeedingSpotPrama;
import com.fansz.members.model.seedingspot.SeedingSpotResult;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by dell on 2015/12/5.
 */
@Component("seedingSpotProvider")
public class SeedingSpotProvider extends AbstractProvider implements SeedingSpotApi {

    private final static NullResult PRESENCE = new NullResult();
    @Autowired
    private SeedingSpotService seedingSpotService;

    @Override
    public CommonResult<NullResult> addSeedingSpot(SeedingSpotPrama seedingSpotPrama) {
        seedingSpotService.addSeedingSpot(seedingSpotPrama);
        return renderSuccess(PRESENCE, "add seedingstop successfully");
    }

    @Override
    public CommonResult<NullResult> delSeedingSpot(SeedingSpotPrama seedingSpotPrama) {
        seedingSpotService.delSeedingSpot(seedingSpotPrama);
        return renderSuccess(PRESENCE, "del seedingstop successfully");
    }

    @Override
    public CommonResult<NullResult> modifySeedingSpot(SeedingSpotPrama seedingSpotPrama) {
        seedingSpotService.modifySeedingSpot(seedingSpotPrama);
        return renderSuccess(PRESENCE, "modify seedingstop successfully");
    }

    @Override
    public CommonResult<SeedingSpotResult> getSeedingSpotById(SeedingSpotPrama seedingSpotPrama) {
        SeedingSpotResult result = seedingSpotService.getSeedingSpotById(seedingSpotPrama);
        return super.renderSuccess(result,"get seedingstop successfully");
    }

    @Override
    public CommonPagedResult<SeedingSpotResult> getSeedingSpot(SeedingSpotPrama seedingSpotPrama) {
        PageList<SeedingSpotResult> pageList = seedingSpotService.getSeedingSpot(seedingSpotPrama);
        return super.renderPagedSuccess(pageList);
    }
}
