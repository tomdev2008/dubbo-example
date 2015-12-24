package com.fansz.service.api.provider;

import com.fansz.common.provider.AbstractProvider;
import com.fansz.common.provider.model.CommonPagedResult;
import com.fansz.service.api.MessageCenterApi;
import com.fansz.service.api.service.MessageCenterService;
import com.fansz.service.model.messagecenter.QueryMessageParam;
import com.fansz.service.model.messagecenter.MessageCenterResult;
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
