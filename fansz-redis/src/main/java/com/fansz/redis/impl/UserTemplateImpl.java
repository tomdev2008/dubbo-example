package com.fansz.redis.impl;

import com.fansz.common.provider.constant.ErrorCode;
import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.pub.utils.CollectionTools;
import com.fansz.pub.utils.DateTools;
import com.fansz.pub.utils.StringTools;
import com.fansz.redis.JedisTemplate;
import com.fansz.redis.UserTemplate;
import com.fansz.redis.support.JedisCallback;
import com.fansz.redis.utils.RedisKeyUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.*;

/**
 * Created by allan on 16/1/8.
 */
public class UserTemplateImpl implements UserTemplate {

    private JedisTemplate jedisTemplate;

    @Override
    public Map<String, String> get(final String sn) {
        Map<String, String> userMap = getWithAuthInfo(sn);
        removePrivacy(userMap);
        return userMap;

    }

    @Override
    public Map<String, String> getWithAuthInfo(final String sn) {
        return jedisTemplate.execute(new JedisCallback<Map<String, String>>() {
            @Override
            public Map<String, String> doInRedis(Jedis jedis) throws Exception {
                Map<String, String> userMap = jedis.hgetAll(RedisKeyUtils.getUserKey(sn));
                return userMap;
            }
        });
    }

    private void removePrivacy(Map<String,String> userMap){
        if (userMap != null) {
            userMap.remove("loginname");
            userMap.remove("password");
            userMap.remove("id");
            userMap.remove("access_token");
            userMap.remove("member_status");
        }
    }
    @Override
    public List<Map<String, String>> getAll(final Collection<String> sns) {
        return jedisTemplate.execute(new JedisCallback<List<Map<String, String>>>() {
            @Override
            public List<Map<String, String>> doInRedis(Jedis jedis) throws Exception {
                List<Map<String, String>> result = new ArrayList<>();
                for (String sn : sns) {
                    Map<String, String> userMap = jedis.hgetAll(RedisKeyUtils.getUserKey(sn));
                    removePrivacy(userMap);
                    if (userMap != null && !userMap.isEmpty()) {
                        result.add(userMap);
                    }
                }
                return result;
            }
        });
    }

