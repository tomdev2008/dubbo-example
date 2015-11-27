package com.fansz.members.api;

import com.fansz.members.model.comment.CommentDelParam;
import com.fansz.members.model.comment.CommentPagedParam;
import com.fansz.members.model.comment.CommentParam;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by root on 15-11-26.
 */
@Path("/comments")
public interface CommentApi {

    /**
     * 发布评论接口
     * @param commentPara 评论信息
     * @return resp 返回对象
     */
    @POST
    @Path("/create")
    @Consumes("application/json")
    @Produces("application/json")
    public Response addComment(CommentParam commentPara);

    /**
     * 删除评论接口
     * @param commentDelParam 评论id
     * @return resp 返回对象
     */
    @POST
    @Path("/del")
    @Produces("application/json")
    public Response removeCommet(CommentDelParam commentDelParam);

    /**
     * 获取评论详细信息接口
     * @param commentPagePara 评论参数
     * @return resp 返回对象
     */
    @GET
    @Path("/post")
    @Produces("application/json")
    public Response getComments(CommentPagedParam commentPagePara);
}
