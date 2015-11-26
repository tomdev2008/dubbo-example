package com.fansz.members.api;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by root on 15-11-26.
 */
@Path("/service")
public interface Service {

    @GET
    @Path("/image/{id}")
    @Produces("image/*")
    public Response getImage(@DefaultValue("empty.jpg") @PathParam("id") String id,
                             @DefaultValue("0") @QueryParam("width") int width,
                             @DefaultValue("0") @QueryParam("height") int height);

}
