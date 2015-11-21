package com.fansz.members.api;

import com.fansz.members.model.LoginResult;
import com.fansz.members.model.User;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * Created by allan on 15/11/20.
 */
@Path("users")
public interface UserService {
    @POST
    @Path("register")
    @Consumes({MediaType.APPLICATION_JSON})
    void register(User user);

    @POST
    @Path("login")
    @Consumes({MediaType.APPLICATION_JSON})
    LoginResult login(User user);

    @GET
    @Path("logout/{id: \\d+}")
    @Consumes({MediaType.APPLICATION_JSON})
    void logout(Long userId);

}
