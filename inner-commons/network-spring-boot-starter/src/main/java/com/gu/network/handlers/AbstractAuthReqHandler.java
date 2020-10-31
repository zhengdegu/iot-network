package com.gu.network.handlers;

import cn.hutool.core.util.ObjectUtil;
import com.gu.network.exception.DefaultException;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author FastG
 * @date 2019/8/26 19:37
 */
@ChannelHandler.Sharable
public abstract class AbstractAuthReqHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {

        if (ObjectUtil.isNull(o)) {
            throw new DefaultException("[" + AbstractAuthReqHandler.class.getSimpleName() + "] Hasn't read data ");
        }

        if (this.doChannelRead(channelHandlerContext, o)) {

            channelHandlerContext.fireChannelRead(o);
            //校验成功 移除
            channelHandlerContext.pipeline().remove(this);
        }
    }

    /**
     * 登录校验的具体实现
     *
     * @param channelHandlerContext handler
     * @param o msg
     * @throws Exception 异常
     */
     protected abstract boolean doChannelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception;

}
