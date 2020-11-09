package com.gu.rpc.client;

import io.grpc.stub.AbstractStub;

/**
 * @author FastG
 * @date 2020/11/9 13:33
 */
public interface StubFactory {

    AbstractStub<?> createStub(String name, Class<? extends AbstractStub<?>> stubType);

    AbstractStub<?> getStub(String name);

    void removeStub(String name);
}
