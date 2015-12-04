package com.fansz.members.extension;

import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.protocol.rest.RpcExceptionMapper;
import com.alibaba.dubbo.rpc.protocol.rest.ViolationReport;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.NullResult;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Created by allan on 15/12/4.
 */
public class ValidationExceptionMapper implements ExceptionMapper<RpcException> {
    private final static NullResult PRESENCE = new NullResult();

    @Override
    public Response toResponse(RpcException e) {
        if (e.getCause() instanceof ValidationException) {
            return handleViolationException((ValidationException) e.getCause());
        }
        CommonResult<NullResult> result = new CommonResult<>();
        result.setStatus("10001");
        result.setResult(PRESENCE);
        result.setMessage("Internal Server Error");
        // 采用json输出代替xml输出
        return Response.status(Response.Status.OK).entity(result).type(ContentType.APPLICATION_JSON_UTF_8).build();
    }

    protected Response handleViolationException(ValidationException cve) {
        CommonResult<NullResult> result = new CommonResult<>();
        result.setStatus("10008");
        result.setResult(PRESENCE);
        result.setMessage("Parameter is wrong");
        // 采用json输出代替xml输出
        return Response.status(Response.Status.OK).entity(result).type(ContentType.APPLICATION_JSON_UTF_8).build();
    }
}
