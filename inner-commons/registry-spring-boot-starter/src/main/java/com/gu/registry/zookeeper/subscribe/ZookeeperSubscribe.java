package com.gu.registry.zookeeper.subscribe;

import com.gu.registry.zookeeper.ChildListener;
import com.gu.registry.zookeeper.ZookeeperClient;
import com.gu.registry.zookeeper.ZookeeperTransporter;
import com.gu.registry.zookeeper.properties.URL;

/**
 * @author FastG
 * @date 2020/11/2 11:33
 */
public class ZookeeperSubscribe implements SubscribeService {


    private final URL url;

    private final ZookeeperClient zookeeperClient;

    private final ChildListener childListener;

    public ZookeeperSubscribe(URL url, ZookeeperTransporter zookeeperTransporter, ChildListener childListener) {
        this.url = url;
        this.zookeeperClient = zookeeperTransporter.connect(url);
        this.childListener = childListener;
    }

    @Override
    public URL getUrl() {
        return this.url;
    }

    @Override
    public boolean isAvailable() {
        return zookeeperClient.isConnected();
    }

    @Override
    public void destroy() {
        zookeeperClient.close();
    }

    @Override
    public void subscribe(URL url) {
        zookeeperClient.addChildListener(url.getPath(), childListener);
    }

    @Override
    public void unsubscribe(URL url) {
        zookeeperClient.removeChildListener(url.getPath(),childListener);
    }
}
