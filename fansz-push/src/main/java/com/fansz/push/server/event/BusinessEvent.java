package com.fansz.push.server.event;

import com.fansz.push.server.session.ClientSession;

import java.io.Serializable;

/**
 * 业务事件对象
 * 
 * @author Administrator
 */
public class BusinessEvent implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 2954727933614900430L;

    private EventType eventType;

    private Serializable obj;

    private ClientSession session;

    public BusinessEvent() {

    }

    public BusinessEvent(EventType eventType, Serializable obj, ClientSession session) {
        this.eventType = eventType;
        this.obj = obj;
        this.session = session;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Serializable getObj() {
        return obj;
    }

    public void setObj(Serializable obj) {
        this.obj = obj;
    }

    public ClientSession getSession() {
        return session;
    }

    public void setSession(ClientSession session) {
        this.session = session;
    }
}
