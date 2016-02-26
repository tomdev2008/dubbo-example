package com.fansz.fandom.provider;

import com.fansz.common.provider.AbstractProvider;
import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.common.provider.model.CommonPagedResult;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;
import com.fansz.event.model.PublishPostEvent;
import com.fansz.event.producer.EventProducer;
import com.fansz.event.type.AsyncEventType;
import com.fansz.fandom.api.FandomPostApi;
import com.fansz.fandom.entity.FandomPostEntity;
import com.fansz.fandom.model.post.*;
import com.fansz.fandom.model.vote.VotePostParam;
import com.fansz.fandom.model.vote.VotePostResult;
import com.fansz.fandom.model.vote.VoteResultByPostId;
import com.fansz.fandom.service.PostService;
import com.fansz.pub.constant.PostType;
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
public class FandomPostProvider extends AbstractProvider implements FandomPostApi {

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
    public CommonResult<PostInfoResult> addPost(AddPostParam addPostParam) throws ApplicationException {
        addPostParam.setPostType(PostType.POST.getCode());
        return saveAndGetPost(addPostParam);
    }

    private CommonResult<PostInfoResult> saveAndGetPost(AddPostParam addPostParam) throws ApplicationException {
        FandomPostEntity fandomPost = postService.addPost(addPostParam);
        GetPostByIdParam postParam = new GetPostByIdParam();
        postParam.setPostId(fandomPost.getId());
        postParam.setCurrentSn(addPostParam.getCurrentSn());
        return getPost(postParam);
    }

    /**
     * 创建投票帖
     *
     * @param addVotePostParam
     * @return
     * @throws ApplicationException
     */
    @Override
    public CommonResult<PostInfoResult> addVotePost(AddVotePostParam addVotePostParam) throws ApplicationException {
        addVotePostParam.setPostType(PostType.VOTE_POST.getCode());
        return saveAndGetPost(addVotePostParam);
    }

    /**
     * 查询帖子点赞接口
     *
     * @param postParam 帖子id
     * @return resp 返回对象
     */
    public CommonResult<List<PostLikeInfoResult>> listPostVoteList(PostParam postParam) throws ApplicationException {
        List<PostLikeInfoResult> result = postService.listPostVotes(postParam);
        return renderSuccess(result);
    }

    @Override
    public CommonResult<NullResult> removePost(RemovePostParam removePostrParam) throws ApplicationException {
        postService.removePost(removePostrParam);
        return renderSuccess(PRESENCE);
    }

    /**
     * 获取帖子信息接口
     *
     * @param postParam 帖子
     * @return resp 返回对象
     */
    public CommonResult<PostInfoResult> getPost(GetPostByIdParam postParam) throws ApplicationException {
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
    public CommonResult<NullResult> addLike(AddLikeParam addLikeParam) throws ApplicationException {
        this.postService.addLike(addLikeParam);
        return renderSuccess();
    }

    /**
     * 取消点赞
     *
     * @param deleteLikeParam
     * @return
     */
    public CommonResult<NullResult> deleteLike(DeleteLikeParam deleteLikeParam) throws ApplicationException {
        this.postService.deleteLike(deleteLikeParam);
        return renderSuccess();
    }

    /**
     * 获得我所关注的所有fandom的所有帖子接口
     *
     * @param getPostsParam 分页参数
     * @return resp 返回对象
     */
    public CommonPagedResult<PostInfoResult> listMyFandomPosts(GetPostsParam getPostsParam) throws ApplicationException {
        PageBounds pageBounds = new PageBounds(getPostsParam.getPageNum(), getPostsParam.getPageSize());
        PageList<PostInfoResult> dataResult = postService.findPostsOfMyFandoms(getPostsParam.getCurrentSn(), pageBounds);
        return renderPagedSuccess(dataResult);
    }

    @Override
    public CommonPagedResult<PostInfoResult> searchPosts(SearchPostParam searchPostParam) throws ApplicationException {
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
    public CommonPagedResult<PostInfoResult> getMemberPostsByFandom(GetMemberFandomPostsParam getMemberFandomPostsParam) throws ApplicationException {
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
    public CommonPagedResult<PostInfoResult> getPostsByFandom(PostsQueryParam param) throws ApplicationException {
        //兼容老接口
        if ("new".equals(param.getType()) || "hot".equals(param.getType())) {
            param.setType("P");
            param.setOrder(param.getType());
        } else if ("vote".equals(param.getType())) {
            param.setType("V");
        }
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
    public CommonPagedResult<PostInfoResult> getAllPostsByMember(GetMemberPostsParam postParam) throws ApplicationException {
        PageList<PostInfoResult> PostInfoList = postService.getPostsAllByMember(postParam);
        return renderPagedSuccess(PostInfoList);
    }

    /**
     * 投票
     *
     * @param votePostParam
     * @return
     * @throws ApplicationException
     */
    @Override
    public CommonResult<VotePostResult> votePost(VotePostParam votePostParam) throws ApplicationException {
        VotePostResult votePostResult = postService.votePost(votePostParam);
        return renderSuccess(votePostResult);
    }

    /**
     * 获取投票帖投票结果
     *
     * @param voteResultByPostId
     * @return
     * @throws ApplicationException
     */
    @Override
    public CommonResult<VotePostResult> getVoteResultByPostId(VoteResultByPostId voteResultByPostId) throws ApplicationException {
        VotePostResult votePostResult = postService.getVoteResultByPostId(voteResultByPostId);
        return renderSuccess(votePostResult);
    }
}
