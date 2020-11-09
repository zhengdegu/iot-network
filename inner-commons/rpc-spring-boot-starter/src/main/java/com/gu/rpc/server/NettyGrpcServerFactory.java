package com.gu.rpc.server;

import com.google.common.net.InetAddresses;
import com.gu.rpc.properties.GrpcServerProperties;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * @author FastG
 * @date 2020/11/6 17:08
 */
public class NettyGrpcServerFactory extends AbstractGrpcServerFactory<NettyServerBuilder> {


    public NettyGrpcServerFactory(GrpcServerProperties properties) {
        super(properties);
    }

    @Override
    protected NettyServerBuilder newServerBuilder() {
        final String address = getAddress();
        final int port = getPort();
        if (GrpcServerProperties.ANY_IP_ADDRESS.equals(address)) {
            return NettyServerBuilder.forPort(port);
        } else {
            return NettyServerBuilder.forAddress(new InetSocketAddress(InetAddresses.forString(address), port));
        }
    }

    @Override
    protected void configureKeepAlive(final NettyServerBuilder builder) {
        if (this.properties.isEnableKeepAlive()) {
            builder.keepAliveTime(this.properties.getKeepAliveTime().toNanos(), TimeUnit.NANOSECONDS)
                    .keepAliveTimeout(this.properties.getKeepAliveTimeout().toNanos(), TimeUnit.NANOSECONDS);
        }
        builder.permitKeepAliveTime(this.properties.getPermitKeepAliveTime().toNanos(), TimeUnit.NANOSECONDS)
                .permitKeepAliveWithoutCalls(this.properties.isPermitKeepAliveWithoutCalls());
    }

}
