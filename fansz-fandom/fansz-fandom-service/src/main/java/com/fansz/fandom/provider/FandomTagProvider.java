package com.fansz.fandom.provider;

import com.fansz.common.provider.AbstractProvider;
import com.fansz.common.provider.constant.ErrorCode;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;
import com.fansz.fandom.api.FandomTagApi;
import com.fansz.fandom.model.fandom.FandomTagParam;
import com.fansz.fandom.model.fandom.FandomTagResult;
import com.fansz.fandom.service.FandomTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by dell on 2015/12/29.
 */
@Component("fandomTagProvider")
public class FandomTagProvider extends AbstractProvider implements FandomTagApi{

    @Autowired
    private FandomTagService fandomTagService;

    @Override
    public CommonResult<List<FandomTagResult>> selectFandomTagsByFandomId(FandomTagParam fandomTagParam) {
        List<FandomTagResult> fandomTagResultList = fandomTagService.selectFandomTagsByFandomId(fandomTagParam);
        return renderSuccess(fandomTagResultList);
    }

    @Override
    public CommonResult<FandomTagResult> delFandomTagByTagId(FandomTagParam fandomTagParam) {
        FandomTagResult fandomTagResult = fandomTagService.deleteFandomTagByTagId(fandomTagParam);
        if(null == fandomTagResult){
            return renderFail(ErrorCode.TAG_NOT_FOUND.getCode());
        }
        return renderSuccess(fandomTagResult);
    }

    @Override
    public CommonResult<NullResult> addTagByfandomId(FandomTagParam fandomTagParam) {
        String code = fandomTagService.saveTagByfandomId(fandomTagParam);
        if(code == null){
            return renderSuccess();
        }
        return renderFail(code);
    }
}
