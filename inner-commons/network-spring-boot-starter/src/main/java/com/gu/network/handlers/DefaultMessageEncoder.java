package com.gu.network.handlers;


import cn.hutool.core.util.ObjectUtil;
import com.gu.network.exception.DefaultException;
import com.gu.network.message.Message;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;

/**
 * 默认编码实现类
 *
 * @author FastG
 * @date 2019/8/26 15:21
 */

@Slf4j
public class DefaultMessageEncoder extends AbstractMessageEncoder {


    @Override
    protected byte[] doEncode(Object defaultPacketMessage, ByteBuf byteBuf) throws Exception {
        if (ObjectUtil.isNull(defaultPacketMessage)) {
            throw new DefaultException("the encoder message is null");
        }

        if (log.isDebugEnabled()) {
            log.debug("[{}] receive message :{}", DefaultMessageEncoder.class.getSimpleName(), defaultPacketMessage.toString());
        }

        if (defaultPacketMessage instanceof String) {
            return ((String) defaultPacketMessage).getBytes();

        }

        if (defaultPacketMessage instanceof byte[]) {
            return (byte[]) defaultPacketMessage;
        }

        if (defaultPacketMessage instanceof Message.ReceivedMessage) {

            return null;
        }
        return new byte[]{0};
    }
}
