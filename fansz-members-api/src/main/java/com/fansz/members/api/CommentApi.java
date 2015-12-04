package com.fansz.members.api;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fansz.members.model.CommonPagedResult;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.NullResult;
import com.fansz.members.model.comment.CommentDelParam;
import com.fansz.members.model.comment.CommentParam;
import com.fansz.members.model.comment.CommentQueryFromFandomPram;
import com.fansz.members.model.comment.CommentQueryFromFandomResult;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by root on 15-11-26.
 */
@Path("/comments")
@Consumes(ContentType.APPLICATION_JSON_UTF_8)
@Produces(ContentType.APPLICATION_JSON_UTF_8)
public interface CommentApi {

    /**
     * 发布帖子评论接口
     *
     * @param commentPara 评论信息
     * @return resp 返回对象
     */
    @POST
    @Path("/create")

    public CommonResult<NullResult> addPostComment(CommentParam commentPara);

    @POST
    @Path("/reply")
    public CommonResult<NullResult> replyComment(CommentParam commentPara);

    /**
     * 删除评论接口
     *
     * @param commentDelParam 评论id
     * @return resp 返回对象
     */
    @POST
    @Path("/del")
    public CommonResult<NullResult> removeCommet(CommentDelParam commentDelParam);

    /**
     * 查询帖子下所有的评论列表
     * @param commentQueryFromFandom
     * @return
     */
    @POST
    @Path("/post")
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    public CommonPagedResult<CommentQueryFromFandomResult> getCommentsByPostidFromFandom(CommentQueryFromFandomPram commentQueryFromFandom);

    /**
     * 查看朋友圈里所有的
     * @param commentQueryFromFandom
     * @return
     */
    @GET
    @Path("/post2")
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    public CommonPagedResult<List<CommentQueryFromFandomResult>> getCommentsByPostidFromNewsfeed(CommentQueryFromFandomPram commentQueryFromFandom);

}
