import com.fansz.db.entity.User;
import com.fansz.db.repository.UserDAO;
import com.fansz.pub.utils.CollectionTools;
import com.fansz.pub.utils.JsonHelper;
import com.fansz.pub.utils.StringTools;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;

/**
 * Created by allan on 15/11/30.
 */
public class TestSender {
    public static void main(String[] args) throws Exception {
      /*  final ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext-event.xml");
        ac.start();
        UserDAO dao = ac.getBean(UserDAO.class);
        List<User> userList = dao.findAll();*/
        Settings settings = Settings.settingsBuilder().put("cluster.name", "fansz-searcher").put("client.transport.sniff", true).build();
        TransportClient client = TransportClient.builder().settings(settings).build();
        client.addTransportAddresses(new InetSocketTransportAddress(new InetSocketAddress("192.168.88.7", 9300)));

      /*  for (User u : userList) {
            client.prepareIndex("fansz", "user", u.getSn()).setRefresh(true).setSource(JsonHelper.convertObject2JSONString(u)).setTTL(2000).execute().actionGet();
        }*/

        /**
         *

         User u = new User();
         u.setSn("0Ymx_T2594ebpqRP_hjhSQ");
         u.setNickname("18621614455");
         String content = JsonHelper.convertObject2JSONString(u);
         client.prepareUpdate("fansz", "user", u.getSn()).setUpsert(content).setDoc(content).setTtl(2000L).execute();
         */


        SearchRequestBuilder builder = client.prepareSearch("fansz").setTypes("user").setSearchType(SearchType.DEFAULT).setFrom(0).setSize(100);
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        qb.should(new QueryStringQueryBuilder("18621614455").field("mobile").field("loginname").field("nickname"));
        //qb.should(new QueryStringQueryBuilder("18621614455").field("loginname"));
        //qb.should(new QueryStringQueryBuilder("18621614455").field("nickname"));
        builder.setQuery(qb);
        SearchResponse response = builder.execute().actionGet();
        SearchHits hits = response.getHits();
        System.out.println("查询到记录数=" + hits.getTotalHits());
        SearchHit[] searchHists = hits.getHits();
        if (searchHists.length > 0) {
            for (SearchHit hit : searchHists) {
                Map<String, Object> props = hit.getSource();
                System.out.println(JsonHelper.convertObject2JSONString(props));
            }
        }
    }
}

