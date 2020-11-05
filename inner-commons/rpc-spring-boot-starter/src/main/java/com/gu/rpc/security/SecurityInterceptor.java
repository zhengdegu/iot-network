package com.gu.rpc.security;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

/**
 * @author FastG
 * @date 2020/11/4 17:37
 */
//public class SecurityInterceptor implements ServerInterceptor {
//
//
//
//    @Override
//    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
//
//        final byte[] authorization = metadata.get(Metadata.Key.of("Authorization"+Metadata.BINARY_HEADER_SUFFIX, Metadata.BINARY_BYTE_MARSHALLER));
//        String authorization1 = metadata.get(Metadata.Key.of("Authorization", ASCII_STRING_MARSHALLER));
//        return null;
//    }
//}
