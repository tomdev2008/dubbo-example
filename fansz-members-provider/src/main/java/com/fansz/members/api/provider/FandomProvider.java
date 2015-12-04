package com.fansz.members.api.provider;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fansz.members.api.FandomApi;
import com.fansz.members.api.service.FandomService;
import com.fansz.members.exception.ApplicationException;
import com.fansz.members.model.post.PostLikeInfoResult;
import com.fansz.members.model.profile.ContactInfoResult;
import com.fansz.members.tools.Constants;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.fandom.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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

    /**
     * 获取圈子fandom接口
     *
     * @param fandomQueryParam 查询参数
     * @return CommonResult<List<FandomInfoResult>> 返回对象
     */
    public CommonResult<List<FandomInfoResult>> getRecommendFandom(FandomQueryParam fandomQueryParam)
    {
        CommonResult<List<FandomInfoResult>> commonResult = new CommonResult<List<FandomInfoResult>>();
        try {
            if (null != fandomQueryParam) {
                List<FandomInfoResult> result = fandomService.getRecommendFandom(fandomQueryParam);
                commonResult.setResult(result);
                commonResult.setStatus(Constants.SUCCESS);
            } else {
                commonResult.setStatus(Constants.FAIL);
                commonResult.setMessage("json is null");
            }
        } catch (Exception e) {
            throw new ApplicationException("", e.getMessage());
        }
        return commonResult;
    }

    /**
     * 获取fandom大小分类接口
     *
     * @param fandomQueryParam 查询参数
     * @return CommonResult<List<FandomCategorys>> 返回对象
     */
    @Override
    public CommonResult<List<FandomCategorys>> getFandomCategory(FandomQueryParam fandomQueryParam) {
        CommonResult<List<FandomCategorys>> commonResult = new CommonResult<List<FandomCategorys>>();
        try {
            if (null != fandomQueryParam) {
                List<FandomCategorys> result = fandomService.getFandomCategory(fandomQueryParam);
                commonResult.setResult(result);
                commonResult.setStatus(Constants.SUCCESS);
            } else {
                commonResult.setStatus(Constants.FAIL);
                commonResult.setMessage("json is null");
            }
        } catch (Exception e) {
            throw new ApplicationException("", e.getMessage());
        }
        return commonResult;
    }

    /**
     * 获取圈子fandom接口
     *
     * @param fandomQueryParam 查询参数
     * @return CommonResult<List<FandomCategorys>> 返回对象
     */
    @Override
    public CommonResult<List<ContactInfoResult>> getFandomMembers(FandomQueryParam fandomQueryParam) {
        CommonResult<List<ContactInfoResult>> commonResult = new CommonResult<List<ContactInfoResult>>();
        try {
            if (null != fandomQueryParam) {
                List<ContactInfoResult> result = fandomService.getFandomMembers(fandomQueryParam);
                commonResult.setResult(result);
                commonResult.setStatus(Constants.SUCCESS);
            } else {
                commonResult.setStatus(Constants.FAIL);
                commonResult.setMessage("json is null");
            }
        } catch (Exception e) {
            throw new ApplicationException("", e.getMessage());
        }
        return commonResult;
    }
}

