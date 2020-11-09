package com.gu.rpc.client;

import com.gu.rpc.ControlRPCGrpc;
import io.grpc.Channel;
import io.grpc.stub.AbstractStub;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author FastG
 * @date 2020/11/9 13:55
 */
public class FutureStubFactory implements StubFactory {

    private final Map<String, AbstractStub<?>> stub = new ConcurrentHashMap<>();
    private final GrpcChannelFactory grpcChannelFactory;

    public FutureStubFactory(GrpcChannelFactory grpcChannelFactory) {
        this.grpcChannelFactory = grpcChannelFactory;
    }

    @Override
    public AbstractStub<?> createStub(String name, Class<? extends AbstractStub<?>> stubType) {
        Channel channel = grpcChannelFactory.getChannel(name);
        if (channel == null) {
            channel = grpcChannelFactory.createChannel(name);
        }
        ControlRPCGrpc.ControlRPCStub rpcFutureStub = ControlRPCGrpc.newStub(channel);
        stub.put(name, rpcFutureStub);
        return rpcFutureStub;
    }

    @Override
    public AbstractStub<?> getStub(String name) {
        return stub.get(name);
    }

    @Override
    public void removeStub(String name) {
        grpcChannelFactory.close(name);

    }
}
