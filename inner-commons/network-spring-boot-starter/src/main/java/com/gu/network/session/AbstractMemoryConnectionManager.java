package com.gu.network.session;


import io.netty.channel.Channel;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author FastG
 * @date 2019/8/28 17:49
 */
@Slf4j
public abstract class AbstractMemoryConnectionManager implements ConnectionManager {


    private static final Map<String, Connection> CONNECTION_HASH_MAP = new ConcurrentHashMap<>();

    @Override
    public void add(Channel channel, String drcId, String serverAddress, Integer serverPort) {
        if (drcId == null || channel == null) {
            return;
        }
        Connection connection = Connection.builder()
                .drcId(drcId)
                .channelId(channel.id().asLongText())
                .channel(channel)
                .serverAddress(serverAddress)
                .serverPort(serverPort)
                .build();

        CONNECTION_HASH_MAP.put(drcId, connection);

        ConnectionUtil.markOnline(channel);
    }

    @Override
    public void update(Channel channel, String drcId, String serverAddress, Integer serverPort) {
        if (drcId == null || channel == null) {
            return;
        }
        final String channelId = channel.id().asLongText();

        Connection connection = get(drcId);

        if (connection == null) {
            return;
        }
        final String channelIdO = connection.getChannelId();

        if (!org.apache.commons.lang3.StringUtils.equals(channelId, channelIdO)) {
            Connection connectionN = Connection.builder()
                    .channelId(channelId)
                    .channel(channel)
                    .drcId(drcId)
                    .serverAddress(serverAddress)
                    .serverPort(serverPort)
                    .conTime(System.currentTimeMillis())
                    .build();

            CONNECTION_HASH_MAP.put(drcId, connectionN);
            ConnectionUtil.markOnline(channel);
        }
    }

    @Override
    public void remove(String drcId, Channel channel) {
        Connection connection = this.get(drcId);
        if (connection != null && org.apache.commons.lang3.StringUtils.equals(connection.getChannelId(), channel.id().asLongText())) {
            CONNECTION_HASH_MAP.remove(drcId);
        }
    }

    @Override
    public Connection get(String drcId) {
        if (drcId == null) {
            return null;
        }
        return CONNECTION_HASH_MAP.get(drcId);
    }

    @Override
    public boolean contains(String drcId) {
        return CONNECTION_HASH_MAP.get(drcId) != null;
    }

    @Override
    public List<ConnectionUser> onlineUser() {
        return CONNECTION_HASH_MAP.values().stream()
                .map(connection -> {
                    return ConnectionUser.builder()
                            .drcId(connection.getDrcId())
                            .channel(connection.getChannel())
                            .conTime(connection.getConTime())
                            .build();
                }).distinct().collect(Collectors.toList());
    }


    @Override
    public void sendMessageToClient(@NonNull String drcId, @NonNull Object message) {

        Connection connection = get(drcId);

        if (connection != null) {
            //noinspection unchecked
            connection.getChannel().writeAndFlush(message).addListeners(future -> {
                if (!future.isSuccess()) {
                    this.retrySendMessage(drcId, message);
                }
            });
        } else {
            log.info("drcId:{} is  offline!", drcId);
        }
    }

    @Override
    public boolean closeConnection(String drcId) {
        Connection connection = get(drcId);
        if (connection != null) {
            connection.getChannel().close();
            return true;
        } else {
            log.info("drcId:{} is  offline!", drcId);
            return false;
        }
    }

    /**
     * 终端在线下发失败，重试
     * @param drcId 终端号
     * @param message 消息体
     */
    abstract void retrySendMessage(String drcId, Object message);
}
