package com.gu.registry.zookeeper.registry;

import com.gu.registry.zookeeper.ZookeeperClient;
import com.gu.registry.zookeeper.ZookeeperTransporter;
import com.gu.registry.zookeeper.properties.URL;
import com.gu.registry.zookeeper.properties.ZooKeeperNodeInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * @author FastG
 * @date 2020/11/2 9:18
 */
@Slf4j
public class ZookeeperRegistry implements RegistryService {

    private final URL url;
    private final ZooKeeperNodeInfo zooKeeperNodeInfo;
    private final ZookeeperClient zookeeperClient;

    public ZookeeperRegistry(URL url, ZookeeperTransporter zookeeperTransporter, ZooKeeperNodeInfo zooKeeperNodeInfo) {
        this.url = url;
        zookeeperClient = zookeeperTransporter.connect(url);
        this.zooKeeperNodeInfo = zooKeeperNodeInfo;
    }

    @Override
    public void register(URL url) {
        if (url == null || zooKeeperNodeInfo == null) {
            throw new IllegalStateException("register instance is null");
        }
        try {
            if (zookeeperClient.checkExist(url.getPath() + zooKeeperNodeInfo.getId())) {
                throw new RuntimeException("Failed to register " + url + " to zookeeper " + zooKeeperNodeInfo.getId() + "is registered!");
            }
            zookeeperClient.create(url.getPath() + zooKeeperNodeInfo.getId(), zooKeeperNodeInfo.serialize(), true);
        } catch (Throwable e) {
            throw new RuntimeException("Failed to register " + url + " to zookeeper " + getUrl() + ", cause: " + e.getMessage(), e);
        }
    }

    @Override
    public void unregister(URL url) {
        if (url == null || zooKeeperNodeInfo == null) {
            throw new IllegalStateException("register instance is null");
        }
        try {
            if (zookeeperClient.checkExist(url.getPath() + zooKeeperNodeInfo.getId())) {
                zookeeperClient.delete(url.getPath() + zooKeeperNodeInfo.getId());
            }
        } catch (Throwable e) {
            throw new RuntimeException("Failed to unregister " + url + ", cause: " + e.getMessage(), e);
        }
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
}
