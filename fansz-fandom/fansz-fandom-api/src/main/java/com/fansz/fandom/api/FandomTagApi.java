package com.fansz.fandom.api;

import com.fansz.common.provider.annotation.DubboxService;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;
import com.fansz.fandom.model.fandom.FandomTagParam;
import com.fansz.fandom.model.fandom.FandomTagResult;

import javax.validation.constraints.Null;
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
    CommonResult<List<FandomTagResult>> selectFandomTagsByFandomId(FandomTagParam fandomTagParam);

    /**
     * 删除某一条fandom的标签
     * @param fandomTagParam
     * @return
     */
    CommonResult<FandomTagResult> delFandomTagByTagId(FandomTagParam fandomTagParam);

    /**
     * 新增一个fandom的标签
     * @param fandomTagParam
     * @return
     */
    CommonResult<NullResult> addTagByfandomId(FandomTagParam fandomTagParam);

}
