package com.gu.network.handlers;

import com.gu.network.session.ConnectionManager;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author FastG
 * @date 2020/10/31 16:57
 */
public class DefaultInactiveHandler extends AbstractInactiveHandler {

    @Autowired
    private ConnectionManager connectionManager;

    @Override
    protected void doChannelInactive(ChannelHandlerContext ctx) throws Exception {

    }
}
