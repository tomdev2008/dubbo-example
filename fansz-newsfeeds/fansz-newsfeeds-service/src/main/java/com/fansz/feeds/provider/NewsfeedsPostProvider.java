package com.fansz.feeds.provider;

import com.fansz.common.provider.AbstractProvider;
import com.fansz.common.provider.constant.ErrorCode;
import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.common.provider.model.CommonPagedResult;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;
import com.fansz.db.entity.NewsfeedsPost;
import com.fansz.feeds.service.NewsfeedsPostService;
import com.fansz.newsfeeds.api.NewsfeedsPostApi;
import com.fansz.newsfeeds.model.post.*;
import com.fansz.pub.model.Page;
import com.fansz.pub.utils.BeanTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 帖子接口类
 * Created by root on 15-11-3.
 */
@Component("newsfeedsPostProvider")
public class NewsfeedsPostProvider extends AbstractProvider implements NewsfeedsPostApi {

    @Autowired
    private NewsfeedsPostService newsfeedsPostService;

    /**
     * 发帖子接口
     *
     * @param addPostParam 帖子信息
     * @return resp 返回对象
     */
    @Override
    public CommonResult<PostInfoResult> addPost(AddPostParam addPostParam) throws ApplicationException {
        Long postId = newsfeedsPostService.addPost(addPostParam);
        GetPostByIdParam param = new GetPostByIdParam();
        param.setPostId(postId);
        return getPost(param);
    }


    @Override
    public CommonResult<PostInfoResult> removePost(RemovePostParam removePostrParam) throws ApplicationException {
        NewsfeedsPost newsfeedsPost = newsfeedsPostService.deletePostById(removePostrParam);
        if (null != newsfeedsPost) {
            PostInfoResult postInfoResult = BeanTools.copyAs(newsfeedsPost, PostInfoResult.class);
            return renderSuccess(postInfoResult);
        }
        return renderFail(ErrorCode.POST_NOT_ALLOW_DEL.getCode());
    }

    /**
     * 给朋友圈内容点赞
     *
     * @param postParam
     * @return
     * @throws ApplicationException
     */
    @Override
    public CommonResult<NullResult> voteNewsfeedLike(GetPostByIdParam postParam) throws ApplicationException {
        String code = newsfeedsPostService.saveNewsfeedLike(postParam);
        if (null != code) {
            return renderFail(code);
        }
        return renderSuccess();
    }

    /**
     * 取消给朋友圈内容点赞
     *
     * @param postParam
     * @return
     * @throws ApplicationException
     */
    @Override
    public CommonResult<NullResult> delNewsfeedLike(GetPostByIdParam postParam) throws ApplicationException {
        String code = newsfeedsPostService.deleteNewsfeedLike(postParam);
        if (null != code) {
            return renderFail(code);
        }
        return renderSuccess();
    }

    /**
     * 单查某一条朋友圈的详情:N008
     * 根据postid来查询post的详情,APP可根据缓存跳转
     *
     * @param postParam 帖子
     * @return resp 返回对象
     */
    public CommonResult<PostInfoResult> getPost(GetPostByIdParam postParam) throws ApplicationException {
        PostInfoResult postInfoResult = newsfeedsPostService.getPost(postParam);
        return renderSuccess(postInfoResult);
    }

    /**
     * 查询我的朋友圈内容列表：N007
     * 显示我的所有好友发表的朋友圈的内容列表
     *
     * @param memberParam
     * @return
     * @throws ApplicationException
     */
    @Override
    public CommonPagedResult<PostInfoResult> getMyNewsfeedsList(GetPostsParam memberParam) throws ApplicationException {
        return renderPagedSuccess(newsfeedsPostService.findNewsfeedsListByMemberSn(memberParam));
    }

    /**
     * 查询某一个人发布的所有的所有朋友圈内容:N009
     * 根据会员号sn查询某人发布的朋友圈内容
     *
     * @param memberPostsParam
     * @return
     * @throws ApplicationException
     */
    @Override
    public CommonPagedResult<PostInfoResult> getFriendsFeedsList(GetMemberPostsParam memberPostsParam) throws ApplicationException {
        return renderPagedSuccess(newsfeedsPostService.findFriendsNewsfeedsListBySn(memberPostsParam));
    }
}
