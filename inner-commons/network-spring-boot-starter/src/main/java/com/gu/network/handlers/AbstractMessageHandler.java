package com.gu.network.handlers;

import com.gu.network.message.Message;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author FastG
 * @date 2019/8/28 9:19
 */
@Slf4j
@ChannelHandler.Sharable
public abstract class AbstractMessageHandler extends SimpleChannelInboundHandler<com.gu.network.message.Message.ReceivedMessage> {



    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message.ReceivedMessage defaultPacketMessage) throws Exception {

        this.doChannelRead0(channelHandlerContext, defaultPacketMessage);
    }


    /**
     * 业务处理具体实现
     *
     * @param channelHandlerContext
     * @param defaultPacketMessage
     * @throws Exception
     */
    protected abstract void doChannelRead0(ChannelHandlerContext channelHandlerContext, Message.ReceivedMessage defaultPacketMessage) throws Exception;

}