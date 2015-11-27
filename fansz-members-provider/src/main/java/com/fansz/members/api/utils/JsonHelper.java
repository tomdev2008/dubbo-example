package com.fansz.members.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.StringWriter;

/**
 * JSON 辅助类
 *
 * Created by xuzhen on 15/8/12.
 */
@AllArgsConstructor
public class JsonHelper {

    private ObjectMapper jacksonObjectMapper;

    public String toJsonString(Object obj) throws IOException {
        StringWriter stringWriter = new StringWriter();
//        jacksonObjectMapper.writer().writeValue(stringWriter, obj);
        stringWriter.flush();
        String result = stringWriter.toString();
        stringWriter.close();
        return result;
    }

}
