package com.fansz.redis;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by allan on 16/1/8.
 */
public interface UserTemplate {

    Map<String,String> get(String sn);

    List<Map<String, String>>  getAll(Collection<String> sns);

    void updatePasswd(final String sn, final String passwd);

    void addUser(final Map<String, Object> user);

    boolean updateUser(final String sn,Map<String,Object> props);


    Map<String,String> getSession(final String accessToken);

    Long saveSession(String accessToken, String refreshToken, final Map<String,String> session,final int accessValidPeriod,final int refreshValidPeriod);

    void invalidateSession(final String accessToken);

    Long refreshToken(final String refreshToken,final String newAccessToken,final int accessValidPeriod);

    Map<String, String> queryVerifyCode(final String mobile);

    void removeVerifyCode(final String mobile, final String verifyCodeName);

    Boolean saveVerifyCode(final String mobile, final Map<String, String> verifyMap);

    String getVerifyCodeCreateTime(final String mobile, final String verifyCodeName);

    String getSnByAccount(final String account);

    String getSnByMobile(final String mobile);

    String getSnByNickname(final String nickname);

    Long generateUserId();
}
