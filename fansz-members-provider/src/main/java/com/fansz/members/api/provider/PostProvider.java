package com.fansz.members.api.provider;

import com.fansz.members.api.PostApi;
import com.fansz.members.api.extension.AbstractProvider;
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
public class PostProvider extends AbstractProvider implements PostApi {

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
     * 帖子点赞接口
     *
     * @param postParam 帖子id
     * @return resp 返回对象
     */
    public CommonResult<List<PostLikeInfoResult>> listPostVoteList(PostParam postParam) {
        CommonResult<List<PostLikeInfoResult>> commonResult = new CommonResult<>();
        List<PostLikeInfoResult> result = postService.listPostVotes(postParam);
        commonResult.setResult(result);
        commonResult.setStatus(Constants.SUCCESS);
        return commonResult;
    }

    @Override
    public CommonResult<NullResult> removePost(PostParam postParam) {
        return null;
    }

    @Override
    public CommonResult<PostInfoResult> getPost(PostParam postParam) {
        return null;
    }

    @Override
    public CommonResult<List<NullResult>> votePost(PostParam postParam) {
        return null;
    }

    @Override
    public CommonResult<List<NullResult>> removeVote(PostParam postParam) {
        return null;
    }

    /**
     * 获得所有好友的所有帖子接口
     *
     * @param getPostsParam 分页参数
     * @return resp 返回对象
     */
    public CommonPagedResult<PostInfoResult> getFriendsPosts(GetPostsParam getPostsParam) {
        PageBounds pageBounds = new PageBounds(getPostsParam.getOffset(), getPostsParam.getLimit());
        PageList<PostInfoResult> dataResult = postService.getFriendsPosts(getPostsParam.getMemberSn(), pageBounds);
        return renderPagedSuccess(dataResult);
    }

    /**
     * 获得我所关注的所有fandom的所有帖子接口
     *
     * @param getPostsParam 分页参数
     * @return resp 返回对象
     */
    public CommonPagedResult<PostInfoResult> getFandomPosts(GetPostsParam getPostsParam) {
        PageBounds pageBounds = new PageBounds(getPostsParam.getOffset(), getPostsParam.getLimit());
        PageList<PostInfoResult> dataResult = postService.findPostsOfMyFandoms(getPostsParam.getMemberSn(), pageBounds);
        return renderPagedSuccess(dataResult);
    }

}
