package com.gu.network.session;

import io.netty.channel.Channel;

public interface ConnectionManager {

    /**
     * 添加channel
     *
     * @param channel
     * @param drcId
     */
    void add(Channel channel, String drcId);

    /**
     * @param channel
     * @param drcId
     */
    void update(Channel channel, String drcId);

    /**
     * 移除channel
     *
     * @param drcId
     * @param channel
     */
    void remove(String drcId, Channel channel);

    /**
     * 获取channel
     *
     * @param drcId
     * @return
     */
    Connection get(String drcId);

    /**
     * 是否存在
     *
     * @param drcId
     * @return
     */
    boolean contains(String drcId);

    /**
     * 在线统计
     *
     * @return
     */
    public java.util.List<ConnectionUser> onlineUser();

    /**
     * 发送消息到终端
     *
     * @param drcId
     * @param memessage
     */
    public void sendMessageToClient(String drcId, Object memessage);

    /**
     * 关闭连接
     *
     * @param drcId
     * @return
     */
    boolean closeConnection(String drcId);


}
