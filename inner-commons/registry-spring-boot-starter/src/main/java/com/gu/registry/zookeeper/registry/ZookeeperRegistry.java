package com.gu.registry.zookeeper.registry;

import com.gu.registry.zookeeper.ZookeeperClient;
import com.gu.registry.zookeeper.ZookeeperTransporter;
import com.gu.registry.zookeeper.properties.URL;
import lombok.extern.slf4j.Slf4j;

/**
 * @author FastG
 * @date 2020/11/2 9:18
 */
@Slf4j
public class ZookeeperRegistry implements RegistryService {

    private final URL url;
    private final ZookeeperClient zookeeperClient;

    public ZookeeperRegistry(URL url, ZookeeperTransporter zookeeperTransporter) {
        this.url = url;
        zookeeperClient = zookeeperTransporter.connect(url);
    }

    @Override
    public void register(URL url) {
        if (url == null || url.getInstance() == null) {
            throw new IllegalStateException("register instance is null");
        }
        try {
            if (zookeeperClient.checkExist(url.getPath() + url.getInstance().getName())) {
                throw new RuntimeException("Failed to register " + url + " to zookeeper " + url.getInstance().getName() + "is registered!");
            }
            zookeeperClient.create(url.getPath() + url.getInstance().getName(), url.getInstance().toString(), true);
        } catch (Throwable e) {
            throw new RuntimeException("Failed to register " + url + " to zookeeper " + getUrl() + ", cause: " + e.getMessage(), e);
        }
    }

    @Override
    public void unregister(URL url) {
        if (url == null || url.getInstance() == null) {
            throw new IllegalStateException("register instance is null");
        }
        try {
            if (zookeeperClient.checkExist(url.getPath() + url.getInstance().getName())) {
                zookeeperClient.delete(url.getPath() + url.getInstance().getName());
            }
        } catch (Throwable e) {
            throw new RuntimeException("Failed to unregister " + url + ", cause: " + e.getMessage(), e);
        }
    }

    @Override
    public URL getUrl() {
        return null;
    }

    @Override
    public boolean isAvailable() {
        return zookeeperClient.isConnected();
    }

    @Override
    public void destroy() {
        zookeeperClient.close();
    }
}
