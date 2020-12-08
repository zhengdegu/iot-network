package com.gu.common.zookeeper;

import com.gu.common.properties.CuratorProperties;
import com.gu.common.properties.GRpcClientProperties;
import com.gu.common.properties.RegistryProperties;
import com.gu.common.zookeeper.client.ZookeeperClient;
import com.gu.common.zookeeper.client.impl.CuratorZookeeperClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.ensemble.EnsembleProvider;
import org.apache.curator.framework.AuthInfo;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.ACLProvider;
import org.apache.curator.framework.api.CompressionProvider;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.ZookeeperFactory;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.concurrent.ThreadFactory;

/**
 * @author gu
 * @create 2020/12/4 下午4:34
 */
@Slf4j
@ConditionalOnClass({ZooKeeper.class, CuratorFramework.class})
@EnableConfigurationProperties({CuratorProperties.class, RegistryProperties.class, GRpcClientProperties.class})
public class CuratorAutoConfiguration implements BeanFactoryAware {

    private BeanFactory beanFactory;

    @Bean(initMethod = "start")
    @ConditionalOnMissingBean(CuratorFramework.class)
    public CuratorFramework curatorFramework(RetryPolicy retryPolicy,CuratorProperties curatorProperties) {

        final CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();

        if(StringUtils.hasText(curatorProperties.getConnectString())) {
            // connection string will be first
            builder.connectString(curatorProperties.getConnectString());
        } else if (StringUtils.hasLength(curatorProperties.getEnsembleProviderRef())) {
            builder.ensembleProvider(beanFactory.getBean(curatorProperties.getEnsembleProviderRef(), EnsembleProvider.class));
        } else {
            throw new IllegalArgumentException("[Assertion failed] 'connection-string' must be configured.");
        }

        if(StringUtils.hasLength(curatorProperties.getAclProviderRef())) {
            builder.aclProvider(beanFactory.getBean(curatorProperties.getAclProviderRef(), ACLProvider.class));
        }

        if(StringUtils.hasText(curatorProperties.getAuthInfosRef())) {
            List<AuthInfo> authInfos = beanFactory.getBean(curatorProperties.getAuthInfosRef(), List.class);
            builder.authorization(authInfos);
        } else if(StringUtils.hasText(curatorProperties.getScheme()) && StringUtils.hasText(curatorProperties.getAuthBase64Str())) {
            builder.authorization(curatorProperties.getScheme(), Base64Utils.decodeFromString(curatorProperties.getAuthBase64Str()));
        }

        if(curatorProperties.getCanBeReadOnly() != null) {
            builder.canBeReadOnly(curatorProperties.getCanBeReadOnly());
        }

        if(curatorProperties.getUseContainerParentsIfAvailable() != null && !curatorProperties.getUseContainerParentsIfAvailable()) {
            builder.dontUseContainerParents();
        }

        if(StringUtils.hasLength(curatorProperties.getCompressionProviderRef())) {
            builder.compressionProvider(beanFactory.getBean(curatorProperties.getCompressionProviderRef(), CompressionProvider.class));
        }

        if(curatorProperties.getDefaultDataBase64Str() != null) {
            builder.defaultData(Base64Utils.decodeFromString(curatorProperties.getDefaultDataBase64Str()));
        }

        if(StringUtils.hasText(curatorProperties.getNamespace())) {
            builder.namespace(curatorProperties.getNamespace());
        }

        // 重试策略
        if(null != retryPolicy) {
            builder.retryPolicy(retryPolicy);
        }

        if(null != curatorProperties.getSessionTimeOutMs()) {
            builder.sessionTimeoutMs(curatorProperties.getSessionTimeOutMs());
        }

        if(null != curatorProperties.getConnectionTimeoutMs()) {
            builder.connectionTimeoutMs(curatorProperties.getConnectionTimeoutMs());
        }

        if(null != curatorProperties.getMaxCloseWaitMs()) {
            builder.maxCloseWaitMs(curatorProperties.getMaxCloseWaitMs());
        }

        if(StringUtils.hasLength(curatorProperties.getThreadFactoryRef())) {
            builder.threadFactory(beanFactory.getBean(curatorProperties.getThreadFactoryRef(), ThreadFactory.class));
        }

        if(StringUtils.hasLength(curatorProperties.getZookeeperFactoryRef())) {
            builder.zookeeperFactory(beanFactory.getBean(curatorProperties.getZookeeperFactoryRef(), ZookeeperFactory.class));
        }
        CuratorFramework build = builder.build();
        build.start();
        return build;
    }

    @Bean
    @ConditionalOnMissingBean(RetryPolicy.class)
    public RetryPolicy retryPolicy(CuratorProperties curatorProperties) {
        return new ExponentialBackoffRetry(curatorProperties.getBaseSleepTimeMs(), curatorProperties.getMaxRetries());
    }

    @Bean
    @ConditionalOnMissingBean(ZookeeperClient.class)
    public ZookeeperClient zookeeperClient() {
        return new CuratorZookeeperClient();
    }

    @Override
    public void setBeanFactory(@Nonnull BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
