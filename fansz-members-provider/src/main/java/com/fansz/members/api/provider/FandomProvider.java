package com.fansz.members.api.provider;

import com.fansz.members.api.FandomApi;
import com.fansz.members.api.service.FandomService;
import com.fansz.members.api.service.ProfileService;
import com.fansz.members.api.utils.Constants;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.fandom.*;
import com.fansz.members.model.post.GetPostsParam;
import com.fansz.members.model.post.PostInfoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * 圈子接口类
 * Created by root on 15-11-3.
 */
@Component("fandomProvider")
public class FandomProvider implements FandomApi {

    @Autowired
    private FandomService fandomService;

    @Override
    public CommonResult<List<FandomInfoResult>> listFandoms(FandomQueryParam fandomQueryParam) {
        CommonResult<List<FandomInfoResult>> result = new CommonResult<List<FandomInfoResult>>();
        result.setStatus(Constants.SUCCESS);
        result.setResult(fandomService.listFandom(fandomQueryParam));
        result.setMessage("List fandoms successfully");
        return result;

    }
}

