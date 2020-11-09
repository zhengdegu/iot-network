
package com.gu.rpc.client;

import io.grpc.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author g130016
 */
public interface GrpcChannelFactory extends AutoCloseable {

    /**
     * 创建channel
     * @param name
     * @return
     */
     Channel createChannel(final String name);

     Channel getChannel(final  String name);

    /**
     * 获取连接状态
     * @return
     */
    default Map<String, ConnectivityState> getConnectivityState() {
        return Collections.emptyMap();
    }

    @Override
    void close();

    void close(String name);
}
