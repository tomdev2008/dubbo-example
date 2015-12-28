package com.fansz.auth.extension;

import com.alibaba.dubbo.common.serialize.support.SerializationOptimizer;
import com.fansz.auth.model.LoginResult;
import com.fansz.auth.model.SessionInfoResult;
import com.fansz.common.provider.model.CommonResult;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by allan on 15/12/28.
 */
public class AuthSerialOptimizerImpl implements SerializationOptimizer {
    public Collection<Class> getSerializableClasses() {
        List<Class> classes = new LinkedList<Class>();
        classes.add(CommonResult.class);
        classes.add(LoginResult.class);
        classes.add(SessionInfoResult.class);
        return classes;
    }
}
