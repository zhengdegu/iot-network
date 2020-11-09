package com.gu.rpc.server;


import com.google.protobuf.Any;
import com.gu.rpc.ControlRPCGrpc;
import com.gu.rpc.RpcMessage;
import com.gu.rpc.properties.GrpcServerProperties;
import com.gu.rpc.service.ControlRPCService;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import io.grpc.reflection.v1alpha.ServerReflectionGrpc;
import io.grpc.services.HealthStatusManager;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link AbstractGrpcServerFactory}.
 */
class AbstractGrpcServerFactoryTest {


    @Test
    public void testConfigureServices() {
        final GrpcServerProperties properties = new GrpcServerProperties();

        final NettyGrpcServerFactory serverFactory = new NettyGrpcServerFactory(properties);
        serverFactory.healthStatusManager = new HealthStatusManager();

        ControlRPCGrpc.ControlRPCImplBase service = new ControlRPCService() {
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
        };
         serverFactory.addService(service);
        final NettyServerBuilder serverBuilder = serverFactory.newServerBuilder();

        GrpcServerLifecycle lifecycle =new GrpcServerLifecycle(serverFactory);
        try {
            lifecycle.createAndStartGrpcServer();

        } catch (IOException e) {
            e.printStackTrace();
        }
        assertThat(lifecycle.isRunning(), is(true));
    }

}
