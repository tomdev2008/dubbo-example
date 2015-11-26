package com.fansz.members.api.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 错误消息类
 *
 * Created by xuzhen on 15/7/23.
 */
@Data
@AllArgsConstructor
public class ErrorMessage {
    private String returnCode;
    private String errorMsg;

    public ErrorMessage(String errorMessage) throws MessageFormatException {
        String[] e = errorMessage.split(" ");
        if (e.length == 2) {
            returnCode = e[0];
            errorMsg = e[1];
        }
        else
            throw new MessageFormatException(
                    "错误信息格式错误：" + errorMessage);
    }

    public static final String NoError = "[{\"returnCode\":\"0\", \"errorMsg\":\"no\"}]";
}
