package com.fansz.members.consumer;

import com.alibaba.fastjson.JSON;
import com.fansz.members.api.*;
import com.fansz.members.consumer.model.HttpRequestBody;
import com.fansz.members.consumer.model.HttpRequestModel;
import com.fansz.members.model.verifycode.VerifyCodeParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by allan on 15/12/5.
 */
@Component("dubboInvoker")
public class DubboInvoker implements  RpcInvoker{

    @Resource(name="accountProvider")
    private AccountApi accountApi;

    @Resource(name="commentProvider")
    private CommentApi commentApi;

    @Resource(name="fandomProvider")
    private FandomApi fandomApi;

    @Resource(name="postProvider")
    private PostApi postApi;

    @Resource(name="profileProvider")
    private ProfileApi profileApi;

    @Resource(name="relationShipProvider")
    private RelationShipApi relationShipApi;

    @Resource(name="verifyCodeProvider")
    private VerifyCodeApi verifyCodeApi;

    public DubboInvoker(){

    }

    public String invoke(String url, String requestBody) {
        HttpRequestModel httpRequestModel = JSON.parseObject(requestBody, HttpRequestModel.class);
        for (HttpRequestBody body : httpRequestModel.getRequest()) {
            Object result = invokeSingle(body);
            return JSON.toJSONString(result);
        }
        return null;
    }

    private Object invokeSingle(HttpRequestBody body) {
        if (body.getMethod().equals("getVerifyCodeForRegister")) {
            VerifyCodeParam verifyCodeParam = copyAs(body.getParams(), VerifyCodeParam.class);
            return verifyCodeApi.getVerifyCodeForRegister(verifyCodeParam);
        }
        return null;
    }

    private <T> T copyAs(Map<String, String> params, Class<T> cls) {
        try {
            T instance = cls.newInstance();
            org.apache.commons.beanutils.BeanUtils.populate(instance, params);
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
