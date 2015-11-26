package com.fansz.members.api.provider;

import com.fansz.appservice.configuration.security.AppUserDetails;
import com.fansz.appservice.resource.param.FandomFollowers;
import com.fansz.appservice.resource.param.FandomParam;
import com.fansz.appservice.resource.param.GetPostsParam;
import com.fansz.appservice.service.CategoryService;
import com.fansz.appservice.service.FandomService;
import com.fansz.appservice.service.ProfileService;
import com.fansz.appservice.utils.ErrorMessage;
import com.fansz.appservice.utils.ErrorParser;
import com.fansz.appservice.utils.StringUtils;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.inject.Singleton;
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
     * @param form 圈子参数
     * @return resp 返回对象
     */
    @POST
    @Path("/add")
    @Consumes("multipart/form-data")
    @Produces("application/json")
    public Response addFandom(FormDataMultiPart form)
    {
        Vector<ErrorMessage> errorMessages = new Vector<>();
        Fandom fandom = null;
        try {
            FandomParam fandomParam = new FandomParam(form);

            AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Assert.notNull(appUserDetails, "error.user.null");

            fandom = fandomService.addFandom(appUserDetails.getUser(),fandomParam);

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
     * @param id 圈子id
     * @return resp 返回对象
     */
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getFandom(@PathParam("id") String id)
    {
        Vector<ErrorMessage> errorMessages = new Vector<>();
        Fandom fandom = null;
        try {
            AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Assert.notNull(appUserDetails, "error.user.null");
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
    public Response getPostsByFandom(GetPostsParam param)
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

