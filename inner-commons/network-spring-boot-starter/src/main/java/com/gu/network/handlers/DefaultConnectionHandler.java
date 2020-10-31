package com.gu.network.handlers;

import com.gu.network.server.Server;
import com.gu.network.session.ConnectionManager;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author FastG
 * @date 2020/10/30 18:00
 */
public class DefaultConnectionHandler extends AbstractConnectionHandler {

    @Autowired
    private  ConnectionManager connectionManager;
    @Autowired
    private  Server server;

    @Override
    protected void doChannelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        connectionManager.add(channelHandlerContext.channel(), "", server.attribute().getAddress(), server.attribute().getPort());
    }
}
