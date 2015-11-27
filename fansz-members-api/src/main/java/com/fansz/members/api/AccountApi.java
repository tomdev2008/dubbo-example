package com.fansz.members.api;

import com.fansz.members.model.param.ChangePasswordPara;
import com.fansz.members.model.param.RegisterPara;
import com.fansz.members.model.param.ResetPasswordParam;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * 账号相关服务
 */
@Path("/accounts")
public interface AccountApi {
    /**
     * 用户注册
     *
     * @param registerPara 注册对象
     * @return resp 返回对象
     */
    @POST
    @Path("/register")
    @Consumes("application/json")
    @Produces("application/json")
    public Response register(RegisterPara registerPara);

    /**
     * 重置密码接口
     *
     * @param resetPasswordParam 密码对象
     * @return resp 返回对象
     */
    @POST
    @Path("/password/reset")
    @Consumes("application/json")
    @Produces("application/json")
    public Response resetPassword(ResetPasswordParam resetPasswordParam);


    /**
     * 修改密码接口
     *
     * @param changePasswordPara 修改密码对象
     * @return resp 返回对象
     */
    @POST
    @Path("/password/change")
    @Consumes("application/json")
    @Produces("application/json")
    public Response changePassword(ChangePasswordPara changePasswordPara);


}
