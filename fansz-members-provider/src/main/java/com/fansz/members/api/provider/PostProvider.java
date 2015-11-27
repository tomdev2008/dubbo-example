package com.fansz.members.api.provider;

import com.fansz.members.api.PostApi;
import com.fansz.members.api.service.PostService;
import com.fansz.members.api.service.ProfileService;
import com.fansz.members.model.param.PagePara;
import com.fansz.members.model.post.PostInfoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * 帖子接口类
 * Created by root on 15-11-3.
 */
@Component("postProvider")
public class PostProvider implements PostApi{

    @Autowired
    private PostService postService;

    @Autowired
    private ProfileService profileService;

    /**
     * 发帖子接口
     * @param form 帖子信息
     * @return resp 返回对象
     */
    public Response addPost(PostInfoResult form){
        try {
            PostInfoResult postParam = new PostInfoResult();
        } catch (Exception iae) {

        }
        return null;
    }

    /**
     * 删除帖子接口
     * @param id 帖子id
     * @return resp 返回对象
     */
    public Response removePost(@PathParam("id") String id) {
        return null;
    }

    /**
     * 获取帖子信息接口
     * @param id 帖子id
     * @return resp 返回对象
     */
    public Response getPost(@PathParam("id") String id) {
       return null;

    }

    /**
     * 帖子点赞接口
     * @param id 帖子id
     * @return resp 返回对象
     */
    public Response likePost(@PathParam("id") String id) {
        return null;
    }

    /**
     * 取消帖子点赞接口
     * @param id 帖子id
     * @return resp 返回对象
     */
    public Response unlikePost(String id) {
       return null;
    }

    /**
     * 获得好友的所有帖子接口
     * @param friendId 好友id
     * @return resp 返回对象
     */
    public Response getFriendPosts( String friendId) {
        return null;
    }

    /**
     * 获得所有好友的所有帖子接口
     * @param pagePara 分页参数
     * @return resp 返回对象
     */
    public Response getFriendsPosts(PagePara pagePara) {
        return null;
    }

    /**
     * 获得我所关注的所有fandom的所有帖子接口
     * @param pagePara 分页参数
     * @return resp 返回对象
     */
    public Response getAllFandomPosts(PagePara pagePara) {
        return null;
    }
}
