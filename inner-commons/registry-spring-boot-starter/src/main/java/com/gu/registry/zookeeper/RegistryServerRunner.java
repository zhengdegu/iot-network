package com.gu.registry.zookeeper;

import com.gu.common.zookeeper.ZookeeperInstance;
import com.gu.common.constant.CommonConstants;
import com.gu.common.properties.CuratorProperties;
import com.gu.common.properties.GRpcClientProperties;
import com.gu.common.properties.RegistryProperties;
import com.gu.registry.zookeeper.registry.RegistryService;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;

import java.util.Properties;

/**
 * @author gu
 * @create 2020/12/8 下午2:59
 */
public class RegistryServerRunner implements CommandLineRunner, DisposableBean {

    @Autowired
    private RegistryService registryService;

    @Autowired
    private RedisProperties redisProperties;

    @Autowired
    private CuratorProperties curatorProperties;

    @Autowired
    private RegistryProperties registryProperties;

    @Autowired
    private GRpcClientProperties gRpcClientProperties;

    @Autowired
    private CuratorFramework curatorFramework;

    @Override
    public void destroy() throws Exception {
        curatorFramework.close();
    }

    @Override
    public void run(String... args) throws Exception {
        Properties properties = new Properties();
        properties.put(CommonConstants.REDIS_INFO, redisProperties);
        properties.put(CommonConstants.ZOOKEEPER_INFO, curatorProperties);
        properties.put(CommonConstants.RPC_INFO, gRpcClientProperties);
        String clusterName = registryProperties.getClusterName() == null ? "coordinate_server_ls" : registryProperties.getClusterName();
        String nodeId = registryProperties.getNodeId() == null ? "111" : registryProperties.getNodeId();
        registryService.register(clusterName, nodeId, ZookeeperInstance.builder()
                .nodeMetaData(properties)
                .build());
    }
}
