package com.fansz.members.api.provider;

import com.fansz.members.api.service.FandomService;
import com.fansz.members.api.service.ProfileService;
import com.fansz.members.model.fandom.FandomByCategory;
import com.fansz.members.model.fandom.FandomFollowers;
import com.fansz.members.model.fandom.FandomParam;
import com.fansz.members.model.fandom.NormalFandomPara;
import com.fansz.members.model.post.GetPostsParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * 圈子接口类
 * Created by root on 15-11-3.
 */
@Service
@Component("/fandomProvider")
public class FandomProvider {

    @Autowired
    private FandomService fandomService;

    @Autowired
    ProfileService profileService;


    /**
     * 添加圈子接口
     * @param fandomParam 圈子参数
     * @return resp 返回对象
     */
    @POST
    @Path("/add")
    @Consumes("multipart/form-data")
    @Produces("application/json")
    public Response addFandom(FandomParam fandomParam)
    {
        try {
            fandomService.addFandom(fandomParam);

        } catch (Exception iae) {
        }
        return null;
    }

    /**
     * 获取圈子信息接口
     * @param fandomPara 圈子id
     * @return resp 返回对象
     */
    @POST
    @Path("/get")
    @Consumes("multipart/form-data")
    @Produces("application/json")
    public Response getFandom(NormalFandomPara fandomPara)
    {
        try {
            fandomService.getFandom(fandomPara);

        } catch (Exception iae) {

        }
        return null;
    }

    /**
     * 获取圈子里面的帖子信息接口
     * @param param 圈子
     * @return resp 返回对象
     */
    @POST
    @Path("/posts")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getPostsByFandom(GetPostsParam param)
    {
        try {
            fandomService.getPostsByFandom(param);

        } catch (IllegalArgumentException iae) {
        }
        return null;
    }

    /**
     * 分类获取圈子接口
     * @param fandomByCategory 圈子id
     * @return resp 返回对象
     */
    @POST
    @Path("/category")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getFandomsByCategory(FandomByCategory fandomByCategory)
    {
        try {
            fandomService.getFandomsByCategoryId(fandomByCategory);

        } catch (Exception iae) {
        }
        return null;
    }

    /**
     * 关注圈子接口
     * @param fandomPara 圈子id
     * @return resp 返回对象
     */
    @POST
    @Path("/follow")
    @Produces("application/json")
    public Response followFandom(NormalFandomPara fandomPara)
    {
        try {
            fandomService.followFandom(fandomPara);

        } catch (IllegalArgumentException iae) {
        }
        return null;
    }

    /**
     * 取消关注圈子接口
     * @param id 圈子id
     * @return resp 返回对象
     */
    @POST
    @Path("/unfollow/{id}")
    @Produces("application/json")
    public Response unfollowFandom(@PathParam("id") Integer id)
    {
        try {
            fandomService.unfollowFandom(id);

        } catch (Exception iae) {

        }
        return null;
    }

    /**
     * 获取推荐圈子信息接口
     * @return resp 返回对象
     */
    @GET
    @Path("/recommend/{id}")
    @Produces("application/json")
    public Response getRecommendFandom(@PathParam("id") String id)
    {
        try {
            fandomService.getRecommendFandom(id);

        } catch (Exception iae) {
        }
        return null;
    }

    /**
     * 获取所有圈子类别接口
     * @return resp 返回对象
     */
    @POST
    @Path("/category/get/{id}")
    @Produces("application/json")
    public Response getSubCategory(@PathParam("id") String id)
    {
        try {
            // 获取所有圈子类别
             //categoryService.getCategoryById(id);
        } catch (IllegalArgumentException iae) {
        }
        return null;
    }

    /**
     * 获取所有圈类别接口
     * @return resp 返回对象
     */
    @GET
    @Path("/category")
    @Produces("application/json")
    public Response getCategory()
    {
        try {
            // 获取所有圈子类别
            //categoryService.getAllCategory();
        } catch (Exception iae) {
        }
        return null;
    }

    /**
     * 获取圈子类别的所有fandom接口
     * @param id 圈子子类别id
     * @return resp 返回对象
     */
    @POST
    @Path("/subcategory/get/{id}")
    @Produces("application/json")
    public Response getFandomsBySubCategory(@PathParam("id") String id)
    {
        try {
            // 获取所有圈子类别
             //categoryService.getFandomsBySubCategory(id, id);
        } catch (Exception iae) {
        }
        return null;
    }

    /**
     * 获取圈子所有关注人接口
     * @param fandomFollowers 圈子id
     * @return resp 返回对象
     */
    @POST
    @Path("/follower")
    @Consumes("application/json")
    @Produces("application/json")
    public Response followerOfFandom(FandomFollowers fandomFollowers)
    {

        try {fandomService.followerOfFandom(fandomFollowers);

        } catch (Exception iae) {

        }
        return null;
    }


}

