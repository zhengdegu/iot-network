package com.gu.network.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * 抽象消息解码类
 *
 * @author FastG
 * @date 2019/8/26 14:09
 */
@Slf4j
public abstract class AbstractMessageDecoder<T> extends ByteToMessageDecoder {


    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, java.util.List<Object> list) throws Exception {


        T msg = doDecode(byteBuf);
        if (msg != null) {
            list.add(msg);
        }
    }

    /**
     * 解码具体实现
     *
     * @param byteBuf
     * @return
     * @throws Exception
     */
    abstract protected T doDecode(ByteBuf byteBuf) throws Exception;
}
