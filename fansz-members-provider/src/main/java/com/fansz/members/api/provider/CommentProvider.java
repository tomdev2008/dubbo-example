package com.fansz.members.api.provider;

import com.fansz.appservice.configuration.security.AppUserDetails;
import com.fansz.appservice.persistence.domain.Comment;
import com.fansz.appservice.resource.param.CommentPagePara;
import com.fansz.appservice.resource.param.CommentPara;
import com.fansz.appservice.service.CommentService;
import com.fansz.appservice.utils.ErrorMessage;
import com.fansz.appservice.utils.ErrorParser;
import com.fansz.appservice.utils.StringUtils;
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
 * 评论接口类
 * Created by root on 15-11-4.
 */
@Service
@Component("/commentProvider")
public class CommentProvider {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ErrorParser errorParser;

    /**
     * 发布评论接口
     * @param commentPara 评论信息
     * @return resp 返回对象
     */
    @POST
    @Path("/add")
    @Consumes("application/json")
    @Produces("application/json")
    public Response addComment(CommentPara commentPara)
    {
        Vector<ErrorMessage> errorMessages = new Vector<>();
        Comment comment = null;
        try {
            AppUserDetails appUserDetails = (AppUserDetails   ) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Assert.notNull(appUserDetails, "error.user.null");

            comment = commentService.addComment(appUserDetails.getUser(), commentPara);

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
            return StringUtils.getSuccessResponse(0, comment);
        }
    }

    /**
     * 删除评论接口
     * @param id 评论id
     * @return resp 返回对象
     */
    @POST
    @Path("/{id}/remove")
    @Produces("application/json")
    public Response removeCommet(@PathParam("id") String id)
    {
        Vector<ErrorMessage> errorMessages = new Vector<>();
        try {
            AppUserDetails appUserDetails = (AppUserDetails   ) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Assert.notNull(appUserDetails, "error.user.null");

            commentService.removeComment(id);

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
     * 获取评论详细信息接口
     * @param commentPagePara 评论参数
     * @return resp 返回对象
     */
    @POST
    @Path("/post")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getComments(CommentPagePara commentPagePara)
    {
        Vector<ErrorMessage> errorMessages = new Vector<>();
        List<Comment> Comments = null;
        try {
            AppUserDetails appUserDetails = (AppUserDetails   ) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Assert.notNull(appUserDetails, "error.user.null");

            Comments = commentService.getComments(commentPagePara);

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
            return StringUtils.getSuccessResponse(0, Comments);
        }
    }
}
