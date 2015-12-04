package com.fansz.members.api;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.comment.SearchParam;
import com.fansz.members.model.profile.ModifyProfileParam;
import com.fansz.members.model.profile.UserInfoResult;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.beans.support.PagedListHolder;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by dell on 2015/12/2.
 */
@Path("/search")
public interface SearchApi {

    /**
     * 根据关键字查询 帖子标题 、内容、会员
     * @param searchParam
     * @return
     */
    @POST
    @Path("/keywordSearch")
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    public CommonResult<PageList> keywordSearch(SearchParam searchParam);

    @POST
    @Path("/keywordSearchMember")
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    public CommonResult<PageList> getProfileByNickname(SearchParam searchParam);

}
