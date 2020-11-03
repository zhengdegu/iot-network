package com.gu.registry.zookeeper.support;

import com.gu.registry.zookeeper.ZookeeperClient;
import com.gu.registry.zookeeper.ZookeeperTransporter;
import com.gu.registry.zookeeper.properties.URL;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author FastG
 * @date 2020/11/2 10:03
 */
@Slf4j
public abstract class AbstractZookeeperTransporter implements ZookeeperTransporter {

    private final Map<String, ZookeeperClient> ZOOKEEPER_CLIENT_MAP = new ConcurrentHashMap<>();

    @Override
    public ZookeeperClient connect(URL url) {

        if (!StringUtils.isNotBlank(url.getConnectString())) {
            throw new IllegalStateException("create zookeeper client connectString is null");
        }
        ZookeeperClient zookeeperClient = null;
        if ((zookeeperClient = fetchZookeeperClientCache(url.getConnectString())) != null && zookeeperClient.isConnected()) {
            log.info("find valid zookeeper client from the cache for address: " + url);
            return zookeeperClient;
        }
        synchronized (ZOOKEEPER_CLIENT_MAP) {
            if ((zookeeperClient = fetchZookeeperClientCache(url.getConnectString())) != null && zookeeperClient.isConnected()) {
                log.info("find valid zookeeper client from the cache for address: " + url);
                return zookeeperClient;
            }

            zookeeperClient = createZookeeperClient(url);
            log.info("No valid zookeeper client found from cache, therefore create a new client for url. " + url);
            ZOOKEEPER_CLIENT_MAP.put(url.getConnectString(), zookeeperClient);
        }
        return zookeeperClient;
    }

    private ZookeeperClient fetchZookeeperClientCache(String connectString) {
        ZookeeperClient zookeeperClient = null;
        if ((zookeeperClient = ZOOKEEPER_CLIENT_MAP.get(connectString)) != null && zookeeperClient.isConnected()) {
            return zookeeperClient;
        }
        return zookeeperClient;
    }

    /**
     * 创建client
     *
     * @param url
     * @return
     */
    protected abstract ZookeeperClient createZookeeperClient(URL url);

}
