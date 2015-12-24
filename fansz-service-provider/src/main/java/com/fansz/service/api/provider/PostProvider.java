package com.fansz.service.api.provider;

import com.fansz.common.provider.AbstractProvider;
import com.fansz.common.provider.model.CommonPagedResult;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;
import com.fansz.event.producer.EventProducer;
import com.fansz.event.type.AsyncEventType;
import com.fansz.service.api.PostApi;
import com.fansz.service.api.service.PostService;
import com.fansz.service.model.event.PublishPostEvent;
import com.fansz.service.model.post.*;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 帖子接口类
 * Created by root on 15-11-3.
 */
@Component("postProvider")
public class PostProvider extends AbstractProvider implements PostApi {

    @Autowired
    private PostService postService;

    @Resource(name = "eventProducer")
    private EventProducer eventProducer;

    /**
     * 发帖子接口
     *
     * @param addPostParam 帖子信息
     * @return resp 返回对象
     */
    public CommonResult<PostInfoResult> addPost(AddPostParam addPostParam) {
        Long postId = postService.addPost(addPostParam);
        GetPostByIdParam postParam = new GetPostByIdParam();
        postParam.setPostId(postId);
        postParam.setCurrentSn(addPostParam.getCurrentSn());
        if ("1".equals(addPostParam.getPostNewsfeeds())) {//发布到朋友圈
            PublishPostEvent postPublishEvent = new PublishPostEvent(postId, addPostParam.getCurrentSn());
            eventProducer.produce(AsyncEventType.PUBLISH_POST, postPublishEvent);
        }
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
    public CommonResult<PostInfoResult> getPost(GetPostByIdParam postParam) {
        PostInfoResult postInfoResult = postService.getPost(postParam);
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
        PageBounds pageBounds = new PageBounds(getPostsParam.getPageNum(), getPostsParam.getPageSize());
        PageList<PostInfoResult> dataResult = postService.getFriendsPosts(getPostsParam.getCurrentSn(), pageBounds);
        return renderPagedSuccess(dataResult);
    }

    /**
     * 获得我所关注的所有fandom的所有帖子接口
     *
     * @param getPostsParam 分页参数
     * @return resp 返回对象
     */
    public CommonPagedResult<PostInfoResult> listMyFandomPosts(GetPostsParam getPostsParam) {
        PageBounds pageBounds = new PageBounds(getPostsParam.getPageNum(), getPostsParam.getPageSize());
        PageList<PostInfoResult> dataResult = postService.findPostsOfMyFandoms(getPostsParam.getCurrentSn(), pageBounds);
        return renderPagedSuccess(dataResult);
    }

    @Override
    public CommonPagedResult<PostInfoResult> searchPosts(SearchPostParam searchPostParam) {
        PageList<PostInfoResult> dataResult = postService.searchPosts(searchPostParam);
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
        PageList<PostInfoResult> postInfoResults = this.postService.getMemberFandomPosts(getMemberFandomPostsParam);
        return renderPagedSuccess(postInfoResults);
    }

    /**
     * 查询某个fandom的所有post列表
     *
     * @param param 圈子
     * @return
     */
    @Override
    public CommonPagedResult<PostInfoResult> getPostsByFandom(PostsQueryParam param) {
        PageList<PostInfoResult> postInfoResults = this.postService.getFandomPosts(param);
        return renderPagedSuccess(postInfoResults);
    }

    /**
     * 获取某人所有帖子列表
     *
     * @param postParam
     * @return
     */
    @Override
    public CommonPagedResult<PostInfoResult> getAllPostsByMember(GetMemberPostsParam postParam) {
        PageList<PostInfoResult> PostInfoList = postService.getPostsAllByMember(postParam);
        return renderPagedSuccess(PostInfoList);
    }
}
