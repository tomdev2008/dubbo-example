package com.fansz.newsfeeds.extension;

import com.alibaba.dubbo.common.serialize.support.SerializationOptimizer;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.newsfeeds.model.post.GetPostByIdParam;
import com.fansz.newsfeeds.model.post.PostInfoResult;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by allan on 15/12/28.
 */
public class FeedsSerialOptimizerImpl implements SerializationOptimizer {
    public Collection<Class> getSerializableClasses() {
        List<Class> classes = new LinkedList<>();
        classes.add(CommonResult.class);
        classes.add(GetPostByIdParam.class);
        classes.add(PostInfoResult.class);
        return classes;
    }
}