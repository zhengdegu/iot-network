package com.gu.network;


import com.gu.network.handlers.*;
import com.gu.network.properties.ServerProperties;
import com.gu.network.server.DefaultServer;
import com.gu.network.server.Server;
import com.gu.network.session.ConnectionManager;
import com.gu.network.session.DefaultConnectionManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


/**
 * @author FastG
 * @date 2019/8/26 15:36
 */
@EnableConfigurationProperties({ServerProperties.class})
@Configuration
public class DefaultNetworkAutoConfig {

    @Bean
    @ConditionalOnMissingBean(AbstractIdleStateChecke.class)
    public AbstractIdleStateChecke idleStateCheck() {
        return new DefaultIdleStateChecke();
    }

    @Bean
    @ConditionalOnMissingBean(Server.class)
    public Server server(ServerProperties serverProperties, AbstractServerChannelInitializer serverChannelInitializer) {
        DefaultServer defaultServer = new DefaultServer(serverProperties, serverChannelInitializer);
        defaultServer.start();
        return defaultServer;
    }


    @Bean
    @ConditionalOnMissingBean(AbstractServerChannelInitializer.class)
    public AbstractServerChannelInitializer serverChannelInitializer(ServerProperties serverProperties) {
        return new DefaultServerChannelInitialize(serverProperties);
    }


    @Bean
    @Scope("prototype")
    @ConditionalOnMissingBean(AbstractMessageDecoder.class)
    public AbstractMessageDecoder messageDecoder() {
        return new DefaultMessageDecoder();
    }


    @Bean
    @ConditionalOnMissingBean(AbstractMessageEncoder.class)
    public AbstractMessageEncoder messageEncoder() {
        return new DefaultMessageEncoder();
    }

    @Bean
    @ConditionalOnMissingBean(AbstractAuthReqHandler.class)
    public AbstractAuthReqHandler loginAuthReqHandler() {
        return new DefaultAuthReqHandler();
    }

    @Bean
    @ConditionalOnMissingBean(AbstractMessageHandler.class)
    public AbstractMessageHandler messageHandler() {
        return new DefaultMessageHandler();
    }


    @Bean
    @ConditionalOnMissingBean(ConnectionManager.class)
    public ConnectionManager connectionManager() {
        return new DefaultConnectionManager();
    }


    @Bean
    @ConditionalOnMissingBean(AbstractConnectionHandler.class)
    public AbstractConnectionHandler connectionHandler() {
        return new DefaultConnectionHandler();
    }

    @Bean
    @ConditionalOnMissingBean(AbstractExceptionHandler.class)
    public AbstractExceptionHandler exceptionHandler() {
        return new DefaultExceptionHandler();
    }
}
