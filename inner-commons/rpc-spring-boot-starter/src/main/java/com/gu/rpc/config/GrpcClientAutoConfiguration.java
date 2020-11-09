package com.gu.rpc.config;

import com.gu.rpc.properties.GrpcClientProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author FastG
 * @date 2020/11/9 10:45
 */
@EnableConfigurationProperties(GrpcClientProperties.class)
public class GrpcClientAutoConfiguration {
}
