package com.gu.registry.zookeeper;

import com.gu.registry.zookeeper.config.RegistryAutoConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gu
 * @create 2020/12/7 下午4:56
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(RegistryAutoConfig.class)
public @interface EnableZookeeperRegistry {
}
