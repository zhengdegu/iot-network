package com.gu.network.handlers;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author FastG
 * @date 2020/10/30 18:13
 */
@Slf4j
public class DefaultExceptionHandler extends AbstractExceptionHandler {
    @Override
    protected void doExceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.warn("occur exception:{}", cause.getMessage(), cause);
        if (ctx.channel().isOpen() && cause instanceof IOException) {
            if (log.isDebugEnabled()) {
                log.debug("close remote address [{}] channel", ctx.channel().remoteAddress());
                ctx.channel().close();
            }
        }
    }
}
