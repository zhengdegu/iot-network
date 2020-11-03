package com.gu.registry.zookeeper;

import com.gu.registry.zookeeper.properties.URL;

/**
 * @author FastG
 * @date 2020/11/2 10:01
 */
public interface ZookeeperTransporter {

    /**
     * 连接zookeeper
     * @param url  连接的参数
     * @return  zookeeperClient
     */
    ZookeeperClient connect(URL url);
}
