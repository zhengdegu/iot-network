package com.gu.rpc.service;


import com.google.protobuf.Any;
import com.gu.rpc.ControlRPCGrpc;
import com.gu.rpc.RpcMessage;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.security.access.annotation.Secured;

@Slf4j
@GrpcService
public abstract class ControlRPCService extends ControlRPCGrpc.ControlRPCImplBase {

    @Secured("ROLE_GREET")
    @Override
    public StreamObserver<RpcMessage.SendRequest> streamSend(StreamObserver<RpcMessage.SendResponse> responseObserver) {
        return new StreamObserver<RpcMessage.SendRequest>() {
            @Override
            public void onNext(RpcMessage.SendRequest value) {
                doSend(value, responseObserver);
            }

            @Override
            public void onError(Throwable t) {
                log.error("", t);
            }

            @Override
            public void onCompleted() {
            }
        };
    }

    @Secured("ROLE_GREET")
    @Override
    public StreamObserver<RpcMessage.CloseRequest> streamClose(
            StreamObserver<RpcMessage.CloseResponse> responseObserver) {
        return new StreamObserver<RpcMessage.CloseRequest>() {
            @Override
            public void onNext(RpcMessage.CloseRequest value) {
                doClose(value, responseObserver);
            }

            @Override
            public void onError(Throwable t) {
                log.error("", t);
            }

            @Override
            public void onCompleted() {
            }
        };
    }

    @Secured("ROLE_GREET")
    @Override
    public void any(Any request, StreamObserver<Any> responseObserver) {
        super.any(request, responseObserver);
        responseObserver.onNext(doAnything(request));
        responseObserver.onCompleted();
    }

    protected abstract void doSend(RpcMessage.SendRequest request,
                                   StreamObserver<RpcMessage.SendResponse> streamObserver);

    protected abstract void doClose(RpcMessage.CloseRequest request,
                                    StreamObserver<RpcMessage.CloseResponse> streamObserver);

    protected abstract Any doAnything(Any any);

}