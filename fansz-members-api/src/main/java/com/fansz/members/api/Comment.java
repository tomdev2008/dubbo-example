package com.fansz.members.api;

import com.fansz.members.model.param.CommentPagePara;
import com.fansz.members.model.param.CommentPara;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by root on 15-11-26.
 */
public interface Comment {

    /**
     * 发布评论接口
     * @param commentPara 评论信息
     * @return resp 返回对象
     */
    @POST
    @Path("/add")
    @Consumes("application/json")
    @Produces("application/json")
    public Response addComment(CommentPara commentPara);

    /**
     * 删除评论接口
     * @param id 评论id
     * @return resp 返回对象
     */
    @POST
    @Path("/remove/{id}")
    @Produces("application/json")
    public Response removeCommet(@PathParam("id") String id);

    /**
     * 获取评论详细信息接口
     * @param commentPagePara 评论参数
     * @return resp 返回对象
     */
    @POST
    @Path("/post")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getComments(CommentPagePara commentPagePara);
}
