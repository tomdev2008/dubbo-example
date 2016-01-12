package com.fansz.push.server.net;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import com.fansz.constant.RequestType;
import com.fansz.push.server.handler.IRequestHandler;
import com.fansz.push.server.session.ClientSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.fansz.push.server.model.JsonRequest;

public class RquestRouter implements ApplicationContextAware {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ApplicationContext applicationContext;

    private Map<RequestType, IRequestHandler> listeners = new ConcurrentHashMap<RequestType, IRequestHandler>();

    public void process(JsonRequest request, ClientSession session) throws Exception {
        boolean found = false;
        RequestType reqType = RequestType.getByCode(request.getHeader().getReqType());
        if (reqType != null) {
            IRequestHandler listener = listeners.get(reqType);
            if (listener != null) {
                found = true;
                listener.onEvent(request, session);
            }
        }
        if (!found) {
            logger.error("cann't find listener for requestType {}", request.getHeader().getReqType());
        }
    }

    @PostConstruct
    public void init() {
        Map<String, IRequestHandler> listenersMap = applicationContext.getBeansOfType(IRequestHandler.class);
        if (listenersMap != null) {
            for (String key : listenersMap.keySet()) {
                IRequestHandler listener = listenersMap.get(key);
                listeners.put(listener.getListenerName(), listener);
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
