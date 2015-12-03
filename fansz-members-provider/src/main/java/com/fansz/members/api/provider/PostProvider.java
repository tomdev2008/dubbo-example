package com.fansz.members.api.provider;

import com.fansz.members.api.PostApi;
import com.fansz.members.api.service.PostService;
import com.fansz.members.tools.Constants;
import com.fansz.members.exception.ApplicationException;
import com.fansz.members.model.CommonPagedResult;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.NullResult;
import com.fansz.members.model.post.*;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * 帖子接口类
 * Created by root on 15-11-3.
 */
@Component("postProvider")
public class PostProvider implements PostApi {

    private final static NullResult PRESENCE = new NullResult();

    @Autowired
    private PostService postService;

    /**
     * 发帖子接口
     *
     * @param addPostParam 帖子信息
     * @return resp 返回对象
     */
    public CommonResult<NullResult> addPost(AddPostParam addPostParam) {
        postService.addPost(addPostParam);
        CommonResult<NullResult> result = new CommonResult<NullResult>();
        result.setResult(PRESENCE);
        result.setStatus(Constants.SUCCESS);
        return result;
    }

    /**
     * 删除帖子接口
     *
     * @param id 帖子id
     * @return resp 返回对象
     */
    public Response removePost(@PathParam("id") String id) {
        return null;
    }

    /**
     * 获取帖子信息接口
     *
     * @param id 帖子id
     * @return resp 返回对象
     */
    public Response getPost(@PathParam("id") String id) {
        return null;

    }

    /**
     * 帖子点赞接口
     *
     * @param postParam 帖子id
     * @return resp 返回对象
     */
    public CommonResult<List<PostLikeInfoResult>> likePost(PostParam postParam) {
        CommonResult<List<PostLikeInfoResult>> commonResult = new CommonResult<List<PostLikeInfoResult>>();
        try {
            if (null != postParam) {
                List<PostLikeInfoResult> result = postService.likePost(postParam);
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
     * 取消帖子点赞接口
     *
     * @param id 帖子id
     * @return resp 返回对象
     */
    public Response unlikePost(String id) {
        return null;
    }

    /**
     * 获得好友的所有帖子接口
     *
     * @param friendId 好友id
     * @return resp 返回对象
     */
    public Response getFriendPosts(String friendId) {
        return null;
    }

    /**
     * 获得所有好友的所有帖子接口
     *
     * @param getPostsParam 分页参数
     * @return resp 返回对象
     */
    public CommonPagedResult<List<PostInfoResult>> getFriendsPosts(GetPostsParam getPostsParam) {
        CommonPagedResult<List<PostInfoResult>> result = new CommonPagedResult<List<PostInfoResult>>();
        result.setStatus(Constants.SUCCESS);
        PageBounds pageBounds = new PageBounds(getPostsParam.getOffset(), getPostsParam.getLimit());
        PageList<PostInfoResult> dataResult = postService.getFriendsPosts(getPostsParam.getMemberSn(), pageBounds);
        result.setResult(dataResult);
        result.setTotalNum(dataResult.getPaginator().getTotalCount());
        return result;
    }

    /**
     * 获得我所关注的所有fandom的所有帖子接口
     *
     * @param getPostsParam 分页参数
     * @return resp 返回对象
     */
    public CommonPagedResult<List<PostInfoResult>> getFandomPosts(GetPostsParam getPostsParam) {

        CommonPagedResult<List<PostInfoResult>> result = new CommonPagedResult<List<PostInfoResult>>();
        result.setStatus(Constants.SUCCESS);
        PageBounds pageBounds = new PageBounds(getPostsParam.getOffset(), getPostsParam.getLimit());
        PageList<PostInfoResult> dataResult = postService.findPostsOfMyFandoms(getPostsParam.getMemberSn(), pageBounds);
        result.setResult(dataResult);
        result.setTotalNum(dataResult.getPaginator().getTotalCount());
        return result;
    }

}
