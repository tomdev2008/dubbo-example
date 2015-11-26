package com.fansz.members.api.provider;

import com.fansz.members.api.FandomApi;
import com.fansz.members.api.service.CategoryService;
import com.fansz.members.api.service.FandomService;
import com.fansz.members.api.service.ProfileService;
import com.fansz.members.api.utils.ErrorMessage;
import com.fansz.members.api.utils.StringUtils;
import com.fansz.members.model.fandom.FandomQueryParam;
import com.fansz.members.model.fandom.FandomFollowers;
import com.fansz.members.model.fandom.PostsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
public class FandomProvider implements FandomApi{


    @Autowired
    private FandomService fandomService;

    @Autowired
    ProfileService profileService;

    @Autowired
    private CategoryService categoryService;



    /**
     * 获取圈子信息接口
     * @param fandomQueryParam 圈子id
     * @return resp 返回对象
     */
    @GET
    @Path("/show")
    @Produces("application/json")
    public Response getFandom(FandomQueryParam fandomQueryParam)
    {
        Vector<ErrorMessage> errorMessages = new Vector<>();
        Fandom fandom = null;
        try {
            fandom = fandomService.getFandom(appUserDetails.getUser(), id);

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
    public Response getPostsByFandom(PostsQueryParam param)
    {
        Vector<ErrorMessage> errorMessages = new Vector<>();
        List<Post> posts = null;
        try {
            AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Assert.notNull(appUserDetails, "error.user.null");
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
     * @param categoryId 圈子id
     * @return resp 返回对象
     */
    @GET
    @Path("/category/{categoryId}")
    @Produces("application/json")
    public Response getFandomsByCategory(@PathParam("categoryId") String categoryId)
    {
        Vector<ErrorMessage> errorMessages = new Vector<>();
        List<Fandom> fandoms = null;
        try {
            AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Assert.notNull(appUserDetails, "error.user.null");
            fandoms = fandomService.getFandomsByCategoryId(appUserDetails.getUser(), categoryId);

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
     * @param id 圈子id
     * @return resp 返回对象
     */
    @POST
    @Path("/{id}/follow")
    @Produces("application/json")
    public Response followFandom(@PathParam("id") String id)
    {
        Vector<ErrorMessage> errorMessages = new Vector<>();
        try {
            AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Assert.notNull(appUserDetails, "error.user.null");
            fandomService.followFandom(appUserDetails.getUser(), id);

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
    @Path("/{id}/unfollow")
    @Produces("application/json")
    public Response unfollowFandom(@PathParam("id") String id)
    {
        Vector<ErrorMessage> errorMessages = new Vector<>();
        try {
            AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Assert.notNull(appUserDetails, "error.user.null");
            fandomService.unfollowFandom(appUserDetails.getUser(), id);

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
    @Path("/recommend")
    @Produces("application/json")
    public Response getRecommendFandom()
    {
        Vector<ErrorMessage> errorMessages = new Vector<>();
        List<Fandom> fandom = null;
        try {
            AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Assert.notNull(appUserDetails, "error.user.null");
            fandom = fandomService.getRecommendFandom(appUserDetails.getUser().getId());

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
            AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Assert.notNull(appUserDetails, "error.user.null");

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
            AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Assert.notNull(appUserDetails, "error.user.null");

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
            AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Assert.notNull(appUserDetails, "error.user.null");

            // 获取所有圈子类别
            subCategory = categoryService.getFandomsBySubCategory(appUserDetails.getUser(), id);
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
            AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Assert.notNull(appUserDetails, "error.user.null");
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

