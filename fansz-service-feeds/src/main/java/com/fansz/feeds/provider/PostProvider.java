package com.fansz.feeds.provider;

import com.fansz.common.provider.AbstractProvider;
import com.fansz.common.provider.model.CommonPagedResult;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;
import com.fansz.event.producer.EventProducer;
import com.fansz.event.type.AsyncEventType;
import com.fansz.api.newsfeed.PostApi;
import com.fansz.service.api.service.PostService;
import com.fansz.service.exception.ApplicationException;
import com.fansz.service.model.event.PublishPostEvent;
import com.fansz.service.model.post.*;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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
    public CommonResult<PostInfoResult> addPost(AddPostParam addPostParam)  throws ApplicationException {
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



    @Override
    public CommonResult<NullResult> removePost(RemovePostParam removePostrParam)  throws ApplicationException{
        postService.removePost(removePostrParam);
        return renderSuccess(PRESENCE);
    }

    /**
     * 获取帖子信息接口
     *
     * @param postParam 帖子
     * @return resp 返回对象
     */
    public CommonResult<PostInfoResult> getPost(GetPostByIdParam postParam)  throws ApplicationException{
        PostInfoResult postInfoResult = postService.getPost(postParam);
        return renderSuccess(postInfoResult);
    }


    @Override
    public CommonPagedResult<PostInfoResult> searchPosts(SearchPostParam searchPostParam)  throws ApplicationException{
        PageList<PostInfoResult> dataResult = postService.searchPosts(searchPostParam);
        return renderPagedSuccess(dataResult);
    }





}
