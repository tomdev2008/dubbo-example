package com.fansz.members.api.service.impl;

import com.fansz.members.api.MessageCenterApi;
import com.fansz.members.api.repository.MessageCenterMapper;
import com.fansz.members.api.service.MessageCenterService;
import com.fansz.members.model.message.QueryMessageParam;
import com.fansz.members.model.messagecenter.MessageCenterResult;
import com.fansz.members.model.search.SearchParam;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by dell on 2015/12/11.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class MessageCenterServiceImpl implements MessageCenterService {
    @Autowired
    private MessageCenterMapper messageCenterMapper;

    @Override
    public PageList<MessageCenterResult> getMessageByMemberSn(QueryMessageParam queryMessageParam) {
        PageBounds pageBounds = new PageBounds(queryMessageParam.getOffset(), queryMessageParam.getLimit());
        return messageCenterMapper.getMessageByMemberSn(queryMessageParam.getMemberSn(),pageBounds);
    }
}
