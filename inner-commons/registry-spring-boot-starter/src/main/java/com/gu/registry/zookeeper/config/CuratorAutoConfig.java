package com.gu.registry.zookeeper.config;

import com.gu.registry.zookeeper.DataListener;
import com.gu.registry.zookeeper.EventType;
import com.gu.registry.zookeeper.ZookeeperTransporter;
import com.gu.registry.zookeeper.curator.CuratorZookeeperTransporter;
import com.gu.registry.zookeeper.properties.URL;
import com.gu.registry.zookeeper.properties.ZooKeeperNodeInfo;
import com.gu.registry.zookeeper.registry.Registry;
import com.gu.registry.zookeeper.registry.ZookeeperRegistry;
import com.gu.registry.zookeeper.subscribe.Subscribe;
import com.gu.registry.zookeeper.subscribe.ZookeeperSubscribe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author FastG
 * @date 2020/11/1 18:57
 */
@Slf4j
@EnableConfigurationProperties({URL.class, ZooKeeperNodeInfo.class})
public class CuratorAutoConfig {

    @Bean
    public ZookeeperTransporter zookeeperTransporter() {
        return new CuratorZookeeperTransporter();
    }

    @Bean
    @ConditionalOnMissingBean(Registry.class)
    public Registry registry(URL url, ZookeeperTransporter zookeeperTransporter, ZooKeeperNodeInfo zooKeeperNodeInfo) {
        return new ZookeeperRegistry(url, zookeeperTransporter, zooKeeperNodeInfo);
    }

    @Bean
    @ConditionalOnMissingBean(Subscribe.class)
    public Subscribe subscribe(URL url, ZookeeperTransporter zookeeperTransporter, DataListener dataListener) {
        return new ZookeeperSubscribe(url, zookeeperTransporter, dataListener);
    }

    @Bean
    @ConditionalOnMissingBean(DataListener.class)
    public DataListener dataListener() {
        return new DataListener() {
            @Override
            public void dataChanged(String path, Object value, EventType eventType) {
                switch (eventType) {
                    case NodeCreated:
                        //创建rpc连接
                        break;
                    case NodeDataChanged:
                        break;
                    case NodeDeleted:
                        break;
                    default:
                }
            }
        };
    }

}
