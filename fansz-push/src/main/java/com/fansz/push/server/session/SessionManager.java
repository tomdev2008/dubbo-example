package com.fansz.push.server.session;

import com.fansz.constant.OsPlatform;
import com.fansz.push.server.model.RegisterParam;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 管理Session
 */
@Component("sessionManager")
public class SessionManager {

    private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);

    private Map<String, ClientSession> clientSessions = new ConcurrentHashMap<>();

    private final AtomicInteger connectionsCounter = new AtomicInteger(0);


    public SessionManager() {

    }


    public ClientSession createClientSession(Channel channel) {
        ClientSession session=new ClientSession(this,channel,null);
        // Increment the counter of user sessions
        connectionsCounter.incrementAndGet();

        logger.debug("ClientSession created.");
        return session;
    }

    public void register(ClientSession session,RegisterParam registerParam){
        String sessionId = registerParam.getSn();
        ClientSession originalSession = this.getSession(sessionId);
        if (originalSession != null && originalSession.getChannel() != null) {
            originalSession.getChannel().close();// 如果用户已经登陆，则注销上一次登陆
        }
        session.setSessionId(sessionId);
        session.setPlatform(OsPlatform.getByName(registerParam.getPlatform()));

    }

    /**
     * 返回和客户端JID相关联的的Session对象
     *
     * @param sessionId the client address
     * @return the session associated with the JID
     */
    public ClientSession getSession(String sessionId) {
        return clientSessions.get(sessionId);
    }

    /**
     * 返回已经认证通过的Session列表
     */
    public Collection<ClientSession> getSessions() {
        return clientSessions.values();
    }

    /**
     * 删除客户端Session
     *
     * @param session the session to be removed
     * @return true 成功删除返回true
     */
    public boolean removeSession(ClientSession session) {
        if (session == null) {
            return false;
        }
        // Remove the session from list
        boolean clientRemoved = clientSessions.remove(session.getSessionId()) != null;
        return clientRemoved;
    }

    /**
     * 关闭所有Session
     */
    public void closeAllSessions() {
        try {
            Set<ClientSession> sessions = new HashSet<>();
            sessions.addAll(clientSessions.values());

            for (ClientSession session : sessions) {
                try {
                    session.getChannel().close();
                } catch (Throwable t) {
                }
            }
        } catch (Exception e) {
        }
    }

    public double getConnectionsCount() {
        return connectionsCounter.doubleValue();
    }

}
