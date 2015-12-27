package com.fansz.fandom.service.impl;

import com.fansz.fandom.repository.MessageCenterMapper;
import com.fansz.fandom.service.MessageCenterService;
import com.fansz.fandom.model.messagecenter.QueryMessageParam;
import com.fansz.fandom.model.messagecenter.MessageCenterResult;
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
        PageBounds pageBounds = new PageBounds(queryMessageParam.getPageNum(), queryMessageParam.getPageSize());
        return messageCenterMapper.getMessageByMemberSn(queryMessageParam.getCurrentSn(),pageBounds);
    }
}
