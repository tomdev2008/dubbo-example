package com.fansz.redis.impl;

import com.fansz.pub.utils.CollectionTools;
import com.fansz.redis.CommonsTemplate;
import com.fansz.redis.JedisTemplate;
import com.fansz.redis.support.JedisCallback;
import com.fansz.redis.utils.RedisKeyUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by allan on 16/1/11.
 */
public class CommonsTemplateImpl implements CommonsTemplate {

    private JedisTemplate jedisTemplate;

    @Override
    public List<Map<String, Object>> getFandomCategory() {
        List<Map<String, String>> categoryList = jedisTemplate.execute(new JedisCallback<List<Map<String, String>>>() {
            @Override
            public List<Map<String, String>> doInRedis(Jedis jedis) throws Exception {
                List<Map<String, String>> categoryList = new ArrayList<>();
                String key = RedisKeyUtils.getCategoryListKey();
                Long size = jedis.llen(key);
                if (size > 0) {
                    List<String> categoryIds = jedis.lrange(key, 0, size - 1);
                    for (String categoryId : categoryIds) {
                        Map<String, String> categoryMap = jedis.hgetAll(RedisKeyUtils.getCategoryKey(categoryId));
                        categoryList.add(categoryMap);
                    }
                }
                return categoryList;
            }
        });

        Map<String, Map<String, Object>> rootCategoryMap = new HashMap<>();
        for (Map<String, String> category : categoryList) {
            String fandomParentId = category.get("fandom_parent_id");
            if ("0".equals(fandomParentId)) {//一级目录
                Map<String, Object> rootCategory = rootCategoryMap.get(category.get("id"));
                if (rootCategory == null) {
                    rootCategory = new HashMap<>();
                    rootCategory.put("child", new ArrayList<Map<String, String>>());
                }
                rootCategory.putAll(category);
                rootCategory.put(category.get("id"), rootCategory);
            } else {//二级目录
                if (!rootCategoryMap.containsKey(fandomParentId)) {
                    Map<String, Object> parentCategory = new HashMap<>();
                    parentCategory.put("child", new ArrayList<Map<String, String>>());
                    rootCategoryMap.put(fandomParentId, parentCategory);
                }
                List<Map<String, Object>> childList = (List<Map<String, Object>>) rootCategoryMap.get(fandomParentId).get("child");
                Map<String, Object> childCategory = new HashMap<>();
                childCategory.putAll(category);
                childList.add(childCategory);
            }

        }

        return new ArrayList<>(rootCategoryMap.values());
    }

    @Override
    public void saveCategory(final List<Map<String, Object>> categoryList) {
        if (!CollectionTools.isNullOrEmpty(categoryList)) {
            jedisTemplate.execute(new JedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(Jedis jedis) throws Exception {
                    Pipeline pipe = jedis.pipelined();
                    for (Map<String, Object> category : categoryList) {
                        String categoryId = (String) category.get("id");
                        pipe.lpush(RedisKeyUtils.getCategoryListKey(), categoryId);
                        for (String key : category.keySet()) {
                            pipe.hset(RedisKeyUtils.getCategoryKey(categoryId), key, String.valueOf(category));
                        }
                    }
                    pipe.sync();
                    return true;
                }
            });

        }
    }

    @Override
    public Boolean isCategoryInCache() {
        return jedisTemplate.execute(new JedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(Jedis jedis) throws Exception {
                Long len=jedis.llen(RedisKeyUtils.getCategoryListKey());
                return len>0;
            }
        });
    }

    public void setJedisTemplate(JedisTemplate jedisTemplate) {
        this.jedisTemplate = jedisTemplate;
    }

    @Override
    public Map<String, Object> getFandomParentName(final Long parentId) {
        Map<String,Object> fandomParentNameMap = new HashMap<String,Object>();
        Map<String, Object> categoryFandoms = jedisTemplate.execute(new JedisCallback<Map<String, Object>>() {
            @Override
            public Map<String, Object> doInRedis(Jedis jedis) throws Exception {
                Map<String, String> map = jedis.hgetAll(RedisKeyUtils.getCategoryKey(String.valueOf(parentId)));
                Map<String,Object> result = new HashMap<String, Object>();
                result.putAll(map);
                return result;
            }
        });
        fandomParentNameMap.put("fandom_name",categoryFandoms.get("fandom_name"));
        fandomParentNameMap.put("id",categoryFandoms.get("id"));

        Long fandomParentId = categoryFandoms.get("fandom_parent_id")==null?0:Long.valueOf(categoryFandoms.get("fandom_parent_id").toString());
        if(fandomParentId > 0){
            categoryFandoms = null;
            categoryFandoms = getFandomParentName(fandomParentId);
            fandomParentNameMap.put("fandom_parent_info",categoryFandoms);
        }
        return fandomParentNameMap;
    }

}
