package com.fansz.relations.service;

import com.fansz.relations.model.OpRequestParam;

/**
 * Created by allan on 16/2/1.
 */
public interface AsyncEventService {

    /**
     * 触发添加好友事件
     * @param opRequestParam
     */
    void addFriend(OpRequestParam opRequestParam);

}
