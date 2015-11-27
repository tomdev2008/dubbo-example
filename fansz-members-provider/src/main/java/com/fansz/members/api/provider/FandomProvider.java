package com.fansz.members.api.provider;

import com.fansz.members.api.service.CategoryService;
import com.fansz.members.api.service.FandomService;
import com.fansz.members.api.service.ProfileService;
import com.fansz.members.api.utils.ErrorMessage;
import com.fansz.members.api.utils.ErrorParser;
import com.fansz.members.api.utils.StringUtils;
import com.fansz.members.model.*;
import com.fansz.members.model.param.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Vector;

/**
 * 圈子接口类
 * Created by root on 15-11-3.
 */
@Service
@Component("/fandomProvider")
public class FandomProvider {

    @Autowired
    private ErrorParser errorParser;

    @Autowired
    private FandomService fandomService;

    @Autowired
    ProfileService profileService;

    @Autowired
    private CategoryService categoryService;

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
        Vector<ErrorMessage> errorMessages = new Vector<>();
        Fandom fandom = null;
        try {
            fandom = fandomService.addFandom(fandomParam);

        } catch (IllegalArgumentException iae) {
            errorMessages.add(errorParser.phase(iae.getMessage()));
        } catch (ConstraintViolationException cve) {
            errorMessages.addAll(errorParser.phase(cve.getConstraintViolations()));
        } catch (Exception e) {
            errorMessages.add(errorParser.phase("error.unknown"));
        }
        if (errorMessages.size() != 0) {
            return StringUtils.getErrorResponse(errorMessages);
        }
        else {
            return StringUtils.getSuccessResponse(0, fandom);
        }
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
        Vector<ErrorMessage> errorMessages = new Vector<>();
        Fandom fandom = null;
        try {
            fandom = fandomService.getFandom(fandomPara);

        } catch (IllegalArgumentException iae) {
            errorMessages.add(errorParser.phase(iae.getMessage()));
        } catch (ConstraintViolationException cve) {
            errorMessages.addAll(errorParser.phase(cve.getConstraintViolations()));
        } catch (Exception e) {
            errorMessages.add(errorParser.phase("error.unknown"));
        }
        if (errorMessages.size() != 0) {
            return StringUtils.getErrorResponse(errorMessages);
        }
        else {
            return StringUtils.getSuccessResponse(0, fandom);
        }
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
        Vector<ErrorMessage> errorMessages = new Vector<>();
        List<Post> posts = null;
        try {
            posts = fandomService.getPostsByFandom(param);

        } catch (IllegalArgumentException iae) {
            errorMessages.add(errorParser.phase(iae.getMessage()));
        } catch (ConstraintViolationException cve) {
            errorMessages.addAll(errorParser.phase(cve.getConstraintViolations()));
        } catch (Exception e) {
            errorMessages.add(errorParser.phase("error.unknown"));
        }
        if (errorMessages.size() != 0) {
            return StringUtils.getErrorResponse(errorMessages);
        }
        else {
            return StringUtils.getSuccessResponse(0, posts);
        }
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
        Vector<ErrorMessage> errorMessages = new Vector<>();
        List<Fandom> fandoms = null;
        try {
            fandoms = fandomService.getFandomsByCategoryId(fandomByCategory);

        } catch (IllegalArgumentException iae) {
            errorMessages.add(errorParser.phase(iae.getMessage()));
        } catch (ConstraintViolationException cve) {
            errorMessages.addAll(errorParser.phase(cve.getConstraintViolations()));
        } catch (Exception e) {
            errorMessages.add(errorParser.phase("error.unknown"));
        }
        if (errorMessages.size() != 0) {
            return StringUtils.getErrorResponse(errorMessages);
        }
        else {
            return StringUtils.getSuccessResponse(0, fandoms);
        }
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
        Vector<ErrorMessage> errorMessages = new Vector<>();
        try {
            fandomService.followFandom(fandomPara);

        } catch (IllegalArgumentException iae) {
            errorMessages.add(errorParser.phase(iae.getMessage()));
        } catch (ConstraintViolationException cve) {
            errorMessages.addAll(errorParser.phase(cve.getConstraintViolations()));
        } catch (Exception e) {
            errorMessages.add(errorParser.phase("error.unknown"));
        }
        if (errorMessages.size() != 0) {
            return StringUtils.getErrorResponse(errorMessages);
        }
        else {
            return StringUtils.getSuccessResponse(0, null);
        }
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
        Vector<ErrorMessage> errorMessages = new Vector<>();
        try {
            fandomService.unfollowFandom(id);

        } catch (IllegalArgumentException iae) {
            errorMessages.add(errorParser.phase(iae.getMessage()));
        } catch (ConstraintViolationException cve) {
            errorMessages.addAll(errorParser.phase(cve.getConstraintViolations()));
        } catch (Exception e) {
            errorMessages.add(errorParser.phase("error.unknown"));
        }
        if (errorMessages.size() != 0) {
            return StringUtils.getErrorResponse(errorMessages);
        }
        else {
            return StringUtils.getSuccessResponse(0, null);
        }
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
        Vector<ErrorMessage> errorMessages = new Vector<>();
        List<Fandom> fandom = null;
        try {
            fandom = fandomService.getRecommendFandom(id);

        } catch (IllegalArgumentException iae) {
            errorMessages.add(errorParser.phase(iae.getMessage()));
        } catch (ConstraintViolationException cve) {
            errorMessages.addAll(errorParser.phase(cve.getConstraintViolations()));
        } catch (Exception e) {
            errorMessages.add(errorParser.phase("error.unknown"));
        }

