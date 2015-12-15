package com.fansz.members.api.provider;

import com.fansz.members.api.MessageCenterApi;
import com.fansz.members.api.extension.AbstractProvider;
import com.fansz.members.api.service.MessageCenterService;
import com.fansz.members.model.CommonPagedResult;
import com.fansz.members.model.message.QueryMessageParam;
import com.fansz.members.model.messagecenter.MessageCenterResult;
import com.fansz.members.model.search.SearchParam;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by dell on 2015/12/11.
 */
@Component("messageCenterProvider")
public class MessageCenterProvider extends AbstractProvider implements MessageCenterApi {

    @Autowired
    private MessageCenterService messageCenterService;

    @Override
    public CommonPagedResult<MessageCenterResult> getMessageByMemberSn(QueryMessageParam queryMessageParam) {
        PageList<MessageCenterResult> pageList = messageCenterService.getMessageByMemberSn(queryMessageParam);
        return super.renderPagedSuccess(pageList);
    }
}
