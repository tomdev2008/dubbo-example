package com.fansz.members.tools;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Component
@Provider
@Consumes({"application/*+json", "text/json"})
@Produces({"application/*+json", "text/json"})
public class JacksonConfig implements ContextResolver<ObjectMapper> {
    private final ObjectMapper objectMapper;

    public JacksonConfig() throws Exception {
        objectMapper = new ObjectMapper().disable(
                SerializationConfig.Feature.WRITE_NULL_MAP_VALUES).disable(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS).setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
    }

    @Override
    public ObjectMapper getContext(Class<?> arg0) {
        return objectMapper;
    }
}
