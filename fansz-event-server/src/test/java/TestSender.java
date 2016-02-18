import com.fansz.db.entity.User;
import com.fansz.db.repository.UserDAO;
import com.fansz.pub.utils.CollectionTools;
import com.fansz.pub.utils.JsonHelper;
import com.fansz.pub.utils.StringTools;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.elasticsearch.index.VersionType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by allan on 15/11/30.
 */
public class TestSender {
    public static void main(String[] args) throws Exception {

        Settings settings = Settings.settingsBuilder().put("cluster.name", "fansz-searcher").put("client.transport.sniff", true).build();
        TransportClient client = TransportClient.builder().settings(settings).build();
        client.addTransportAddresses(new InetSocketTransportAddress(new InetSocketAddress("192.168.88.6", 9300)));


        SearchRequestBuilder builder = client.prepareSearch("fandom").setTypes("post").setSearchType(SearchType.DEFAULT).setFrom(0).setSize(100);
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        //qb.should(new QueryStringQueryBuilder("1").field("post_title").field("post_content"));
        //qb.should(new QueryStringQueryBuilder("18621614455").field("loginname"));
        //qb.should(new QueryStringQueryBuilder("18621614455").field("nickname"));
        //builder.setQuery(qb);
        qb.should(new MatchQueryBuilder("post_title", "测试投票"));
        //qb.should(new MatchQueryBuilder("post_content", "关键字"));
        builder.setQuery(qb);
        SearchResponse response = builder.execute().actionGet();
        SearchHits hits = response.getHits();
        System.out.println("查询到记录数=" + hits.getTotalHits());
        SearchHit[] searchHists = hits.getHits();
        System.out.println("返回记录数=" + searchHists.length);
        if (searchHists.length > 0) {
            for (SearchHit hit : searchHists) {
                Map<String, Object> props = hit.getSource();
                System.out.println(JsonHelper.convertObject2JSONString(props));
            }
        }

        //createMapping(client, "fandom", "post");
        // migrate(client);
        //createUser(client);
    }

    private static void createMapping(TransportClient client, String indices, String mappingType) throws Exception {
        client.admin().indices().prepareCreate("fandom").execute().actionGet();
        XContentBuilder builder = JsonXContent.contentBuilder()
                .startObject()
                .startObject("properties")
                .startObject("id").field("type", "string").endObject()
                .startObject("fandom_id").field("type", "integer").endObject()
                .startObject("member_sn").field("type", "string").endObject()
                .startObject("post_content").field("type", "string").field("analyzer", "ik_smart").endObject()
                .startObject("post_title").field("type", "string").field("analyzer", "ik_smart").endObject()
                .startObject("post_newsfeeds").field("type", "string").endObject()
                .startObject("post_type").field("type", "string").endObject()
                .startObject("post_time").field("type", "string").endObject()
                .startObject("effective_time").field("type", "string").endObject()
                .startObject("comment_count").field("type", "integer").endObject()
                .startObject("vote_count").field("type", "integer").endObject()
                .startObject("like_count").field("type", "integer").endObject()
                .endObject()
                .endObject();
        PutMappingRequest mapping = Requests.putMappingRequest(indices).type(mappingType).source(builder);
        client.admin().indices().putMapping(mapping).actionGet();
        client.close();

    }

    private static void migrate(TransportClient client) throws Exception {
        BulkProcessor bulkProcessor = BulkProcessor.builder(client, new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long executionId, BulkRequest request) {

            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {

            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, Throwable failure) {

            }
        }).setBulkActions(100)
                .setBulkSize(new ByteSizeValue(20, ByteSizeUnit.MB))
                .setFlushInterval(TimeValue.timeValueSeconds(5))
                .setConcurrentRequests(2)
                .build();
        boolean finish = false;
        int index = 0;
        while (!finish) {
            SearchResponse sr = client.prepareSearch("fansz").setFrom(index).setSize(100).execute().actionGet();
            SearchHit[] hits = sr.getHits().getHits();
            for (SearchHit hit : hits) {
                Map<String, Object> rowMap = hit.sourceAsMap();
                String id = String.valueOf(rowMap.get("id"));
                bulkProcessor.add(new IndexRequest("fandom", "post", id).source(rowMap));
            }

            if (hits.length < 100) {
                finish = true;
            }
            index += 100;
        }
        bulkProcessor.awaitClose(30L, TimeUnit.SECONDS);
        client.close();
    }

    private static void createUser(TransportClient client) {
        final ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext-event.xml");
        ac.start();
        UserDAO dao = ac.getBean(UserDAO.class);
        List<User> userList = dao.findAll();
        for (User u : userList) {
            client.prepareIndex("member", "user", u.getSn()).setRefresh(true).setSource(JsonHelper.convertObject2JSONString(u)).setTTL(2000).execute().actionGet();
        }
        client.close();
    }

    private static void deleteIndex(TransportClient client, String index) {
        DeleteIndexResponse response = client.admin().indices().prepareDelete(index).execute().actionGet();
        System.out.println(response.toString());
    }


}

