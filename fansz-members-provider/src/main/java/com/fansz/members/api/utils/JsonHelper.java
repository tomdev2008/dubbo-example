package com.fansz.members.api.utils;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JSON 辅助类
 */
public class JsonHelper {

    private Logger logger = LoggerFactory.getLogger(JsonHelper.class);

    public static String toString(Object obj) {
        return JSON.toJSONString(obj);
    }

}
