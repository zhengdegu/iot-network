package com.gu.registry.zookeeper.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author FastG
 * @date 2020/11/1 13:41
 */
@Setter
@Getter
@AllArgsConstructor
public class URL {

    private String connectString = "127.0.0.1:2181";
    private Integer timeout = 5 * 1000;
    private Integer sessionExpireMs = 60 * 1000;
    private String username;
    private String password;
    private String path = "/network";

    public URL(String connectString, Integer timeout, Integer sessionExpireMs, String username, String password) {
        this.connectString = connectString;
        this.password = password;
        this.username = username;
        this.timeout = timeout;
        this.sessionExpireMs = sessionExpireMs;
    }

    public String getAuthority() {
        if (StringUtils.isEmpty(username)
                && StringUtils.isEmpty(password)) {
            return null;
        }
        return (username == null ? "" : username)
                + ":" + (password == null ? "" : password);
    }

}
