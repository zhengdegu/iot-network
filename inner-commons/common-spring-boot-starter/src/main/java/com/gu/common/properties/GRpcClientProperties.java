package com.gu.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author gu
 * @create 2020/12/8 下午2:46
 */
@ConfigurationProperties(prefix = "spring.grpc.client")
public class GRpcClientProperties {

    private String host="127.0.0.1";
    private  Integer port=6565;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
