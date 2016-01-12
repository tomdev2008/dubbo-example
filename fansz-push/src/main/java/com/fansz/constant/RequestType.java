package com.fansz.constant;

public enum RequestType {
    LOGOUT("logout"), // 客户端退出

    LOGIN("login"), // 客户端登陆

    CLOSE("invalidate"), // 服务端主动关闭

    PUSH("push"), // 消息推送

    RECEIPTS("receipts"), // 消息回执

    HEARTBEAT("heartbeat"), // 心跳

    CLICK("click"), // 客户端点击事件

    SETTING_TAG("tag"), // 客户端设置标签

    SETTING_ALIAS("alias");// 客户端设备别名

    private String code;

    private RequestType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static RequestType getByCode(String code) {
        for (RequestType p : RequestType.values()) {
            if (p.getCode().equals(code))
                return p;
        }
        return null;
    }
}
