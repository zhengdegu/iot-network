package com.gu.registry.zookeeper.config;

import com.gu.common.zookeeper.CuratorAutoConfiguration;
import com.gu.registry.zookeeper.RegistryServerRunner;
import com.gu.registry.zookeeper.registry.RegistryService;
import com.gu.registry.zookeeper.registry.impl.ZookeeperRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

/**
 * @author FastG
 * @date 2020/11/1 18:57
 */
@Slf4j
public class RegistryAutoConfig extends CuratorAutoConfiguration {

    @Bean
    public RegistryService registryService() {
        return new ZookeeperRegistry();
    }

    @Bean
    public RegistryServerRunner registryServerRunner() {
        return new RegistryServerRunner();
    }
}
