package com.gu.network.handlers;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author FastG
 * @date 2020/10/31 16:55
 */
@ChannelHandler.Sharable
public abstract class AbstractInactiveHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        this.doChannelInactive(ctx);
        ctx.fireChannelInactive();
    }
    /**
     * 关闭连接操作
     * @param ctx 通道
     * @throws Exception
     */
    protected abstract void doChannelInactive(ChannelHandlerContext ctx) throws Exception;

}