        if (errorMessages.size() != 0)
        {
            return StringUtils.getErrorResponse(errorMessages);
        }
        else
        {
            return StringUtils.getSuccessResponse(0, fandom);
        }
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
        Vector<ErrorMessage> errorMessages = new Vector<>();
        Category categorie = null;
        try {
            // 获取所有圈子类别
            categorie = categoryService.getCategoryById(id);
        } catch (IllegalArgumentException iae) {
            errorMessages.add(errorParser.phase(iae.getMessage()));
        } catch (ConstraintViolationException cve) {
            errorMessages.addAll(errorParser.phase(cve.getConstraintViolations()));
        } catch (Exception e) {
            errorMessages.add(errorParser.phase("error.unknown"));
        }
        if (errorMessages.size() != 0) {
            return StringUtils.getErrorResponse(errorMessages);
        }
        else {
            return StringUtils.getSuccessResponse(0, categorie);
        }
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
        Vector<ErrorMessage> errorMessages = new Vector<>();
        List<Category> categories = null;
        try {
            // 获取所有圈子类别
            categories = categoryService.getAllCategory();
        } catch (IllegalArgumentException iae) {
            errorMessages.add(errorParser.phase(iae.getMessage()));
        } catch (ConstraintViolationException cve) {
            errorMessages.addAll(errorParser.phase(cve.getConstraintViolations()));
        } catch (Exception e) {
            errorMessages.add(errorParser.phase("error.unknown"));
        }
        if (errorMessages.size() != 0) {
            return StringUtils.getErrorResponse(errorMessages);
        }
        else {
            return StringUtils.getSuccessResponse(0, categories);
        }
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
        Vector<ErrorMessage> errorMessages = new Vector<>();
        SubCategory subCategory = null;
        try {
            // 获取所有圈子类别
            subCategory = categoryService.getFandomsBySubCategory(id, id);
        } catch (IllegalArgumentException iae) {
            errorMessages.add(errorParser.phase(iae.getMessage()));
        } catch (ConstraintViolationException cve) {
            errorMessages.addAll(errorParser.phase(cve.getConstraintViolations()));
        } catch (Exception e) {
            errorMessages.add(errorParser.phase("error.unknown"));
        }
        if (errorMessages.size() != 0) {
            return StringUtils.getErrorResponse(errorMessages);
        }
        else {
            return StringUtils.getSuccessResponse(0, subCategory);
        }
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
        Vector<ErrorMessage> errorMessages = new Vector<>();

        List<User> users = null;
        try {
            users = fandomService.followerOfFandom(fandomFollowers);

        } catch (IllegalArgumentException iae) {
            errorMessages.add(errorParser.phase(iae.getMessage()));
        } catch (ConstraintViolationException cve) {
            errorMessages.addAll(errorParser.phase(cve.getConstraintViolations()));
        } catch (Exception e) {
            errorMessages.add(errorParser.phase("error.unknown"));
        }
        if (errorMessages.size() != 0) {
            return StringUtils.getErrorResponse(errorMessages);
        }
        else {
            return StringUtils.getSuccessResponse(0, users);
        }
    }


}

