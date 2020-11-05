package com.gu.registry.zookeeper.properties;

/**
 * @author FastG
 * @date 2020/11/3 17:38
 */
public class RpcNodeInfo {
    /**
     * 服务器id
     */
    private String serverId;
    private String host;
    private String port;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public int getIntPort() {
        return Integer.parseInt(port);
    }
}
