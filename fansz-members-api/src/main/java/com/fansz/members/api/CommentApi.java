package com.fansz.members.api;

import com.fansz.members.model.comment.CommentDelParam;
import com.fansz.members.model.comment.CommentPagedParam;
import com.fansz.members.model.comment.CommentParam;
import com.fansz.members.model.comment.CommentQueryFromFandom;

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
     * 查询帖子下所有的评论列表
     * @param commentQueryFromFandom
     * @return
     */
    @GET
    @Path("/post")
    @Produces("application/json")
    public Response getCommentsByPostidFromFandom(CommentQueryFromFandom commentQueryFromFandom);

    /**
     * 查看朋友圈里所有的
     * @param commentQueryFromFandom
     * @return
     */
    @GET
    @Path("/post")
    @Produces("application/json")
    public Response getCommentsByPostidFromNewsfeed(CommentQueryFromFandom commentQueryFromFandom);
}
