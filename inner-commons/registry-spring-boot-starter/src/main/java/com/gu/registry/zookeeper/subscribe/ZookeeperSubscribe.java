package com.gu.registry.zookeeper.subscribe;

import com.gu.registry.zookeeper.ChildListener;
import com.gu.registry.zookeeper.DataListener;
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

    private final DataListener  dataListener;

    public ZookeeperSubscribe(URL url, ZookeeperTransporter zookeeperTransporter, DataListener dataListener) {
        this.url = url;
        this.zookeeperClient = zookeeperTransporter.connect(url);
        this.dataListener = dataListener;
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
    public ZookeeperClient getClient() {
        return this.zookeeperClient;
    }

    @Override
    public void subscribe(URL url) {
        zookeeperClient.addDataListener(url.getPath(),dataListener);
    }

    @Override
    public void unsubscribe(URL url) {
        zookeeperClient.removeDataListener(url.getPath(), dataListener);
    }
}
