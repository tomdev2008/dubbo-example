package com.fansz.service.api;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fansz.common.provider.model.CommonPagedResult;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;
import com.fansz.service.extension.DubboxService;
import com.fansz.service.model.comment.DelCommentParam;
import com.fansz.service.model.comment.AddCommentParam;
import com.fansz.service.model.comment.PostCommentQueryParam;
import com.fansz.service.model.comment.PostCommentQueryResult;

import javax.ws.rs.*;
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
    @DubboxService("commentPost")
    CommonResult<PostCommentQueryResult> addPostComment(AddCommentParam commentPara);

    @POST
    @Path("/reply")
    @DubboxService("replyComment")
    CommonResult<PostCommentQueryResult> replyComment(AddCommentParam commentPara);

    /**
     * 删除评论接口
     *
     * @param delCommentParam 评论id
     * @return resp 返回对象
     */
    @POST
    @Path("/del")
    @DubboxService("deleteComment")
    CommonResult<NullResult> removeCommet(DelCommentParam delCommentParam);

    /**
     * 查询帖子下所有的评论列表
     * @param commentQueryFromFandom
     * @return
     */
    @POST
    @Path("/fandom/show")
    @DubboxService("listPostComments")
    CommonPagedResult<PostCommentQueryResult> getCommentsByPostidFromFandom(PostCommentQueryParam commentQueryFromFandom);

    /**
     * 查看朋友圈里所有的
     * @param commentQueryFromFandom
     * @return
     */
    @POST
    @Path("/newsfeed/show")
    CommonPagedResult<List<PostCommentQueryResult>> getCommentsByPostidFromNewsfeed(PostCommentQueryParam commentQueryFromFandom);

}
