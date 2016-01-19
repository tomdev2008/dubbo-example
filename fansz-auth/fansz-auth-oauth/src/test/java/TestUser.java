import com.fansz.db.entity.User;
import com.fansz.db.repository.UserDAO;
import com.fansz.pub.utils.JsonHelper;
import com.fansz.redis.JedisTemplate;
import com.fansz.redis.UserTemplate;
import com.fansz.redis.support.JedisCallback;
import com.fansz.redis.utils.RedisKeyUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.Date;
import java.util.Map;

/**
 * Created by allan on 16/1/7.
 */
public class TestUser {
    public static void main(String[] args) {
      /*  ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext-auth.xml");
        ac.start();
        UserDAO userDAO=ac.getBean(UserDAO.class);
        final User user=userDAO.findBySn("0czggPg0F7zau-sE5EJSrl");

        UserTemplate userTemplate=ac.getBean(UserTemplate.class);
        final Map<String,Object> userMap= JsonHelper.convertJSONString2Object(JsonHelper.convertObject2JSONString(user),Map.class);

        userTemplate.addUser(userMap);*/
        //41558646531456744
         Date d=new Date(1452838499677L);
        System.out.println(d);
    }
}
