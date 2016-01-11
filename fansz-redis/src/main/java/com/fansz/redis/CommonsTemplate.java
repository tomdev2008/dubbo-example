package com.fansz.redis;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by allan on 16/1/11.
 */
public interface CommonsTemplate {
    /**
     * 获取fandom的分类
     *
     * @return
     */
    List<Map<String, Object>> getFandomCategory();

    /**
     * 保存fandom分类到redis
     *
     * @param categoryList
     */
    void saveCategory(List<Map<String, Object>> categoryList);

    Boolean isCategoryInCache();
}
