
package com.gu.remoting.zookeeper;

import io.grpc.Channel;
import io.grpc.ConnectivityState;

import java.util.Collections;
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

    /**
     * 获取 channel
     * @param name
     * @return
     */
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

    /**
     * 根据name关闭channel
     * @param name
     */
    void close(String name);
}
