package com.fansz.access.rpc;

import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fansz.access.utils.ConsumerConstants;
import com.fansz.access.utils.ResponseUtils;
import com.fansz.common.provider.annotation.DubboxMethod;
import com.fansz.common.provider.annotation.DubboxService;
import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.common.provider.model.AccessTokenAware;
import com.fansz.fandom.api.AccountApi;
import com.fansz.fandom.model.session.SessionInfoResult;
import com.fansz.pub.utils.JsonHelper;
import com.fansz.pub.utils.StringTools;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.validation.ValidationException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component("dynaDubboInvoker")
public class DynaDubboInvoker implements RpcInvoker {

    private final static String BASE_PACKAGE = "com.fansz.service.api";

    private Logger logger = LoggerFactory.getLogger(DynaDubboInvoker.class);

    private static Map<String, Method> methodMap = new ConcurrentHashMap<>();

    @Autowired
    private ApplicationContext applicationContext;

    @Resource(name = "accountProvider")
    private AccountApi accountApi;


    @PostConstruct
    private void init() throws Exception {
        Reflections reflections = new Reflections(BASE_PACKAGE);
        Set<Class<?>> clsSet = reflections.getTypesAnnotatedWith(DubboxService.class);
        for (Class<?> cls : clsSet) {
            Method[] methods = cls.getDeclaredMethods();
            for (Method method : methods) {
                DubboxMethod DubboxMethod = method.getAnnotation(DubboxMethod.class);
                if (DubboxMethod != null) {
                    if (methodMap.get(DubboxMethod.value()) != null) {
                        logger.error("Api method name repeated:{}", DubboxMethod.value());
                    }
                    methodMap.put(DubboxMethod.value(), method);
                } else {
                    logger.warn("Api without DubboxMethod annotation,class-{},method-{}", cls.getCanonicalName(), method.getName());
                }
            }
        }
    }

    public String invoke(String url, String requestBody) {
        Map<String, Object> reqMap = parseParameter(requestBody);
        String header = JSON.toJSONString(reqMap.get(ConsumerConstants.HTTP_HEADER));
        JSONArray reqArray = (JSONArray) reqMap.get(ConsumerConstants.HTTP_REQUEST);

        List<String> responseList = mergeRequest(reqArray);
        String response = StringTools.join(responseList, ",");
        return String.format(ConsumerConstants.HTTP_RESPONSE, header, response);
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
            String method = (String) req.get("method");
            JSONObject params = req.getJSONObject("params");
            String response = ResponseUtils.renderAppError();
            try {
                response = invokeRpc(method, params);
            } catch (Exception e) {
                if (e.getCause() != null && e.getCause() instanceof ApplicationException) {
                    logger.error("调用时逻辑错误,抛出异常!", e);
                    ApplicationException ae = (ApplicationException) e.getCause();
                    response = ResponseUtils.renderLogicError(ae.getCode(), ae.getMessage());
                } else if (e.getCause() != null && e.getCause() instanceof RpcException) {
                    RpcException re = (RpcException) e.getCause();
                    if (re != null && re.getCause() instanceof ValidationException) {
                        response = ResponseUtils.renderParamError();
                    }
                } else {
                    logger.error("调用RPC服务出错!", e);
                    response = ResponseUtils.renderAppError();
                }
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
    private String invokeRpc(String method, JSONObject params) throws Exception {
        Method m = methodMap.get(method);
        if (m == null) {
            return ResponseUtils.renderMethodNameError();
        }

        Class[] args = m.getParameterTypes();
        Object[] values = new Object[args.length];
        values[0] = JsonHelper.copyAs(params, args[0]);
        if (values[0] instanceof AccessTokenAware) {
            AccessTokenAware at = (AccessTokenAware) values[0];
            String accessToken = at.getAccessToken();
            if (StringTools.isBlank(accessToken)) {
                return ResponseUtils.renderAccessTokenError();//accessToken不能为空
            }

            SessionInfoResult session = accountApi.getSession(accessToken);
            if (!isValid(session)) {
                return ResponseUtils.renderAccessTokenError();//accessToken不能为空
            }
            at.setCurrentSn(session.getSn());
        }

        Object result = m.invoke(applicationContext.getBean(m.getDeclaringClass()), values);

        return result == null ? ResponseUtils.renderMethodNameError() : JsonHelper.convertObject2JSONString(result);
    }

    private boolean isValid(SessionInfoResult session) {
        if (session != null) return true;
        return false;
    }
}
