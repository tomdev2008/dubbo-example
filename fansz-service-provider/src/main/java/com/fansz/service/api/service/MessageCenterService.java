package com.fansz.service.api.service;

import com.fansz.service.model.messagecenter.QueryMessageParam;
import com.fansz.service.model.messagecenter.MessageCenterResult;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

/**
 * Created by dell on 2015/12/11.
 */
public interface MessageCenterService {

    PageList<MessageCenterResult> getMessageByMemberSn(QueryMessageParam queryMessageParam);

}
