package com.gu.rpc.server;

import com.gu.rpc.ControlRPCGrpc;
import io.grpc.Server;
import org.springframework.beans.factory.DisposableBean;

/**
 * @author FastG
 * @date 2020/11/6 16:54
 */
public interface GrpcServerFactory extends DisposableBean {

    /**
     * 启动
     *
     * @return
     */
    Server createServer();

    /**
     * 获取地址
     *
     * @return
     */
    String getAddress();

    /**
     * 获取端口
     *
     * @return
     */
    int getPort();

    /**
     * 添加服务
     *
     * @param service
     */
    void addService(ControlRPCGrpc.ControlRPCImplBase service);


    @Override
    void destroy();
}
