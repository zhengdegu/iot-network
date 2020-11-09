package com.gu.rpc.client.service;

import com.gu.rpc.ControlRPCGrpc;
import com.gu.rpc.RpcMessage;
import com.gu.rpc.client.StubFactory;
import com.gu.rpc.properties.GrpcClientProperties;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CompletableFuture;

/**
 * @author FastG
 * @date 2020/11/9 14:33
 */
abstract class AbstractSendRequest implements SendRequest {

    public final StubFactory stubFactory;

    public final GrpcClientProperties grpcClientProperties;

    public AbstractSendRequest(StubFactory stubFactory, GrpcClientProperties properties) {
        this.stubFactory = stubFactory;
        this.grpcClientProperties = properties;
    }

    @Override
    public CompletableFuture<RpcMessage.SendResponse> asySend(String id, RpcMessage.SendRequest request) {

        CompletableFuture<RpcMessage.SendResponse> result = new CompletableFuture<>();
        StreamObserver<RpcMessage.SendResponse> responseStreamObserver = new StreamObserver<RpcMessage.SendResponse>() {
            @Override
            public void onNext(RpcMessage.SendResponse response) {
                result.complete(response);
            }

            @Override
            public void onError(Throwable t) {
            }

            @Override
            public void onCompleted() {
            }
        };
        ControlRPCGrpc.ControlRPCStub stub = (ControlRPCGrpc.ControlRPCStub) stubFactory.getStub(grpcClientProperties.getSingle().getServerName());
        StreamObserver<RpcMessage.SendRequest> requestStreamObserver = stub.streamSend(responseStreamObserver);
        requestStreamObserver.onNext(request);
        requestStreamObserver.onCompleted();
        return result;
    }

    @Override
    public RpcMessage.SendResponse send(String id,RpcMessage.SendRequest request) {
        ControlRPCGrpc.ControlRPCBlockingStub stub= (ControlRPCGrpc.ControlRPCBlockingStub)stubFactory.getStub(grpcClientProperties.getSingle().getServerName());
        return stub.send(request);
    }

    @Override
    public CompletableFuture<RpcMessage.CloseResponse> asyCloseConnection(String id, RpcMessage.CloseRequest request) {
        CompletableFuture<RpcMessage.CloseResponse> result = new CompletableFuture<>();
        StreamObserver<RpcMessage.CloseResponse> responseStreamObserver = new StreamObserver<RpcMessage.CloseResponse>() {
            @Override
            public void onNext(RpcMessage.CloseResponse value) {
                result.complete(value);
                this.onCompleted();
            }

            @Override
            public void onError(Throwable t) {
            }

            @Override
            public void onCompleted() {
            }
        };
        ControlRPCGrpc.ControlRPCStub stub = (ControlRPCGrpc.ControlRPCStub) stubFactory.getStub(grpcClientProperties.getSingle().getServerName());
        StreamObserver<RpcMessage.CloseRequest> requestStreamObserver = stub.streamClose(responseStreamObserver);
        requestStreamObserver.onNext(RpcMessage.CloseRequest.newBuilder().setId(id).build());
        requestStreamObserver.onCompleted();
        return result;
    }

    @Override
    public RpcMessage.CloseResponse closeConnection(String id,RpcMessage.CloseRequest request) {
        ControlRPCGrpc.ControlRPCBlockingStub stub= (ControlRPCGrpc.ControlRPCBlockingStub)stubFactory.getStub(grpcClientProperties.getSingle().getServerName());
        return stub.close(request);
    }

}
