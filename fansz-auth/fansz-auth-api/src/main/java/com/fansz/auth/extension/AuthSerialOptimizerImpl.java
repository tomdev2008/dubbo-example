package com.fansz.auth.extension;

import com.alibaba.dubbo.common.serialize.support.SerializationOptimizer;
import com.fansz.auth.model.*;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by allan on 15/12/28.
 */
public class AuthSerialOptimizerImpl implements SerializationOptimizer {
    public Collection<Class> getSerializableClasses() {
        List<Class> classes = new LinkedList<>();
        classes.add(CommonResult.class);
        classes.add(NullResult.class);


        classes.add(ChangePasswordParam.class);
        classes.add(LoginParam.class);
        classes.add(LogoutParam.class);
        classes.add(OssTokenParam.class);
        classes.add(RefreshTokenParam.class);
        classes.add(RegisterParam.class);
        classes.add(ResetPasswordParam.class);
        classes.add(SessionInfoResult.class);
        classes.add(SessionQueryParam.class);
        classes.add(VerifyCodeParam.class);
        classes.add(LoginResult.class);
        return classes;
    }
}
