import com.fansz.auth.api.AccountApi;
import com.fansz.auth.model.SessionInfoResult;
import com.fansz.auth.model.SessionQueryParam;
import com.fansz.newsfeeds.api.NewsfeedsPostApi;
import com.fansz.newsfeeds.model.post.GetPostByIdParam;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by allan on 15/12/30.
 */
public class TestNewsfeeds {
    public static void main(String[] args){
        ClassPathXmlApplicationContext cac=new ClassPathXmlApplicationContext("spring-test.xml");
        AccountApi accountApi=cac.getBean(AccountApi.class);
        SessionQueryParam param = new SessionQueryParam();
        param.setAccessToken("2HRWvLhid24GghS1D9w8z9");
        SessionInfoResult result=accountApi.getSession(param);
        System.out.println(result);


        NewsfeedsPostApi a= cac.getBean(NewsfeedsPostApi.class);
        GetPostByIdParam postParam=new GetPostByIdParam();
        postParam.setPostId(1L);
        postParam.setCurrentSn("444");
        Object b=a.getPost(postParam);
        System.out.println(b);

        postParam.setPostId(3L);
        postParam.setCurrentSn("444");
        b=a.getPost(postParam);
        System.out.println(b);


    }
}