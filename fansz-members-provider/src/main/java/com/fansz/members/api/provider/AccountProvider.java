package com.fansz.members.api.provider;

import com.fansz.appservice.configuration.security.AppUserDetails;
import com.fansz.appservice.persistence.domain.User;
import com.fansz.appservice.resource.param.ChangePasswordPara;
import com.fansz.appservice.resource.param.RegisterPara;
import com.fansz.appservice.resource.param.ResetPasswordParam;
import com.fansz.appservice.service.AccountService;
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
import java.util.Vector;

/**
 * 账户接口类
 * Created by xuzhen on 15/11/3.
 */
@Service
@Component("accountProvider")
public class AccountProvider {

    @Autowired
    private ErrorParser errorParser;

    @Autowired
    private AccountService accountService;

    /**
     * 重置密码接口
     * @param resetPasswordParam 密码对象
     * @return resp 返回对象
     */
    @POST
    @Path("/password/reset")
    @Consumes("application/json")
    @Produces("application/json")
    public Response resetPassword(ResetPasswordParam resetPasswordParam) {
        Vector<ErrorMessage> errorMessages = new Vector<>();
        try {
            accountService.resetPassword(resetPasswordParam);
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
     * 获取忘记密码验证码接口
     * @param mobile 手机号码
     * @return resp 返回对象
     */
    @GET
    @Path("/identify/password/{mobile}")
    @Produces("application/json")
    public Response getPasswordIdentifyCode(@PathParam("mobile") String mobile) {
        Vector<ErrorMessage> errorMessages = new Vector<>();
        try {
            accountService.getPasswordIdentifyCode(mobile);
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
     * 获取注册验证码接口
     * @param mobile 手机号码
     * @return resp 返回对象
     */
    @GET
    @Path("/identify/register/{mobile}")
    @Produces("application/json")
    public Response getRegisterIdentifyCode(@PathParam("mobile") String mobile)
    {
        Vector<ErrorMessage> errorMessages = new Vector<>();
        try {
            accountService.getRegisterIdentifyCode(mobile);
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
     * 修改密码接口
     * @param changePasswordPara 修改密码对象
     * @return resp 返回对象
     */
    @POST
    @Path("/password/change")
    @Consumes("application/json")
    @Produces("application/json")
    public Response changePassword(ChangePasswordPara changePasswordPara)
    {
        Vector<ErrorMessage> errorMessages = new Vector<>();

        try {
            AppUserDetails appUserDetails = (AppUserDetails   ) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Assert.notNull(appUserDetails, "error.user.null");

            accountService.changePassword(changePasswordPara);

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
     * 注册接口
     * @param registerPara 注册对象
     * @return resp 返回对象
     */
    @POST
    @Path("/register")
    @Consumes("application/json")
    @Produces("application/json")
    public Response register(RegisterPara registerPara)
    {
        Vector<ErrorMessage> errorMessages = new Vector<>();
        User user = null;
        try {
             user = accountService.register(registerPara);

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
            return StringUtils.getSuccessResponse(1, user);
        }
    }
}
