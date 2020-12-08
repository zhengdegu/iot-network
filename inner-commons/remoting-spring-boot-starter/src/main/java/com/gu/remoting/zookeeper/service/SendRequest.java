package com.gu.remoting.zookeeper.service;

import com.gu.rpc.RpcMessage;

import java.util.concurrent.CompletableFuture;

/**
 * @author FastG
 * @date 2020/11/9 14:27
 */
public interface SendRequest {

    /**
     * 异步发送消息到终端
     *
     * @param id
     * @param request
     * @return
     */
    public CompletableFuture<?> asySend(String id, RpcMessage.SendRequest request);

    /**
     * 同步发送消息到终端
     *
     * @param id
     * @param request
     * @return
     */
    public RpcMessage.SendResponse send(String id, RpcMessage.SendRequest request);

    /**
     * 异步关闭连接
     *
     * @param id
     * @return
     */
    public CompletableFuture<?> asyCloseConnection(String id, RpcMessage.CloseRequest request);

    /**
     * 同步关闭连接
     *
     * @param id
     * @return
     */
    public RpcMessage.CloseResponse closeConnection(String id,RpcMessage.CloseRequest request);
}
