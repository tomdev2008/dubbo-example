package com.fansz.service.extension;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;
import com.fansz.service.exception.ApplicationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * 异常统一处理
 */
public class ApplicationExceptionMapper implements ExceptionMapper<RuntimeException> {

    private final static NullResult PRESENCE=new NullResult();
    public Response toResponse(RuntimeException e) {
        e.printStackTrace();
        CommonResult<NullResult> result=new CommonResult<>();
        result.setResult(PRESENCE);
        result.setMessage("System Error");
        if(e instanceof  ApplicationException) {
            ApplicationException ae=(ApplicationException)e;
            result.setStatus(ae.getCode());
            result.setMessage(ae.getMessage());
        }
        else{
            result.setStatus("10001");
        }
        // 采用json输出代替xml输出
        return Response.status(Response.Status.OK).entity(result).type(ContentType.APPLICATION_JSON_UTF_8).build();
    }
}
