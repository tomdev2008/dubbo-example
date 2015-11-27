package com.fansz.members.extension;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fansz.members.exception.ApplicationException;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.NullResult;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * 异常统一处理
 */
public class ApplicationExceptionMapper implements ExceptionMapper<ApplicationException> {
    public Response toResponse(ApplicationException e) {
        CommonResult<NullResult> result=new CommonResult<>();
        result.setStatus(e.getCode());
        result.setMessage(e.getMessage());
        // 采用json输出代替xml输出
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(result).type(ContentType.APPLICATION_JSON_UTF_8).build();
    }
}
