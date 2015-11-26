package com.fansz.members.api.provider;

import com.fansz.appservice.configuration.security.AppUserDetails;
import com.fansz.appservice.persistence.domain.Fandom;
import com.fansz.appservice.persistence.domain.User;
import com.fansz.appservice.resource.param.ModifyProfilePara;
import com.fansz.appservice.resource.param.UsersInfoParam;
import com.fansz.appservice.service.ProfileService;
import com.fansz.appservice.utils.ErrorMessage;
import com.fansz.appservice.utils.ErrorParser;
import com.fansz.appservice.utils.StringUtils;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
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
 *
 *
 * Created by root on 15-11-3.
 */
@Service
@Component("/profileProvider")
public class ProfileProvider {

    @Autowired
    private ErrorParser errorParser;

    @Autowired
    private ProfileService profileService;

    @GET
    @Produces("application/json")
    public Response getProfile() {
        Vector<ErrorMessage> errorMessages = new Vector<>();
        User user = null;
        try {
            AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Assert.notNull(appUserDetails, "error.user.null");

            user = profileService.getProfile(appUserDetails.getUser().getId());

        } catch (IllegalArgumentException iae) {
            errorMessages.add(errorParser.phase(iae.getMessage()));
        } catch (Exception e) {
            errorMessages.add(errorParser.phase("error.unknown"));
        }
        if (errorMessages.size() != 0) {
            return StringUtils.getErrorResponse(errorMessages);
        }
        else {
            return StringUtils.getSuccessResponse(0, user);
        }
    }

    @POST
    @Path("/modify")
    @Consumes("application/json")
    @Produces("application/json")
    public Response modifyProfile(ModifyProfilePara modifyProfilePara)
    {
        Vector<ErrorMessage> errorMessages = new Vector<>();
        User user = null;
        try {
            AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Assert.notNull(appUserDetails, "error.user.null");

            profileService.modifyProfile(appUserDetails.getUser().getId(), modifyProfilePara);
            user = profileService.getProfile(appUserDetails.getUser().getId());
        } catch (IllegalArgumentException iae) {
            errorMessages.add(errorParser.phase(iae.getMessage()));
        } catch (Exception e) {
            errorMessages.add(errorParser.phase("error.unknown"));
        }
        if (errorMessages.size() != 0) {
            return StringUtils.getErrorResponse(errorMessages);
        }
        else {
            return StringUtils.getSuccessResponse(1, user);
        }
    }

    /**
     * 获取我所有关注的fandom接口
     * @param form 个人头像信息
     * @return resp 返回对象
     */
    @POST
    @Path("/avatar")
    @Consumes("multipart/form-data")
    @Produces("application/json")
    public Response setAvatar(FormDataMultiPart form)
    {
        Vector<ErrorMessage> errorMessages = new Vector<>();
        String fileName = null;
        try {
            AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Assert.notNull(appUserDetails, "error.user.null");

            FormDataBodyPart filePart = form.getField("avatar");

            fileName = profileService.setAvatar(
                    appUserDetails.getUser().getId(),
                    filePart);

        } catch (IllegalArgumentException iae) {
            errorMessages.add(errorParser.phase(iae.getMessage()));
        } catch (Exception e) {
            errorMessages.add(errorParser.phase("error.unknown"));
        }
        if (errorMessages.size() != 0) {
            return StringUtils.getErrorResponse(errorMessages);
        }
        else {
            return StringUtils.getSuccessResponse(0, fileName);
        }
    }

    /**
     * 获取我所有关注的fandom接口
     * @return resp 返回对象
     */
    @GET
    @Path("/fandom")
    @Produces("application/json")
    public Response getFollowedFandom()
    {
        Vector<ErrorMessage> errorMessages = new Vector<>();
        List<Fandom> fandoms = null;
        try {
            AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Assert.notNull(appUserDetails, "error.user.null");
            // 获得我关注的fandom
            fandoms = profileService.getFollowedFandoms(appUserDetails.getUser().getId());

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
     * 获得我的好友信息
     * @return resp 返回对象
     */
    @POST
    @Path("/get/userInfo")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getUsers(UsersInfoParam usersInfoParam)
    {
        Vector<ErrorMessage> errorMessages = new Vector<>();
        List<User> users = null;
        try {
            AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Assert.notNull(appUserDetails, "error.user.null");
            // 获得所有联系人信息
            users = profileService.getUsers(appUserDetails.getUser().getId(), usersInfoParam.getMobiles());

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
