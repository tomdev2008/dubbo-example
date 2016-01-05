package com.fansz.auth.service.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.fansz.auth.service.OssService;
import com.fansz.common.provider.constant.ErrorCode;
import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.pub.utils.FileTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by allan on 16/1/4.
 */
@Component
public class OssServiceImpl implements OssService {

    @Resource(name = "applicationProps")
    private Properties applicationProps;

    @Resource(name = "policyResource")
    private ClassPathResource policyResource;

    private final Logger logger = LoggerFactory.getLogger(OssServiceImpl.class);

    @Autowired
    private ApplicationContext applicationContext;

    public Map<String, String> getToken() {
        // RoleSessionName 是临时Token的会话名称，自己指定用于标识你的用户，主要用于审计，或者用于区分Token颁发给谁
        // 但是注意RoleSessionName的长度和规则，不要有空格，只能有'-' '_' 字母和数字等字符
        // 具体规则请参考API文档中的格式要求
        String roleSessionName = "external-username";
        try {
            // 创建一个 Aliyun Acs Client, 用于发起 OpenAPI 请求
            String accessKeyId = applicationProps.getProperty("accessKey.id");
            String accessKeySecret = applicationProps.getProperty("accessKey.secret");
            String roleArn = applicationProps.getProperty("role.arn");
            long durationSeconds = Long.valueOf(applicationProps.getProperty("token.expireTime"));
            // 目前只有"cn-hangzhou"这个region可用, 不要使用填写其他region的值
            String region = applicationProps.getProperty("sts.region", "cn-hangzhou");
            IClientProfile profile = DefaultProfile.getProfile(region, accessKeyId, accessKeySecret);
            DefaultAcsClient client = new DefaultAcsClient(profile);

            // 创建一个 AssumeRoleRequest 并设置请求参数
            final AssumeRoleRequest request = new AssumeRoleRequest();
            request.setVersion(applicationProps.getProperty("sts.version", "2015-04-01"));
            request.setMethod(MethodType.POST);
            request.setProtocol(ProtocolType.HTTPS);//// 此处必须为 HTTPS

            request.setRoleArn(roleArn);
            request.setRoleSessionName(roleSessionName);
            String policy = FileTools.readStringFromStream(policyResource.getInputStream());
            request.setPolicy(policy);
            request.setDurationSeconds(durationSeconds);

            // 发起请求，并得到response
            final AssumeRoleResponse response = client.getAcsResponse(request);

            Map<String, String> respMap = new LinkedHashMap<>();
            respMap.put("access_key_id", response.getCredentials().getAccessKeyId());
            respMap.put("access_key_secret", response.getCredentials().getAccessKeySecret());
            respMap.put("security_token", response.getCredentials().getSecurityToken());
            respMap.put("expiration", response.getCredentials().getExpiration());
            return respMap;
        } catch (ClientException e) {
            logger.error("aliyun sts return error-%s:%s", e.getErrCode(), e.getErrMsg());
            throw new ApplicationException(e.getErrCode(), e.getErrMsg());
        } catch (Exception e) {
            logger.error("call aliyun  encounter error", e);
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR);
        }
    }
}
