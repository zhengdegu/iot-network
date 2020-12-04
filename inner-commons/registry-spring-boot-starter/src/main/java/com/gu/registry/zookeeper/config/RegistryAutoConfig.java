package com.gu.registry.zookeeper.config;

import com.gu.common.zookeeper.CuratorAutoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Import;

/**
 * @author FastG
 * @date 2020/11/1 18:57
 */
@Slf4j
@Import(CuratorAutoConfiguration.class)
public class RegistryAutoConfig {

}