    /**
     * 将用户信息保存到redis,目前用户信息在数据库和redis中都保存了一份,后续会统一保存到redis;
     *
     * @param user
     */
    public void addUser(final Map<String, Object> user) {
        final String account = (String) user.get("loginname");
        final String sn = (String) user.get("sn");
        final String mobile = (String) user.get("mobile");
        jedisTemplate.execute(new JedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(Jedis jedis) throws Exception {
                Pipeline pipe = jedis.pipelined();
                String key = RedisKeyUtils.getUserKey((String) user.get("sn"));
                for (Map.Entry<String, Object> prop : user.entrySet()) {
                    if (prop.getValue() != null && !"access_token".equals(prop.getKey())) {
                        pipe.hset(key, prop.getKey(), String.valueOf(prop.getValue()));
                    }
                }
                pipe.hset(RedisKeyUtils.getMobileIdxKey(mobile), mobile, sn);
                pipe.hset(RedisKeyUtils.getAccountIdxKey(account), account, sn);
                pipe.sync();
                return true;
            }
        });
    }

    @Override
    public boolean updateUser(final String sn, final Map<String, Object> props) {
        return jedisTemplate.execute(new JedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(Jedis jedis) throws Exception {
                Pipeline pipe = jedis.pipelined();
                for (String prop : props.keySet()) {
                    Object val = props.get(prop);
                    if (val != null) {
                        pipe.hset(RedisKeyUtils.getUserKey(sn), prop, String.valueOf(props.get(prop)));
                    }
                }
                if (props.containsKey("nickname")) {
                    String nickname = (String) props.get("nickname");
                    if (StringTools.isNotBlank(nickname)) {//更新nickname索引
                        pipe.hset(RedisKeyUtils.getNickIdxKey(nickname), nickname, sn);
                    }
                }
                pipe.sync();
                return true;
            }
        });
    }

    /**
     * 同步修改redis中的密码
     *
     * @param sn
     * @param passwd
     */
    @Override
    public void updatePasswd(final String sn, final String passwd) {
        jedisTemplate.execute(new JedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(Jedis jedis) throws Exception {
                jedis.hset(RedisKeyUtils.getUserKey(sn), "password", passwd);
                return true;
            }
        });

    }

    public Map<String, String> getSession(final String accessToken) {
        return jedisTemplate.execute(new JedisCallback<Map<String, String>>() {
            @Override
            public Map<String, String> doInRedis(Jedis jedis) throws Exception {
                Map<String, String> sessionMap = jedis.hgetAll(RedisKeyUtils.getSessionKey(accessToken));
                return sessionMap;
            }
        });

    }

    @Override
    public Long saveSession(String accessToken, String refreshToken, final Map<String, String> session, final int accessValidPeriod, final int refreshValidPeriod) {
        final String sessionKey = RedisKeyUtils.getSessionKey(accessToken);
        final String refreshKey = RedisKeyUtils.getRefreshKey(refreshToken);

        return jedisTemplate.execute(new JedisCallback<Long>() {
            @Override
            public Long doInRedis(Jedis jedis) throws Exception {
                Date now = DateTools.getSysDate();
                Long aExpire = DateTools.wrapDate(now, "m" + accessValidPeriod).getTime();
                Long rExpire = DateTools.wrapDate(now, "m" + refreshValidPeriod).getTime();

                Pipeline pipe = jedis.pipelined();
                session.put("expiresAt", String.valueOf(aExpire));
                pipe.hmset(sessionKey, session);
                pipe.expire(sessionKey, accessValidPeriod * 60);//默认为5小时,配置文件单位为分钟


                session.put("expiresAt", String.valueOf(rExpire));
                pipe.hmset(refreshKey, session);
                pipe.expire(refreshKey, refreshValidPeriod * 60);//默认为30天,配置文件单位为分钟
                pipe.sync();
                return aExpire;
            }
        });
    }

    @Override
    public void invalidateSession(final String accessToken) throws ApplicationException {
        jedisTemplate.execute(new JedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(Jedis jedis) throws Exception {
                String refreshToken = jedis.hget(RedisKeyUtils.getSessionKey(accessToken), "refreshToken");
                if (StringTools.isNotBlank(refreshToken)) {
                    jedis.del(RedisKeyUtils.getSessionKey(accessToken));
                    jedis.del(RedisKeyUtils.getRefreshKey(refreshToken));
                }
                return true;
            }
        });
    }

    @Override
    public Long refreshToken(final String refreshToken, final String newAccessToken, final int accessValidPeriod) {
        return jedisTemplate.execute(new JedisCallback<Long>() {
            @Override
            public Long doInRedis(Jedis jedis) throws Exception {
                final String refreshKey = RedisKeyUtils.getRefreshKey(refreshToken);
                List<String> uidSn = jedis.hmget(refreshKey, "id", "sn", "accessToken");
                if (CollectionTools.isNullOrEmpty(uidSn) || StringTools.isBlank(uidSn.get(0))) {
                    throw new ApplicationException(ErrorCode.REFRESH_TOKEN_INVALID);
                }

                final Map<String, String> session = new HashMap<>();

                session.put("id", uidSn.get(0));
                session.put("sn", uidSn.get(1));
                session.put("accessToken", newAccessToken);
                session.put("refreshToken", refreshToken);

                Date now = DateTools.getSysDate();
                session.put("lastAccessTime", String.valueOf(now.getTime()));
                Long expire = DateTools.wrapDate(now, "m" + accessValidPeriod).getTime();
                session.put("expiresAt", String.valueOf(expire));
                Pipeline pipe = jedis.pipelined();
                if (uidSn.size() > 2) {
                    pipe.del(RedisKeyUtils.getSessionKey(uidSn.get(2)));//删除之前的accessToken
                }
                pipe.hmset(RedisKeyUtils.getSessionKey(newAccessToken), session);
                pipe.hset(refreshKey, "accessToken", newAccessToken);
                pipe.sync();

                return expire;
            }
        });
    }

    @Override
    public Map<String, String> queryVerifyCode(final String mobile) {
        return jedisTemplate.execute(new JedisCallback<Map<String, String>>() {
            @Override
            public Map<String, String> doInRedis(Jedis jedis) throws Exception {
                return jedis.hgetAll(RedisKeyUtils.getVerifyKey(mobile));
            }
        });
    }

    @Override
    public void removeVerifyCode(final String mobile, final String verifyCodeName) {
        jedisTemplate.execute(new JedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(Jedis jedis) throws Exception {
                jedis.hdel(RedisKeyUtils.getVerifyKey(mobile), verifyCodeName + ".verifyCode", verifyCodeName + ".createTime", verifyCodeName + ".expiredTime");
                return true;
            }
        });

    }

    @Override
    public String getVerifyCodeCreateTime(final String mobile, final String verifyCodeName) {
        return jedisTemplate.execute(new JedisCallback<String>() {
            @Override
            public String doInRedis(Jedis jedis) throws Exception {
                return jedis.hget(RedisKeyUtils.getVerifyKey(mobile), verifyCodeName + ".createTime");
            }
        });

    }

    @Override
    public Boolean saveVerifyCode(final String mobile, final Map<String, String> verifyMap) {
        return jedisTemplate.execute(new JedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(Jedis jedis) throws Exception {
                jedis.hmset(RedisKeyUtils.getVerifyKey(mobile), verifyMap);
                return true;
            }
        });
    }

    /**
     * 根据账号,查询用户的Sn
     *
     * @param account
     * @return
     */
    public String getSnByAccount(final String account) {
        return jedisTemplate.execute(new JedisCallback<String>() {
            @Override
            public String doInRedis(Jedis jedis) throws Exception {
                return jedis.hget(RedisKeyUtils.getAccountIdxKey(account), account);
            }
        });
    }

    /**
     * 根据手机号码,查询用户的Sn
     *
     * @param mobile
     * @return
     */
    public String getSnByMobile(final String mobile) {
        return jedisTemplate.execute(new JedisCallback<String>() {
            @Override
            public String doInRedis(Jedis jedis) throws Exception {
                return jedis.hget(RedisKeyUtils.getMobileIdxKey(mobile), mobile);
            }
        });
    }

    @Override
    public String getSnByNickname(final String nickname) {
        return jedisTemplate.execute(new JedisCallback<String>() {
            @Override
            public String doInRedis(Jedis jedis) throws Exception {
                return jedis.hget(RedisKeyUtils.getNickIdxKey(nickname), nickname);
            }
        });
    }

    /**
     * 获取下一个可以用的用户ID
     *
     * @return
     */
    public Long generateUserId() {
        return jedisTemplate.execute(new JedisCallback<Long>() {
            @Override
            public Long doInRedis(Jedis jedis) throws Exception {
                return jedis.incr(RedisKeyUtils.getUserSequence());
            }
        });
    }

    public void setJedisTemplate(JedisTemplate jedisTemplate) {
        this.jedisTemplate = jedisTemplate;
    }
}
