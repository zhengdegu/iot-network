package com.gu.registry.zookeeper.registry.impl;

import com.gu.common.zookeeper.client.ZookeeperClient;
import com.gu.common.zookeeper.ZookeeperInstance;
import com.gu.registry.zookeeper.registry.RegistryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author gu
 * @create 2020/12/7 下午3:28
 */
@Slf4j
public class ZookeeperRegistry implements RegistryService {

    @Autowired
    private ZookeeperClient zookeeperClient;

    @Override
    public void register(String cluster, String nodeId, ZookeeperInstance zookeeperInstance) {
        if (!zookeeperClient.checkExists(cluster)) {
            zookeeperClient.create(cluster, false);
        }
        String nodeMetaDataPath = cluster + "/" + nodeId;
        if (zookeeperClient.checkExists(nodeMetaDataPath)) {
            log.error("注册到Zookeeper服务NodeID {}节点存在，请确保NodeID编号唯一", nodeId);
            System.exit(-6);
        }
        zookeeperClient.create(nodeMetaDataPath, SerializationUtils.serialize(zookeeperInstance), true);
    }
}
