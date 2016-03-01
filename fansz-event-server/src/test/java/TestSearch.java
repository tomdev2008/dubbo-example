import com.fansz.db.entity.FandomPost;
import com.fansz.db.entity.User;
import com.fansz.db.repository.FandomPostDAO;
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by allan on 15/11/30.
 */
public class TestSearch {
    public static void main(String[] args) throws Exception {

        Settings settings = Settings.settingsBuilder().put("cluster.name", "fansz-searcher").put("client.transport.sniff", true).build();
        TransportClient client = TransportClient.builder().settings(settings).build();
        client.addTransportAddresses(new InetSocketTransportAddress(new InetSocketAddress("192.168.88.6", 9300)));


        //deleteIndex(client,"member");
        //searchPost(client, "测试", "V");

        //createUserMapping(client);
        //migrate(client);
        createUser(client);
        //createPost(client);
    }

    private static void createPostMapping(TransportClient client) throws Exception {
        client.admin().indices().prepareCreate("fandom").execute().actionGet();
        XContentBuilder builder = JsonXContent.contentBuilder()
                .startObject()
                .startObject("properties")
                .startObject("id").field("type", "string").field("index", "not_analyzed").endObject()
                .startObject("fandom_id").field("type", "integer").field("index", "not_analyzed").endObject()
                .startObject("member_sn").field("type", "string").field("index", "not_analyzed").endObject()
                .startObject("post_content").field("type", "string").field("analyzer", "ik_smart").endObject()
                .startObject("post_title").field("type", "string").field("analyzer", "ik_smart").endObject()
                .startObject("post_newsfeeds").field("type", "string").field("index", "not_analyzed").endObject()
                .startObject("post_type").field("type", "string").field("index", "not_analyzed").endObject()
                .startObject("post_time").field("type", "string").field("index", "not_analyzed").endObject()
                .startObject("effective_time").field("type", "string").field("index", "not_analyzed").endObject()
                .startObject("comment_count").field("type", "integer").field("index", "not_analyzed").endObject()
                .startObject("vote_count").field("type", "integer").field("index", "not_analyzed").endObject()
                .startObject("like_count").field("type", "integer").field("index", "not_analyzed").endObject()
                .startObject("post_level").field("type", "string").field("index", "not_analyzed").endObject()
                .endObject()
                .endObject();
        PutMappingRequest mapping = Requests.putMappingRequest("fandom").type("post").source(builder);
        client.admin().indices().putMapping(mapping).actionGet();
        client.close();

    }

    private static void createUserMapping(TransportClient client) throws Exception {
        client.admin().indices().prepareCreate("member").execute().actionGet();
        XContentBuilder builder = JsonXContent.contentBuilder()
                .startObject()
                .startObject("properties")
                .startObject("id").field("type", "long").field("index", "not_analyzed").endObject()
                .startObject("sn").field("type", "string").field("index", "not_analyzed").endObject()
                .startObject("loginname").field("type", "string").field("index", "not_analyzed").endObject()
                .startObject("password").field("type", "string").field("index", "not_analyzed").endObject()
                .startObject("mobile").field("type", "string").field("index", "not_analyzed").endObject()
                .startObject("email").field("type", "string").field("index", "not_analyzed").endObject()
                .startObject("nickname").field("type", "string").field("index", "not_analyzed").endObject()
                .startObject("gender").field("type", "string").field("index", "not_analyzed").endObject()
                .startObject("birthday").field("type", "string").field("index", "not_analyzed").endObject()
                .startObject("member_avatar").field("type", "string").field("index", "not_analyzed").endObject()
                .startObject("profile_createtime").field("type", "long").field("index", "not_analyzed").endObject()
                .startObject("profile_updatetime").field("type", "long").field("index", "not_analyzed").endObject()
                .startObject("member_type").field("type", "string").field("index", "not_analyzed").endObject()
                .startObject("member_status").field("type", "string").field("index", "not_analyzed").endObject()
                .startObject("signature").field("type", "string").field("analyzer", "ik_smart").endObject()
                .startObject("country").field("type", "string").field("index", "not_analyzed").endObject()
                .endObject()
                .endObject();
        PutMappingRequest mapping = Requests.putMappingRequest("member").type("user").source(builder);
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

    private static void createPost(TransportClient client) {
        final ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext-event.xml");
        ac.start();
        FandomPostDAO dao = ac.getBean(FandomPostDAO.class);
        List<FandomPost> userList = dao.findAll();
        for (FandomPost p : userList) {
            client.prepareIndex("fandom", "post", String.valueOf(p.getId())).setRefresh(true).setSource(JsonHelper.convertObject2JSONString(p)).setTTL(2000).execute().actionGet();
        }
        client.close();
    }

    private static void deleteIndex(TransportClient client, String index) {
        DeleteIndexResponse response = client.admin().indices().prepareDelete(index).execute().actionGet();
        System.out.println(response.toString());
    }


    private static void searchPost(TransportClient searchClient, String keyWord, String postType) {
        SearchRequestBuilder builder = searchClient.prepareSearch("fandom").setTypes("post").setSearchType(SearchType.DEFAULT).setFrom(0).setSize(10);
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        qb.must(new QueryStringQueryBuilder(postType).field("post_type"));
        qb.should(new MatchQueryBuilder("post_title", keyWord));
        qb.should(new MatchQueryBuilder("post_content", keyWord));
        builder.setQuery(qb);
        SearchResponse response = builder.execute().actionGet();
        SearchHits hits = response.getHits();
        System.out.println("查询到post记录总数=" + hits.getTotalHits());
        SearchHit[] searchHists = hits.getHits();


        Set<String> fandomIdList = new HashSet<>();
        for (SearchHit hit : searchHists) {
            Map<String, Object> props = hit.getSource();
            System.out.println(JsonHelper.convertObject2JSONString(props));
        }
    }
}
