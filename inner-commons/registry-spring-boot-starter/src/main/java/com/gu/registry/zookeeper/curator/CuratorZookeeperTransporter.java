package com.gu.registry.zookeeper.curator;

import com.gu.registry.zookeeper.ZookeeperClient;
import com.gu.registry.zookeeper.properties.URL;
import com.gu.registry.zookeeper.support.AbstractZookeeperTransporter;

/**
 * @author FastG
 * @date 2020/11/2 10:43
 */
public class CuratorZookeeperTransporter extends AbstractZookeeperTransporter {
    @Override
    protected ZookeeperClient createZookeeperClient(URL url) {
        return new CuratorZookeeperClient(url);
    }
}
