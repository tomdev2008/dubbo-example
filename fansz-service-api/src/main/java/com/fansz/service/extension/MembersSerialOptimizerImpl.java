package com.fansz.service.extension;

import com.alibaba.dubbo.common.serialize.support.SerializationOptimizer;
import com.fansz.common.provider.model.CommonPagedResult;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.service.model.account.LoginResult;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by allan on 15/11/23.
 */
public class MembersSerialOptimizerImpl implements SerializationOptimizer {
    public Collection<Class> getSerializableClasses() {
        List<Class> classes = new LinkedList<>();
        classes.add(CommonResult.class);
        classes.add(CommonPagedResult.class);
        return classes;
    }
}

