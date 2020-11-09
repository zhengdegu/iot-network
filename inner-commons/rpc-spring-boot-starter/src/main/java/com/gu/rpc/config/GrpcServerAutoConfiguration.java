package com.gu.rpc.config;

import com.google.protobuf.Any;
import com.gu.rpc.ControlRPCGrpc;
import com.gu.rpc.RpcMessage;
import com.gu.rpc.properties.GrpcServerProperties;
import com.gu.rpc.server.GrpcServerFactory;
import com.gu.rpc.server.GrpcServerLifecycle;
import com.gu.rpc.server.NettyGrpcServerFactory;
import com.gu.rpc.service.ControlRPCService;
import io.grpc.Server;
import io.grpc.services.HealthStatusManager;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author g130016
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(Server.class)
@EnableConfigurationProperties(GrpcServerProperties.class)
public class GrpcServerAutoConfiguration {


    @ConditionalOnClass(name = {"io.netty.channel.Channel", "io.grpc.netty.NettyServerBuilder"})
    @Bean
    public NettyGrpcServerFactory nettyGrpcServerFactory(
            final GrpcServerProperties properties,
            final ControlRPCGrpc.ControlRPCImplBase service) {
        final NettyGrpcServerFactory factory = new NettyGrpcServerFactory(properties);
        factory.addService(service);
        return factory;
    }

    @ConditionalOnMissingBean
    @Bean
    public HealthStatusManager healthStatusManager() {
        return new HealthStatusManager();
    }


    @ConditionalOnMissingBean
    @ConditionalOnBean(GrpcServerFactory.class)
    @Bean
    public SmartLifecycle grpcServerLifecycle(final GrpcServerFactory factory) {
        return new GrpcServerLifecycle(factory);
    }

    @Bean
    @ConditionalOnMissingBean(ControlRPCGrpc.ControlRPCImplBase.class)
    public ControlRPCGrpc.ControlRPCImplBase service() {
        return new ControlRPCService() {
            @Override
            protected void doSend(RpcMessage.SendRequest request, StreamObserver<RpcMessage.SendResponse> streamObserver) {

            }

            @Override
            protected void doClose(RpcMessage.CloseRequest request, StreamObserver<RpcMessage.CloseResponse> streamObserver) {

            }

            @Override
            protected Any doAnything(Any any) {
                return null;
            }

            @Override
            protected RpcMessage.SendResponse doSend(RpcMessage.SendRequest request) {
                return null;
            }

            @Override
            protected RpcMessage.CloseResponse doClose(RpcMessage.CloseRequest request) {
                return null;
            }
        };
    }
}
