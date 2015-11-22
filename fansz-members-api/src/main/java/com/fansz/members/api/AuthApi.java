package com.fansz.members.api;

import com.fansz.members.model.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by allan on 15/11/20.
 */
@Path("auth")
public interface AuthApi {
    /**
     * 用户登录
     *
     * @param userParameters
     * @return
     */
    @POST
    @Path("login")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.TEXT_PLAIN})
    @Produces({MediaType.APPLICATION_JSON})
    CommonResult<LoginResult> login(UserParameters userParameters);

    /**
     * accessToken失效,重新获取
     *
     * @param refreshParameters
     * @return
     */
    @POST
    @Path("refresh")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    CommonResult<RefreshResult> refreshToken(RefreshParameters refreshParameters);

}
