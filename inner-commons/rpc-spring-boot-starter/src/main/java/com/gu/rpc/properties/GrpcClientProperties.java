package com.gu.rpc.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author FastG
 * @date 2020/11/9 10:41
 */
@ConfigurationProperties("grpc")
public class GrpcClientProperties {

    private final Map<String, GrpcChannelProperties> client = new ConcurrentHashMap<>();

    public final Map<String, GrpcChannelProperties> getClient() {
        return this.client;
    }

    public GrpcChannelProperties getChannel(final String name) {
        final GrpcChannelProperties properties = getRawChannel(name);
        return properties;
    }

    private GrpcChannelProperties getRawChannel(final String name) {
        return this.client.computeIfAbsent(name, key -> new GrpcChannelProperties());
    }
    GrpcChannelProperties single= new GrpcChannelProperties();

    public GrpcChannelProperties getSingle() {
        return single;
    }

    public void setSingle(GrpcChannelProperties single) {
        this.single = single;
    }
}
