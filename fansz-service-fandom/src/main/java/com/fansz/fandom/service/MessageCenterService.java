package com.fansz.fandom.service;

import com.fansz.fandom.model.messagecenter.MessageCenterResult;
import com.fansz.fandom.model.messagecenter.QueryMessageParam;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

/**
 * Created by dell on 2015/12/11.
 */
public interface MessageCenterService {

    PageList<MessageCenterResult> getMessageByMemberSn(QueryMessageParam queryMessageParam);

}
