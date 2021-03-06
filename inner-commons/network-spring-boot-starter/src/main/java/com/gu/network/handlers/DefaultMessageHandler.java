package com.gu.network.handlers;

import com.gu.network.message.Message;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 消息处理具体实现类   默认不做处理
 *
 * @author FastG
 * @date 2019/8/28 9:23
 */
@Slf4j
public class DefaultMessageHandler extends AbstractMessageHandler {
    @Override
    protected void doChannelRead0(ChannelHandlerContext channelHandlerContext, Message.ReceivedMessage defaultPacketMessage) throws Exception {
        log.info("You should extends com.gu.network.handlers.MessageHandler and override the doChannelRead0 method to add the active channel:{}", channelHandlerContext);
    }
}
