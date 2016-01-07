package com.fansz.fandom.api;

import com.fansz.common.provider.annotation.DubboxMethod;
import com.fansz.common.provider.annotation.DubboxService;
import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;
import com.fansz.fandom.model.fandom.FandomTagParam;
import com.fansz.fandom.model.fandom.FandomTagResult;

import java.util.List;

/**
 * Created by dell on 2015/12/29.
 */
@DubboxService("fandomTag")
public interface FandomTagApi {

    /**
     * 查询某一个fandom的所有的标签
     * @param fandomTagParam
     * @return
     */
    @DubboxMethod("selectFandomTagsByFandomId")
    CommonResult<List<FandomTagResult>> selectFandomTagsByFandomId(FandomTagParam fandomTagParam) throws ApplicationException;

    /**
     * 删除某一条fandom的标签
     * @param fandomTagParam
     * @return
     */
    @DubboxMethod("delFandomTagByTagId")
    CommonResult<FandomTagResult> delFandomTagByTagId(FandomTagParam fandomTagParam) throws ApplicationException;

    /**
     * 新增一个fandom的标签
     * @param fandomTagParam
     * @return
     */
    @DubboxMethod("addTagByfandomId")
    CommonResult<NullResult> addTagByfandomId(FandomTagParam fandomTagParam) throws ApplicationException;

}
