package com.fansz.members.api.provider;

import com.fansz.members.api.PostApi;
import com.fansz.members.api.service.PostService;
import com.fansz.members.api.service.ProfileService;
import com.fansz.members.model.PageParam;
import com.fansz.members.model.post.PostInfoResult;
import com.fansz.members.model.post.PostParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    /**
     * 发帖子接口
     * @param form 帖子信息
     * @return resp 返回对象
     */
    public Response addPost(PostParam form){
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
    public Response getFriendsPosts(PageParam pagePara) {
        return null;
    }

    /**
     * 获得我所关注的所有fandom的所有帖子接口
     * @param pagePara 分页参数
     * @return resp 返回对象
     */
    public Response getAllFandomPosts(PageParam pagePara) {
        return null;
    }
//   返回对象 @POST
//    @Path("/add")
//    @Consumes("multipart/form-data")
//    @Produces("application/json")
//    public Response addPost(FormDataMultiPart form){
//        Vector<ErrorMessage> errorMessages = new Vector<>();
//        Post post = null;
//        try {
//            AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            Assert.notNull(appUserDetails, "error.profile.null");
//
//            PostParam postParam = new PostParam(form);
//
//            post = postService.addPost(
//                    appUserDetails.getUser(),
////                    profileService.getProfile("3aT2L0XgFeIVUlQAol9rNf"),
//                    postParam);
//            Assert.notNull(post, "error.post.add");
//
//        } catch (IllegalArgumentException iae) {
//            errorMessages.add(errorParser.phase(iae.getMessage()));
//        } catch (Exception e) {
//            errorMessages.add(errorParser.phase("error.unknown"));
//        }
//        if (errorMessages.size() != 0) {
//            return StringUtils.getErrorResponse(errorMessages);
//        }
//        else {
//            return StringUtils.getSuccessResponse(0, post);
//        }
//    }
//
//    /**
//     * 删除帖子接口
//     * @param id 帖子id
//     * @return resp 返回对象
//     */
//    @POST
//    @Path("/{id}/remove")
//    @Produces("application/json")
//    public Response removePost(@PathParam("id") String id) {
//        Vector<ErrorMessage> errorMessages = new Vector<>();
//        try {
//            AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            Assert.notNull(appUserDetails, "error.profile.null");
//            postService.removePost(id);
//        } catch (IllegalArgumentException iae) {
//            errorMessages.add(errorParser.phase(iae.getMessage()));
//        } catch (Exception e) {
//            errorMessages.add(errorParser.phase("error.unknown"));
//        }
//        if (errorMessages.size() != 0) {
//            return StringUtils.getErrorResponse(errorMessages);
//        }
//        else {
//            return StringUtils.getSuccessResponse(0, null);
//        }
//    }
//
//    /**
//     * 获取帖子信息接口
//     * @param id 帖子id
//     * @return resp 返回对象
//     */
//    @GET
//    @Path("/{id}")
//    @Produces("application/json")
//    public Response getPost(@PathParam("id") String id) {
//        Vector<ErrorMessage> errorMessages = new Vector<>();
//        Post post = null;
//        try {
//            AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            Assert.notNull(appUserDetails, "error.profile.null");
//            post = postService.getPost(appUserDetails.getUser(), id);
//        } catch (IllegalArgumentException iae) {
//            errorMessages.add(errorParser.phase(iae.getMessage()));
//        } catch (Exception e) {
//            errorMessages.add(errorParser.phase("error.unknown"));
//        }
//        if (errorMessages.size() != 0) {
//            return StringUtils.getErrorResponse(errorMessages);
//        }
//        else {
//            return StringUtils.getSuccessResponse(0, post);
//        }
//
//    }
//
//    /**
//     * 帖子点赞接口
//     * @param id 帖子id
//     * @return resp 返回对象
//     */
//    @POST
//    @Path("/{id}/like")
//    @Produces("application/json")
//    public Response likePost(@PathParam("id") String id) {
//        Vector<ErrorMessage> errorMessages = new Vector<>();
//        try {
//            AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            Assert.notNull(appUserDetails, "error.profile.null");
//            postService.likePost(appUserDetails.getUser(), id);
//        } catch (IllegalArgumentException iae) {
//            errorMessages.add(errorParser.phase(iae.getMessage()));
//        } catch (Exception e) {
//            errorMessages.add(errorParser.phase("error.unknown"));
//        }
//        if (errorMessages.size() != 0) {
//            return StringUtils.getErrorResponse(errorMessages);
//        }
//        else {
//            return StringUtils.getSuccessResponse(0, null);
//        }
//    }
//
//    /**
//     * 取消帖子点赞接口
//     * @param id 帖子id
//     * @return resp 返回对象
//     */
//    @POST
//    @Path("/{id}/unlike")
//    @Produces("application/json")
//    public Response unlikePost(@PathParam("id") String id) {
//        Vector<ErrorMessage> errorMessages = new Vector<>();
//        try {
//            AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            Assert.notNull(appUserDetails, "error.profile.null");
//            postService.unlikePost(appUserDetails.getUser(), id);
//        } catch (IllegalArgumentException iae) {
//            errorMessages.add(errorParser.phase(iae.getMessage()));
//        } catch (Exception e) {
//            errorMessages.add(errorParser.phase("error.unknown"));
//        }
//        if (errorMessages.size() != 0) {
//            return StringUtils.getErrorResponse(errorMessages);
//        }
//        else {
//            return StringUtils.getSuccessResponse(0, null);
//        }
//    }
//
//    /**
//     * 获得好友的所有帖子接口
//     * @param friendId 好友id
//     * @return resp 返回对象
//     */
//    @GET
//    @Path("/friend/{id}")
//    @Produces("application/json")
//    public Response getFriendPosts(@PathParam("id") String friendId) {
//        Vector<ErrorMessage> errorMessages = new Vector<>();
//        List<Post> posts = null;
//        try {
//            AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            Assert.notNull(appUserDetails, "error.profile.null");
//
//            posts = postService.getFriendPosts(appUserDetails.getUser(), friendId);
//        } catch (IllegalArgumentException iae) {
//            errorMessages.add(errorParser.phase(iae.getMessage()));
//        } catch (Exception e) {
//            errorMessages.add(errorParser.phase("error.unknown"));
//        }
//        if (errorMessages.size() != 0) {
//            return StringUtils.getErrorResponse(errorMessages);
//        }
//        else {
//            return StringUtils.getSuccessResponse(0, posts);
//        }
//    }
//
//    /**
//     * 获得所有好友的所有帖子接口
//     * @param pagePara 分页参数
//     * @return resp 返回对象
//     */
//    @POST
//    @Path("/friend")
//    @Consumes("application/json")
//    @Produces("application/json")
//    public Response getFriendsPosts(PagePara pagePara) {
//        Vector<ErrorMessage> errorMessages = new Vector<>();
//        List<Post> posts = null;
//
//        try {
//            AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            Assert.notNull(appUserDetails, "error.profile.null");
//
//            posts = postService.getFriendsPosts(appUserDetails.getUser(), pagePara);
//
//
//        } catch (IllegalArgumentException iae) {
//            errorMessages.add(errorParser.phase(iae.getMessage()));
//        } catch (Exception e) {
//            errorMessages.add(errorParser.phase("error.unknown"));
//        }
//        if (errorMessages.size() != 0) {
//            return StringUtils.getErrorResponse(errorMessages);
//        }
//        else {
//            return StringUtils.getSuccessResponse(0, posts);
//        }
//    }
//
//    /**
//     * 获得我所关注的所有fandom的所有帖子接口
//     * @param pagePara 分页参数
//     * @return resp 返回对象
//     */
//    @POST
//    @Path("/fandom")
//    @Consumes("application/json")
//    @Produces("application/json")
//    public Response getAllFandomPosts(PagePara pagePara) {
//        Vector<ErrorMessage> errorMessages = new Vector<>();
//        List<Post> posts = null;
//
//        try {
//            AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            Assert.notNull(appUserDetails, "error.profile.null");
//
//            posts = postService.getAllFandomPosts(appUserDetails.getUser(), pagePara);
//
//        } catch (IllegalArgumentException iae) {
//            errorMessages.add(errorParser.phase(iae.getMessage()));
//        } catch (Exception e) {
//            errorMessages.add(errorParser.phase("error.unknown"));
//        }
//        if (errorMessages.size() != 0) {
//            return StringUtils.getErrorResponse(errorMessages);
//        }
//        else {
//            return StringUtils.getSuccessResponse(0, posts);
//        }
//    }
}
