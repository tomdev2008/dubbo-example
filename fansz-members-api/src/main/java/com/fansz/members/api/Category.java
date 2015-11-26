package com.fansz.members.api;

import com.fansz.members.model.param.CategoryParam;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Created by root on 15-11-26.
 */
@Path("/category")
public interface Category {

    /**
     * 添加圈子类别接口
     * @return resp 返回对象
     */
    @POST
    @Path("/add")
    @Produces("application/json")
    public Response addCategory(CategoryParam categoryParam);

    /**
     * 添加圈子子类别接口
     * @return resp 返回对象
     */
    @POST
    @Path("/add/subCategory")
    @Produces("application/json")
    public Response addSubCategory(CategoryParam categoryParam);


}
