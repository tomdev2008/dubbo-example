package com.fansz.feeds.provider;

import com.fansz.common.provider.AbstractProvider;
import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;
import com.fansz.newsfeed.api.PostApi;
import com.fansz.newsfeed.model.post.AddPostParam;
import com.fansz.newsfeed.model.post.GetPostByIdParam;
import com.fansz.newsfeed.model.post.PostInfoResult;
import com.fansz.newsfeed.model.post.RemovePostParam;
import org.springframework.stereotype.Component;

/**
 * 帖子接口类
 * Created by root on 15-11-3.
 */
@Component("postProvider")
public class PostProvider extends AbstractProvider implements PostApi {

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
    public CommonResult<NullResult> removePost(RemovePostParam removePostrParam)  throws ApplicationException{
        return renderSuccess(PRESENCE);
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




}
