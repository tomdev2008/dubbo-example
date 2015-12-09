package com.fansz.members.api.provider;

import com.fansz.members.api.PostApi;
import com.fansz.members.api.entity.FandomPostEntity;
import com.fansz.members.api.extension.AbstractProvider;
import com.fansz.members.api.service.PostService;
import com.fansz.members.model.CommonPagedResult;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.NullResult;
import com.fansz.members.model.post.*;
import com.fansz.members.tools.Constants;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 帖子接口类
 * Created by root on 15-11-3.
 */
@Component("postProvider")
public class PostProvider extends AbstractProvider implements PostApi {

    @Autowired
    private PostService postService;

    /**
     * 发帖子接口
     *
     * @param addPostParam 帖子信息
     * @return resp 返回对象
     */
    public CommonResult<GetPostInfoResult> addPost(AddPostParam addPostParam) {
        FandomPostEntity fandomPostEntity = postService.addPost(addPostParam);
        PostParam postParam = new PostParam();
        postParam.setPostId(fandomPostEntity.getId());
        postParam.setMemberSn(addPostParam.getSn());
        return getPost(postParam);
    }


    /**
     * 查询帖子点赞接口
     *
     * @param postParam 帖子id
     * @return resp 返回对象
     */
    public CommonResult<List<PostLikeInfoResult>> listPostVoteList(PostParam postParam) {
        List<PostLikeInfoResult> result = postService.listPostVotes(postParam);
        return renderSuccess(result);
    }

    @Override
    public CommonResult<NullResult> removePost(RemovePostParam removePostrParam) {
        postService.removePost(removePostrParam);
        return renderSuccess(PRESENCE);
    }

    /**
     * 获取帖子信息接口
     *
     * @param postParam 帖子
     * @return resp 返回对象
     */
    public CommonResult<GetPostInfoResult> getPost(PostParam postParam) {
        GetPostInfoResult postInfoResult = postService.getPost(postParam);
        return renderSuccess(postInfoResult);
    }


    /**
     * 点赞
     *
     * @param addLikeParam
     * @return
     */
    @Override
    public CommonResult<NullResult> addLike(AddLikeParam addLikeParam) {
        this.postService.addLike(addLikeParam);
        return renderSuccess();
    }

    /**
     * 取消点赞
     *
     * @param deleteLikeParam
     * @return
     */
    public CommonResult<NullResult> deleteLike(DeleteLikeParam deleteLikeParam) {
        this.postService.deleteLike(deleteLikeParam);
        return renderSuccess();
    }

    /**
     * 获得所有好友的所有帖子接口
     *
     * @param getPostsParam 分页参数
     * @return resp 返回对象
     */
    public CommonPagedResult<PostInfoResult> listFriendsPosts(GetPostsParam getPostsParam) {
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
    public CommonPagedResult<PostInfoResult> listMyFandomPosts(GetPostsParam getPostsParam) {
        PageBounds pageBounds = new PageBounds(getPostsParam.getOffset(), getPostsParam.getLimit());
        PageList<PostInfoResult> dataResult = postService.findPostsOfMyFandoms(getPostsParam.getMemberSn(), pageBounds);
        return renderPagedSuccess(dataResult);
    }

    @Override
    public CommonPagedResult<SearchPostResult> searchPosts(SearchPostParam searchPostParam) {
        PageList<SearchPostResult> dataResult = postService.searchPosts(searchPostParam);
        return renderPagedSuccess(dataResult);
    }

    /**
     * 查询某人在某个fandom的所有帖子列表
     *
     * @param getMemberFandomPostsParam
     * @return
     */
    @Override
    public CommonPagedResult<PostInfoResult> getMemberPostsByFandom(GetMemberFandomPostsParam getMemberFandomPostsParam) {
        PageList<PostInfoResult> memberPostInfoResults = this.postService.getMemberFandomPosts(getMemberFandomPostsParam);
        return renderPagedSuccess(memberPostInfoResults);
    }

    /**
     * 查询某个fandom的所有post列表
     *
     * @param param 圈子
     * @return
     */
    @Override
    public CommonPagedResult<PostInfoResult> getPostsByFandom(PostsQueryParam param) {
        PageList<PostInfoResult> fandomPostInfoResults = this.postService.getFandomPosts(param);
        return renderPagedSuccess(fandomPostInfoResults);
    }

    /**
     * 获取某人所有帖子列表
     *
     * @param postParam
     * @return
     */
    @Override
    public CommonPagedResult<PostInfoResult> getAllPostsByMember(PostParam postParam) {
        PageList<PostInfoResult> PostInfoList = postService.getPostsAllByMember(postParam);
        return renderPagedSuccess(PostInfoList);
    }
}
