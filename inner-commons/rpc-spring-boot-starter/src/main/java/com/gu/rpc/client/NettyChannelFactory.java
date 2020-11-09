package com.gu.rpc.client;

import com.google.common.net.InetAddresses;
import com.gu.rpc.properties.GrpcChannelProperties;
import com.gu.rpc.properties.GrpcClientProperties;
import io.grpc.Channel;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;

import java.net.InetSocketAddress;
import java.net.URI;

/**
 * @author FastG
 * @date 2020/11/9 13:24
 */
public class NettyChannelFactory extends AbstractChannelFactory<NettyChannelBuilder> {

    protected NettyChannelFactory(GrpcClientProperties properties) {
        super(properties);
    }

    @Override
    protected NettyChannelBuilder newChannelBuilder(String name) {
        final GrpcChannelProperties properties = getPropertiesFor(name);
        final String address = properties.getAddress();
        final int port = properties.getPort();
        return NettyChannelBuilder.forAddress(new InetSocketAddress(InetAddresses.forString(address), port));
    }
}
