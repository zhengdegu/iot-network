package com.gu.network.handlers;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author FastG
 * @date 2020/10/30 17:43
 */
@ChannelHandler.Sharable
public abstract class AbstractExceptionHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        this.doExceptionCaught(ctx, cause);
    }

    /**
     * 异常处理
     *
     * @param ctx   handler
     * @param cause 异常信息
     * @throws Exception 抛出异常
     */
    protected abstract void doExceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception;

}
