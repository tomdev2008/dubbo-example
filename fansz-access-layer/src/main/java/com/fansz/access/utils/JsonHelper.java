package com.fansz.access.utils;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * Created by allan on 15/12/7.
 */
public class JsonHelper {
    private final static Logger logger = LoggerFactory.getLogger(JsonHelper.class);

    public static String toString(Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper().disable(
                    SerializationConfig.Feature.WRITE_NULL_MAP_VALUES).disable(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS).setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
            return mapper.writeValueAsString(obj);
        } catch (IOException e) {
            logger.error("JSON字符串序列化出错!", e);
        }
        return null;
    }

    public static Map<String, Object> parseObject(String body) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(body, Map.class);
        } catch (IOException e) {
            logger.error("JSON字符串转化为对象出错!", e);
        }
        return null;
    }

    public static <T> T copyAs(Map<String, Object> obj, Class<T> cls) {
        try {
            String body = toString(obj);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(body, cls);
        } catch (Exception e) {
            logger.error("JSON字符串转化为对象出错!", e);
        }
        return null;
    }
}
