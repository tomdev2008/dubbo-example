package com.fansz.feeds.provider;

import com.fansz.common.provider.AbstractProvider;
import com.fansz.common.provider.constant.ErrorCode;
import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;
import com.fansz.db.entity.NewsfeedsPost;
import com.fansz.feeds.service.NewsfeedsPostService;
import com.fansz.newsfeeds.api.NeswfeedsPostApi;
import com.fansz.newsfeeds.model.post.AddPostParam;
import com.fansz.newsfeeds.model.post.GetPostByIdParam;
import com.fansz.newsfeeds.model.post.PostInfoResult;
import com.fansz.newsfeeds.model.post.RemovePostParam;
import com.fansz.pub.utils.BeanTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 帖子接口类
 * Created by root on 15-11-3.
 */
@Component("neswfeedsPostProvider")
public class NeswfeedsPostProvider extends AbstractProvider implements NeswfeedsPostApi {

    @Autowired
    private NewsfeedsPostService newsfeedsPostService;

    /**
     * 发帖子接口
     *
     * @param addPostParam 帖子信息
     * @return resp 返回对象
     */
    @Override
    public CommonResult<PostInfoResult> addPost(AddPostParam addPostParam)  throws ApplicationException {
        PostInfoResult postInfoResult=null;
        return renderSuccess(postInfoResult);
    }



    @Override
    public CommonResult<PostInfoResult> removePost(RemovePostParam removePostrParam)  throws ApplicationException{
        NewsfeedsPost newsfeedsPost = newsfeedsPostService.deletePostById(removePostrParam);
        if(null != newsfeedsPost){
            PostInfoResult postInfoResult = BeanTools.copyAs(newsfeedsPost,PostInfoResult.class);
            return renderSuccess(postInfoResult);
        }
        return renderFail(ErrorCode.POST_NOT_ALLOW_DEL.getCode());
    }

    /**
     * 获取帖子信息接口
     *
     * @param postParam 帖子
     * @return resp 返回对象
     */
    public CommonResult<PostInfoResult> getPost(GetPostByIdParam postParam)  throws ApplicationException{
        PostInfoResult postInfoResult=null;
        return renderSuccess(postInfoResult);
    }

    /**
     * 给朋友圈内容点赞
     * @param postParam
     * @return
     * @throws ApplicationException
     */
    @Override
    public CommonResult<NullResult> voteNewsfeedLike(GetPostByIdParam postParam) throws ApplicationException {
        String code = newsfeedsPostService.saveNewsfeedLike(postParam);
        if(null != code){
            return renderFail(code);
        }
        return renderSuccess();
    }

    /**
     * 取消给朋友圈内容点赞
     * @param postParam
     * @return
     * @throws ApplicationException
     */
    @Override
    public CommonResult<NullResult> delNewsfeedLike(GetPostByIdParam postParam) throws ApplicationException {
        String code = newsfeedsPostService.deleteNewsfeedLike(postParam);
        if(null != code){
            return renderFail(code);
        }
        return renderSuccess();
    }

}
