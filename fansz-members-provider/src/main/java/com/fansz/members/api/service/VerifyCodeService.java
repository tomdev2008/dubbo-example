package com.fansz.members.api.service;

import com.fansz.members.api.model.VerifyCode;
import com.fansz.members.api.utils.VerifyCodeType;

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
    public boolean createVerifyCode(String mobile, VerifyCodeType verifyCodeType);

    /**
     * 查询验证码
     *
     * @param mobile
     * @param verifyCodeType
     * @return
     */
    VerifyCode queryVerifyCode(String mobile, VerifyCodeType verifyCodeType);

    /**
     * 删除验证码
     *
     * @param mobile
     * @param verifyCodeType
     */
    void removeVerifyCode(String mobile, VerifyCodeType verifyCodeType);

}
