package com.gu.network.handlers;

import cn.hutool.core.util.ObjectUtil;
import com.gu.network.exception.DefaultException;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author FastG
 * @date 2020/10/30 17:57
 */
@ChannelHandler.Sharable
public abstract class AbstractConnectionHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {

        if (ObjectUtil.isNull(o)) {
            throw new DefaultException("[" + AbstractConnectionHandler.class.getSimpleName() + "] Hasn't read data ");
        }

        channelHandlerContext.fireChannelRead(o);
        //第一次连接初始化
        channelHandlerContext.pipeline().remove(AbstractConnectionHandler.class);

    }

    /**
     * 登录校验的具体实现
     *
     * @param channelHandlerContext handler
     * @param o                     msg
     * @throws Exception 异常
     */
    protected abstract void doChannelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception;

}
