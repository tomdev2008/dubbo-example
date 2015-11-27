package com.fansz.members.api.provider;

import com.fansz.members.api.CommentApi;
import com.fansz.members.api.service.CommentService;
import com.fansz.members.api.utils.ErrorMessage;
import com.fansz.members.api.utils.ErrorParser;
import com.fansz.members.api.utils.StringUtils;
<<<<<<< HEAD
import com.fansz.members.model.comment.CommentPagePara;
import com.fansz.members.model.comment.CommentParam;
=======
import com.fansz.members.model.param.CommentPagePara;
import com.fansz.members.model.param.CommentPara;
>>>>>>> 959ccc6f288736da7023f3bbfd307fdceab3a9c3
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
 * 评论接口类
 * Created by root on 15-11-4.
 */
@Service
@Component("/commentProvider")
public class CommentProvider implements CommentApi{

    @Autowired
    private CommentService commentService;


    /**
     * 发布评论接口
     * @param commentPara 评论信息
     * @return resp 返回对象
     */
    @POST
    @Path("/add")
    @Consumes("application/json")
    @Produces("application/json")
    public Response addComment(CommentParam commentPara)
    {
        Vector<ErrorMessage> errorMessages = new Vector<>();
        Comment comment = null;
        try {

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
