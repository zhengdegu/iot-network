package com.gu.network.handlers;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 连接超时机制   默认180秒没有心跳数据断开连接
 *
 * @author FastG
 * @date 2019/8/26 19:03
 */
@ChannelHandler.Sharable
public abstract class AbstractIdleStateChecke extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            this.doUserEventTriggered(ctx, evt);
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    /**
     * idle处理
     * @param ctx
     * @param evt
     * @throws Exception
     */
    protected abstract void doUserEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception;
}
