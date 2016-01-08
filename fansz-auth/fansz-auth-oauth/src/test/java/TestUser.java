import com.fansz.db.entity.User;
import com.fansz.db.repository.UserDAO;
import com.fansz.pub.utils.JsonHelper;
import com.fansz.redis.JedisTemplate;
import com.fansz.redis.support.JedisCallback;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.Map;

/**
 * Created by allan on 16/1/7.
 */
public class TestUser {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext-auth.xml");
        ac.start();
        UserDAO userDAO=ac.getBean(UserDAO.class);
        final User user=userDAO.findBySn("0qq6xruSV038hE6PxqdevU");

        JedisTemplate jedisTemplate=ac.getBean(JedisTemplate.class);
        final Map<String,Object> userMap= JsonHelper.convertJSONString2Object(JsonHelper.convertObject2JSONString(user),Map.class);
        jedisTemplate.execute(new JedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(Jedis jedis) throws Exception {
                Pipeline pipe = jedis.pipelined();
                String key = "user:{" + user.getSn() + "}";
                for (Map.Entry<String, Object> prop : userMap.entrySet()) {
                    pipe.hset(key, prop.getKey(), String.valueOf(prop.getValue()));
                }
                pipe.sync();
                return true;
            }
        });
    }
}
