package com.gu.network.handlers;

import com.gu.network.properties.ServerProperties;
import com.gu.network.utils.SpringUtil;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;


/**
 * @author FastG
 * @date 2019/8/26 16:34
 */
public abstract class AbstractServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final int readTimeout;
    @Autowired
    private AbstractIdleStateChecke idleStateChecke;
    @Autowired
    private AbstractMessageEncoder messageEncoder;
    @Autowired
    private AbstractAuthReqHandler loginAuthReqHandler;
    @Autowired
    private AbstractConnectionHandler connectionHandler;
    @Autowired
    private AbstractMessageHandler messageHandler;
    @Autowired
    private AbstractExceptionHandler exceptionHandler;
    @Autowired
    private AbstractInactiveHandler inactiveHandler;

    public AbstractServerChannelInitializer(ServerProperties serverProperties) {
        this.readTimeout = serverProperties.getOfflineTimeout();
    }

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {

        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast("idleHandler", new IdleStateHandler(this.readTimeout, 0, 0, TimeUnit.SECONDS));
        pipeline.addLast("messageDecoder", getMessageDecoder());
        pipeline.addLast("messageEncoder", messageEncoder);
        pipeline.addLast("idleStateCheck", idleStateChecke);
        //考虑验证 消息处理耗时 添加线程
        pipeline.addLast("loginAuthReqHandler", loginAuthReqHandler);
        pipeline.addLast("connectionHandler", connectionHandler);
        pipeline.addLast("messageHandler", messageHandler);
        pipeline.addLast("exceptionHandler", exceptionHandler);
        pipeline.addLast("inactiveHandler", inactiveHandler);
        this.doChannel(pipeline);
    }


    private AbstractMessageDecoder getMessageDecoder() {
        return SpringUtil.getBean(AbstractMessageDecoder.class);
    }

    /**
     * 添加handler
     *
     * @param channel
     * @throws Exception
     */
    abstract protected void doChannel(ChannelPipeline channel) throws Exception;
}
