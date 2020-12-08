package com.gu.registry.zookeeper.registry;

import com.gu.common.zookeeper.ZookeeperInstance;

/**
 * @author FastG
 * @date 2020/11/1 19:02
 */
public interface RegistryService {

    /**
     * 注册服务
     */
    void register(String cluster,String nodeId,ZookeeperInstance zookeeperInstance);
}
