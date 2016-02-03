package com.fansz.event.consumer.support;

import com.alibaba.fastjson.JSON;
import com.fansz.event.model.AddCommentEvent;
import com.fansz.event.type.AsyncEventType;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Map;

/**
 * 用户事件消费者,处理包括用户新增,修改等
 */
@Component("userConsumer")
public class UserConsumer implements IEventConsumer {
    private Logger logger = LoggerFactory.getLogger(UserConsumer.class);

    @Resource(name = "searchClient")
    private Client searchClient;

    @Override
    public void onEvent(ConsumerRecord<String, String> record) {
        try {
            createIndex(record.key(), record.value());
        } catch (Exception e) {
            logger.error(String.format("error to index user(%s)!", record.value()), e);
        }

    }

    @Override
    public AsyncEventType getEventType() {
        return AsyncEventType.USER;
    }

    private void createIndex(String sn, String content) throws Exception {
        //IndexResponse response = searchClient.prepareIndex("fansz", "user", sn).setRefresh(true).setSource(content).setTTL(2000).execute().actionGet();
        //logger.info("Response id is {}", response.getId());
        searchClient.prepareUpdate("fansz", "user", sn).setUpsert(content).setDoc(content).setTtl(2000L).execute();
    }
}
