package com.fansz.members.api.service;

import com.fansz.members.api.model.VerifyCodeModel;
import com.fansz.members.tools.VerifyCodeType;

/**
 * Created by allan on 15/11/26.
 */
public interface VerifyCodeService {
    /**
     * 获取新的验证码
     *
     * @param mobile         手机号码
     * @param verifyCodeType 验证码类型
     */
    boolean createVerifyCode(String mobile, VerifyCodeType verifyCodeType);

    /**
     * 查询验证码
     *
     * @param mobile
     * @param verifyCodeType
     * @return
     */
    VerifyCodeModel queryVerifyCode(String mobile, VerifyCodeType verifyCodeType);

    /**
     * 删除验证码
     *
     * @param mobile
     * @param verifyCodeType
     */
    void removeVerifyCode(String mobile, VerifyCodeType verifyCodeType);

    /**
     * 验证码是否有效
     *
     * @param verifyCodeEntity
     * @return
     */
    boolean isValid(VerifyCodeModel verifyCodeEntity);

}