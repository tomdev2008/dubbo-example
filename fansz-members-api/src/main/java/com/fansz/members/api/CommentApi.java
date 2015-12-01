package com.fansz.members.api;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.NullResult;
import com.fansz.members.model.comment.CommentDelParam;
import com.fansz.members.model.comment.CommentPagedParam;
import com.fansz.members.model.comment.CommentParam;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * Created by root on 15-11-26.
 */
@Path("/comments")
public interface CommentApi {

    /**
     * 发布帖子评论接口
     * @param commentPara 评论信息
     * @return resp 返回对象
     */
    @POST
    @Path("/create")
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    public CommonResult<NullResult> addPostComment(CommentParam commentPara);

    @POST
    @Path("/reply")
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    public CommonResult<NullResult> replyComment(CommentParam commentPara);

    /**
     * 删除评论接口
     * @param commentDelParam 评论id
     * @return resp 返回对象
     */
    @POST
    @Path("/del")
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    public CommonResult<NullResult> removeCommet(CommentDelParam commentDelParam);

    /**
     * 获取评论详细信息接口
     * @param commentPagePara 评论参数
     * @return resp 返回对象
     */
    @GET
    @Path("/post")
    @Produces("application/json")
    public CommonResult<NullResult> getComments(CommentPagedParam commentPagePara);
}
