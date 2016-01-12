package com.fansz.push.server.event.handler;

import com.fansz.constant.RequestType;
import com.fansz.push.server.event.EventType;
import com.fansz.push.server.model.JsonResponse;
import com.fansz.push.server.session.ClientSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fansz.push.sender.model.NotifyMessageVO;
import com.fansz.push.server.event.BusinessEvent;
import com.fansz.push.server.event.IEventHandler;
import com.fansz.push.server.model.ResponseHeader;

/**
 * 消息推送处理逻辑实现类
 * 
 * @author Administrator
 */
@Component("pushEventHandler")
public class PushEventHandler implements IEventHandler {
    @Override
    public void handle(BusinessEvent event) {
        NotifyMessageVO messageVO = (NotifyMessageVO)event.getObj();
        try {
            logger.debug("开始发送消息给用户-{}", messageVO.getIdentifier());
            ClientSession session = event.getSession();
            if (session != null && !session.isClosed()) {
                JsonResponse response = new JsonResponse();
                ResponseHeader header = new ResponseHeader();
                header.setResType(RequestType.PUSH.getCode());
                response.setHeader(header);
                response.setBody(messageVO.getMessage());
                session.deliver(response, true);
            }
            else {
                logger.error("用户{}不在线,取消发送", messageVO.getIdentifier());
            }
        }
        catch (Exception e) {
            logger.error("发送消息给用户-{}失败", messageVO.getIdentifier());
        }

    }

    @Override
    public EventType getEventType() {
        return EventType.PUSH;
    }

    private Logger logger = LoggerFactory.getLogger("service.push");
}
