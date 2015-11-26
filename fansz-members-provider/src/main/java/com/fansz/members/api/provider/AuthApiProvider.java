package com.fansz.members.api.provider;

import com.fansz.members.api.AuthApi;
import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.api.repository.UserRepository;
import com.fansz.members.model.*;
import com.fansz.members.tools.DateTools;
import com.fansz.members.tools.MembersConstant;
import com.fansz.members.tools.SecurityTools;
import com.fansz.members.tools.UUIDTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by allan on 15/11/20.
 * 临时版本,后续部分逻辑可以进行拆分,避免provider太重
 */
@Component("loginApiProvider")
public class AuthApiProvider implements AuthApi {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserRepository userRepository;

    public CommonResult<LoginResult> login(UserParameters user) {
        CommonResult apiResult = new CommonResult("0", "登录失败");
        UserEntity entity = userRepository.findByAccount(user.getUserAccount());
        String encodedPwd = SecurityTools.encode(user.getPassword());
        if (entity != null && entity.getPasswd().equals(encodedPwd)) {
            apiResult.setStatus("1");
            apiResult.setMessage("登录成功");
            LoginResult loginResult = new LoginResult();
            loginResult.setAccessToken(UUIDTools.getUniqueId());
            loginResult.setRefreshToken(UUIDTools.getUniqueId());
            Date expiresAt = DateTools.wrapDate(new Date(), "m+" + MembersConstant.EXPIRED_PERIOD);
            loginResult.setExpiresAt(expiresAt.getTime());
            loginResult.setUid(entity.getUserId());
            apiResult.setResult(loginResult);


            String key = "session:" + entity.getUserId();
            Map<String, String> session = new HashMap<String, String>();
            session.put("lastAccessTime", String.valueOf(System.currentTimeMillis()));
            session.put("accessToken", loginResult.getAccessToken());
            session.put("refreshToken", loginResult.getRefreshToken());
            session.put("expiredAt", String.valueOf(loginResult.getExpiresAt()));
            redisTemplate.boundHashOps(key).putAll(session);
        }
        return apiResult;
    }

    @Override
    public CommonResult<RefreshResult> refreshToken(RefreshParameters refreshParameters) {
        CommonResult apiResult = new CommonResult("0", "刷新失败");
        String key = "session:" + refreshParameters.getUserId();
        String refreshToken = (String) redisTemplate.boundHashOps(key).get("refreshToken");
        if (refreshParameters.getRefreshToken().equals(refreshToken)) {
            apiResult.setStatus("1");
            apiResult.setMessage("刷新成功");

            RefreshResult refreshResult = new RefreshResult();
            refreshResult.setAccessToken(UUIDTools.getUniqueId());
            Date expiresAt = DateTools.wrapDate(new Date(), "m+" + MembersConstant.EXPIRED_PERIOD);
            refreshResult.setExpiresAt(expiresAt.getTime());


            Map<String, String> session = new HashMap<String, String>();
            session.put("lastAccessTime", String.valueOf(System.currentTimeMillis()));
            session.put("accessToken", refreshResult.getAccessToken());
            session.put("expiredAt", String.valueOf(refreshResult.getExpiresAt()));
            redisTemplate.boundHashOps(key).putAll(session);
        }
        return apiResult;
    }
}
