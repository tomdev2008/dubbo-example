package com.fansz.members.consumer.rpc;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.fansz.members.consumer.utils.JsonHelper;
import com.fansz.members.extension.DubboxService;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ws.rs.Path;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component("dynamicDubboInvoker")
public class DynamicDubboInvoker implements RpcInvoker {
    private Logger logger = LoggerFactory.getLogger(DubboInvoker.class);

    private static Map<String, Method> methodMap = new ConcurrentHashMap<>();

    @Autowired
    private ApplicationContext applicationContext;

    public DynamicDubboInvoker() {

    }

    @PostConstruct
    private void init() throws Exception {
        Reflections reflections = new Reflections("com.fansz.members.api");
        Set<Class<?>> clsSet = reflections.getTypesAnnotatedWith(Path.class);
        for (Class<?> cls : clsSet) {
            Method[] methods = cls.getDeclaredMethods();
            for (Method method : methods) {
                DubboxService dubboxService = method.getAnnotation(DubboxService.class);
                if (dubboxService != null) {
                    if(methodMap.get(dubboxService.value())!=null){
                        logger.error("Api method name repeated:{}",dubboxService.value());
                    }
                    methodMap.put(dubboxService.value(), method);
                } else {
                    logger.warn("Api without DubboxService annotation,class-{},method-{}", cls.getCanonicalName(),method.getName());
                }
            }
        }
    }

    public String invoke(String url, String requestBody) {
        Map<String, Object> reqMap = parseParameter(requestBody);
        String header = JsonHelper.toString(reqMap.get("header"));
        List reqArray = (List) reqMap.get("request");

        List<String> responseList = mergeRequest(reqArray);
        String response = StringUtils.join(responseList, ",");
        return String.format("{\"header\":%s,\"response\": [%s]}", header, response);
    }

    private Map<String, Object> parseParameter(String requestBody) {
        Map<String, Object> reqMap = JsonHelper.parseObject(requestBody);
        return reqMap;
    }

    /**
     * 合并多个请求
     *
     * @param reqArray
     * @return
     */
    private List<String> mergeRequest(List<Map<String, Object>> reqArray) {
        List<String> responseList = new ArrayList<>();
        for (int i = 0; i < reqArray.size(); i++) {
            Map<String, Object> req = reqArray.get(i);
            String method = (String) req.get("method");
            Map<String, Object> params = (Map<String, Object>) req.get("params");
            String response = "";
            try {
                response = invokeRpc(method, params);
            } catch (Exception e) {
                logger.error("调用RPC服务出错!", e);
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
        Method m = methodMap.get(method);
        Class[] args = m.getParameterTypes();
        Object[] values = new Object[args.length];
        values[0] = JsonHelper.copyAs(params, args[0]);
        Object result = null;
        try {
            result = m.invoke(applicationContext.getBean(m.getDeclaringClass()), values);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
        return result == null ? "{\"message\": \"method name error\", \"result\": {}, \"status\": \"10001\"}" : JsonHelper.toString(result);
    }
}
