package com.fansz.members.api;

<<<<<<< HEAD
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.param.NullResult;

=======
>>>>>>> 959ccc6f288736da7023f3bbfd307fdceab3a9c3
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * 验证码相关服务
 */
@Path("/verifyCodes")
public interface VerifyCodeApi {
    /**
<<<<<<< HEAD
     * 获取验证码,用于重置密码场景
=======
     * 获取验证码,用于忘记密码场景
>>>>>>> 959ccc6f288736da7023f3bbfd307fdceab3a9c3
     *
     * @param mobile 手机号码
     * @return resp 返回对象
     */
    @GET
<<<<<<< HEAD
    @Path("/reset/{mobile}")
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    public CommonResult<NullResult> getVerifyCodeForReset(@PathParam("mobile") String mobile);
=======
    @Path("/passwd/{mobile}")
    @Produces("application/json")
    public Response getPasswordIdentifyCode(@PathParam("mobile") String mobile);
>>>>>>> 959ccc6f288736da7023f3bbfd307fdceab3a9c3

    /**
     * 获取验证码,用于用户注册场景
     *
     * @param mobile 手机号码
     * @return resp 返回对象
     */
    @GET
    @Path("/register/{mobile}")
<<<<<<< HEAD
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    public CommonResult<NullResult> getVerifyCodeForRegister(@PathParam("mobile") String mobile);
=======
    @Produces("application/json")
    public Response getRegisterIdentifyCode(@PathParam("mobile") String mobile);
>>>>>>> 959ccc6f288736da7023f3bbfd307fdceab3a9c3
}
