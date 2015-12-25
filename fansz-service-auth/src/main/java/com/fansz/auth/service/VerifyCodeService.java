package com.fansz.auth.service;


import com.fansz.auth.model.VerifyCodeModel;
import com.fansz.auth.model.VerifyCodeType;
import com.fansz.service.constant.ErrorCode;

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
    ErrorCode createVerifyCode(String mobile, VerifyCodeType verifyCodeType);

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
