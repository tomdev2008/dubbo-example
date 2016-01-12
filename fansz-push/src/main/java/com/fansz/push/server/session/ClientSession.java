package com.fansz.push.server.session;

import com.alibaba.fastjson.JSON;
import com.fansz.constant.OsPlatform;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 客户端连接Session对象
 */
public class ClientSession {

    private static final Logger logger = LoggerFactory.getLogger(ClientSession.class);
    protected Channel channel;

    protected SessionManager sessionManager;

    private String sessionId;

    private OsPlatform platform;// Ios或Android

    private long startDate = System.currentTimeMillis();

    private long lastActiveDate;

    private long clientPacketCount = 0;

    private long serverPacketCount = 0;

    private AtomicInteger state = new AtomicInteger(1);


    /**
     * Constructor.
     *
     * @param channel   the connection
     * @param sessionId the session ID
     */

    public ClientSession(SessionManager sessionManager, Channel channel, String sessionId) {
        this.channel = channel;
        this.sessionManager = sessionManager;
        this.sessionId = sessionId;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }


    /**
     * 返回客户端的IP地址
     */
    public String getRemoteAddress() {
        try {
            InetAddress inet = ((InetSocketAddress) channel.remoteAddress()).getAddress();
            return inet.getHostAddress();
        } catch (Exception e) {
            logger.error("获取客户端地址出错", e);
        }
        return "unkonwn";
    }

    /**
     * 获取回话的创建时间
     */
    public Date getCreationDate() {
        return new Date(startDate);
    }

    /**
     * 获取回话的最近一次活动时间
     */
    public Date getLastActiveDate() {
        return new Date(lastActiveDate);
    }

    /**
     * 获取客户端发送给服务端的Packet数
     */
    public long getNumClientPackets() {
        return this.clientPacketCount;
    }

    /**
     * 获取服务器发送给客户端的Packet数
     */
    public long getNumServerPackets() {
        return this.serverPacketCount;
    }

    /**
     * 增加客户端发送给服务器端的Packet数
     */
    public void incrementClientPacketCount() {
        clientPacketCount++;
        lastActiveDate = System.currentTimeMillis();
    }

    /**
     * 增大服务器端发送给客户端的packet数
     */
    public void incrementServerPacketCount() {
        serverPacketCount++;
        lastActiveDate = System.currentTimeMillis();
    }

    public void invalidate() {
        state.compareAndSet(1, 0);
        if (channel != null) {
            channel.close();
        }
    }


    /**
     * 判断连接是否关闭
     */
    public boolean isClosed() {
        return state.get() == 0;
    }

    public boolean deliver(Object response) {
        return deliver(response, false);
    }

    public boolean deliver(Object response, boolean flush) {
        if (response instanceof String) {
            return deliverRawText((String) response, flush);
        } else {
            return deliverRawText(JSON.toJSONString(response), flush);
        }
    }

    /**
     * 发送消息并关闭连接
     *
     * @param response
     */
    public void deliverAndClose(Object response) {
        if (!isClosed()) {
            String content = "";
            try {
                content = JSON.toJSONString(response);
                channel.writeAndFlush(content).addListener(ChannelFutureListener.CLOSE);
            } catch (Exception e) {
                logger.error(String.format("向客户端写数据时出错:%s", content), e);
                invalidate();
            }
        }

    }

    private boolean deliverRawText(String text, boolean flush) {
        boolean flag = true;
        if (!isClosed()) {
            try {
                if (flush) {
                    channel.writeAndFlush(text);
                } else {
                    channel.write(text);
                }
            } catch (Exception e) {
                flag = false;
                logger.error(String.format("向客户端写数据时出错:%s", text), e);
                invalidate();
            }
        }
        return flag;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }


    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getClientPacketCount() {
        return clientPacketCount;
    }

    public void setClientPacketCount(long clientPacketCount) {
        this.clientPacketCount = clientPacketCount;
    }

    public long getServerPacketCount() {
        return serverPacketCount;
    }

    public void setServerPacketCount(long serverPacketCount) {
        this.serverPacketCount = serverPacketCount;
    }


    public void setLastActiveDate(long lastActiveDate) {
        this.lastActiveDate = lastActiveDate;
    }


    public OsPlatform getPlatform() {
        return platform;
    }

    public void setPlatform(OsPlatform platform) {
        this.platform = platform;
    }

    public Channel getChannel() {
        return channel;
    }
}
