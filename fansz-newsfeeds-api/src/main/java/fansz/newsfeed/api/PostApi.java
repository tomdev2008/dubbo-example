package fansz.newsfeed.api;

import com.fansz.common.provider.annotation.DubboxService;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;
import fansz.newsfeed.api.model.post.AddPostParam;
import fansz.newsfeed.api.model.post.GetPostByIdParam;
import fansz.newsfeed.api.model.post.PostInfoResult;
import fansz.newsfeed.api.model.post.RemovePostParam;
import org.omg.CORBA.portable.ApplicationException;


/**
 * newsfeeds帖子相关接口
 */
public interface PostApi {
    /**
     * 发帖子接口
     *
     * @param addPostParam 帖子信息
     * @return resp 返回对象
     */
    @DubboxService("publishPost")
    CommonResult<PostInfoResult> addPost(AddPostParam addPostParam) throws ApplicationException;

    /**
     * 删除帖子接口
     *
     * @param removePostrParam 帖子id
     * @return resp 返回对象
     */
    @DubboxService("deletePost")
    CommonResult<NullResult> removePost(RemovePostParam removePostrParam) throws ApplicationException;

    /**
     * 根据postId获取帖子信息接口
     *
     * @param postParam 帖子
     * @return resp 返回对象
     */
    @DubboxService("getPost")
    CommonResult<PostInfoResult> getPost(GetPostByIdParam postParam) throws ApplicationException;

}
