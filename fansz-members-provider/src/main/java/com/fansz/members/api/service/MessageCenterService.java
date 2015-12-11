package com.fansz.members.api.service;

import com.fansz.members.model.message.QueryMessageParam;
import com.fansz.members.model.messagecenter.MessageCenterResult;
import com.fansz.members.model.search.SearchParam;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

/**
 * Created by dell on 2015/12/11.
 */
public interface MessageCenterService {

    PageList<MessageCenterResult> getMessageByMemberSn(QueryMessageParam queryMessageParam);

}
