package com.fansz.members.consumer;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fansz.members.api.*;
import com.fansz.members.consumer.utils.BeanTools;
import com.fansz.members.model.account.LoginParam;
import com.fansz.members.model.account.RegisterParam;
import com.fansz.members.model.account.ResetPasswordParam;
import com.fansz.members.model.verifycode.VerifyCodeParam;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by allan on 15/12/5.
 */
@Component("dubboInvoker")
public class DubboInvoker implements RpcInvoker {

    @Resource(name = "accountProvider")
    private AccountApi accountApi;

    @Resource(name = "commentProvider")
    private CommentApi commentApi;

    @Resource(name = "fandomProvider")
    private FandomApi fandomApi;

    @Resource(name = "postProvider")
    private PostApi postApi;

    @Resource(name = "profileProvider")
    private ProfileApi profileApi;

    @Resource(name = "relationShipProvider")
    private RelationShipApi relationShipApi;

    @Resource(name = "verifyCodeProvider")
    private VerifyCodeApi verifyCodeApi;

    public DubboInvoker() {

    }

    public String invoke(String url, String requestBody) {
        Map<String, Object> reqMap = parseParameter(requestBody);
        String header = JSON.toJSONString(reqMap.get("header"));
        JSONArray reqArray = (JSONArray) reqMap.get("request");

        List<String> responseList = mergeRequest(reqArray);
        String response = StringUtils.join(responseList, ",");
        return String.format("{\"header\":%s,\"response\": [%s]}", header, response);
    }

    private Map<String, Object> parseParameter(String requestBody) {
        Map<String, Object> reqMap = JSON.parseObject(requestBody, Map.class);
        return reqMap;
    }

    /**
     * 合并多个请求
     *
     * @param reqArray
     * @return
     */
    private List<String> mergeRequest(JSONArray reqArray) {
        List<String> responseList = new ArrayList<>();
        for (int i = 0; i < reqArray.size(); i++) {
            JSONObject req = reqArray.getJSONObject(i);
            String method = req.getString("method");
            Map<String, Object> params = (Map<String, Object>) req.get("params");
            String response = "";
            try {
                response = invokeRpc(method, params);
            } catch (Exception e) {
                response = "{\"message\": \"System error\", \"result\": {}, \"status\": \"10001\"}";
            }
            String finalString = "{\"method\":\"" + method + "\"," + response.substring(1);
            responseList.add(finalString);
        }
        return responseList;
    }


    /**
     * 调用dubbo服务
     *
     * @param method
     * @param params
     * @return
     */
    private String invokeRpc(String method, Map<String, Object> params) {
        Object result = null;
        if ("getVerifyCodeForRegister".equals(method)) {
            VerifyCodeParam verifyCodeParam = BeanTools.copyAs(params, VerifyCodeParam.class);
            result = verifyCodeApi.getVerifyCodeForRegister(verifyCodeParam);

        } else if ("getVerifyCodeForReset".equals(method)) {
            VerifyCodeParam verifyCodeParam = BeanTools.copyAs(params, VerifyCodeParam.class);
            result = verifyCodeApi.getVerifyCodeForReset(verifyCodeParam);
        } else if ("register".equals(method)) {
            RegisterParam registerParam = BeanTools.copyAs(params, RegisterParam.class);
            result = accountApi.register(registerParam);
        } else if ("login".equals(method)) {
            LoginParam loginParam = BeanTools.copyAs(params, LoginParam.class);
            result = accountApi.login(loginParam);
        } else if ("resetPassword".equals(method)) {
            ResetPasswordParam resetPasswordParam = BeanTools.copyAs(params, ResetPasswordParam.class);
            result = accountApi.resetPassword(resetPasswordParam);
        }
        return result == null ? "{\"message\": \"method name error\", \"result\": {}, \"status\": \"10001\"}" : JSON.toJSONString(result);
    }
}
