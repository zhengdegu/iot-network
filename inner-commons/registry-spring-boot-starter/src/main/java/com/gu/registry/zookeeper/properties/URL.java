package com.gu.registry.zookeeper.properties;


import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * @author FastG
 * @date 2020/11/1 13:41
 */
@ConfigurationProperties(prefix = "spring.network.zookeeper")
public class URL implements Serializable {

    private String connectString = "127.0.0.1:2181";
    private Integer timeout = 5 * 1000;
    private Integer sessionExpireMs = 60 * 1000;
    private String username;
    private String password;
    private String path = "/network/cluster";

    public URL(String connectString, Integer timeout, Integer sessionExpireMs, String username, String password, String path) {
        this.connectString = connectString;
        this.timeout = timeout;
        this.sessionExpireMs = sessionExpireMs;
        this.username = username;
        this.password = password;
        this.path = path;
    }

    public URL(String connectString, Integer timeout, Integer sessionExpireMs, String username, String password) {
        this.connectString = connectString;
        this.password = password;
        this.username = username;
        this.timeout = timeout;
        this.sessionExpireMs = sessionExpireMs;
    }

    public String getConnectString() {
        return connectString;
    }

    public String getAuthority() {
        if (StringUtils.isEmpty(username)
                && StringUtils.isEmpty(password)) {
            return null;
        }
        return (username == null ? "" : username)
                + ":" + (password == null ? "" : password);
    }

    public void setConnectString(String connectString) {
        this.connectString = connectString;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getSessionExpireMs() {
        return sessionExpireMs;
    }

    public void setSessionExpireMs(Integer sessionExpireMs) {
        this.sessionExpireMs = sessionExpireMs;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
