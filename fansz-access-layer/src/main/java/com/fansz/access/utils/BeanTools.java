package com.fansz.access.utils;

import java.util.Map;

/**
 * Created by allan on 15/12/5.
 */
public final class BeanTools {

    public static  <T> T copyAs(Map<String, String> params, Class<T> cls) {
        try {
            T instance = cls.newInstance();
            org.apache.commons.beanutils.BeanUtils.populate(instance, params);
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static  <T> T copyAs(Object obj, Class<T> cls) {
        try {
            T instance = cls.newInstance();
            org.apache.commons.beanutils.BeanUtils.copyProperties(instance, obj);
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
